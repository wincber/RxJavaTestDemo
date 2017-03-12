package com.example.wincber.rxjavatestdemo.common;

import android.util.Xml;

import com.example.wincber.rxjavatestdemo.common.Weather;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wincber on 3/11/2017.
 */

public class WeatherConstant {
    private static  String  WEATHER_API_URL="http://php.weather.sina.com.cn/xml.php?city=%s&password=DJOYnieT8234jlsK&day=0";
    private static OkHttpClient client = new OkHttpClient();
    public static String getWeatherApiUrl(String city){
        String urlString = null;
        try {
             urlString = String.format(WEATHER_API_URL, URLEncoder.encode(city, "GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlString;
    }
    public static String getWeather(final String url){
        final Request request = new Request.Builder().get().url(url).build();
        StringBuffer buffer = new StringBuffer();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                buffer.append(response.body().string()) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public static Weather parseXNLWithPull(String xmlData){
        XmlPullParser xmlPullParser = Xml.newPullParser();
        StringReader reader = new StringReader(xmlData);
        Weather weather = null;
        try {
            xmlPullParser.setInput(reader);
            int eventType = xmlPullParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        weather = new Weather();
                        break;
                    case XmlPullParser.START_TAG:{
                        String nodeName = xmlPullParser.getName();
                        if("city" .equals(nodeName)){
                            weather.setCity(xmlPullParser.nextText());
                        }else if("savedate_weather" .equals(nodeName)){
                            weather.setDate(xmlPullParser.nextText());
                        } else if("temperature1".equals(nodeName)) {
                            weather.setTemperature( xmlPullParser.nextText());
                        } else if("temperature2".equals(nodeName)){
                            weather.setTemperature("--" +  xmlPullParser.nextText());
                        } else if("direction1".equals(nodeName)){
                            weather.setDirection(xmlPullParser.nextText());
                        } else if("power1".equals(nodeName)){
                            weather.setPower( xmlPullParser.nextText());
                        } else if("status1".equals(nodeName)){
                            weather.setStatus( xmlPullParser.nextText());
                        }
                        break;
                    }
                }
                eventType = xmlPullParser.next();
            }
            return weather;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            reader.close();
        }
        return null;
    }
}

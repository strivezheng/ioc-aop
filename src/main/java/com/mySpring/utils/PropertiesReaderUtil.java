package com.mySpring.utils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 10033 on 2017/5/9.
 */
public class PropertiesReaderUtil {

    //IoC的定位
    public static Map<String, String> getProperties(String properties)   {//"scan-package.properties"

        Properties props = new Properties();
        Map<String, String> map = new HashMap<String, String>();
        try {

//            InputStream in = getClass().getResourceAsStream(properties);
            InputStream in = PropertiesReaderUtil.class.getResourceAsStream(properties);
            props.load(in);
            Enumeration en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String property = props.getProperty(key);
                map.put(key, property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /*public static void main(String[] args) {
        Map map=getProperties("/scan-package.properties");
        System.out.println(map.get("scan.package"));
    }*/
}

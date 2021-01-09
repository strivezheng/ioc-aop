package com.mySpring.autowired;

import com.mySpring.utils.ConstantUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by seven on 2018/5/12.
 * 获取相应类对象
 * IoC的解析
 */
public class GetClass {
    private static ClasspathPackageScanner classpathPackageScanner;

    public static List<Class> getClassList(String key)
            throws IOException, ClassNotFoundException {//key = scan.package
        //从常量工具类里获取
        classpathPackageScanner = new ClasspathPackageScanner(ConstantUtil.PROPERTY_MAP.get(key));

        List<String> list = classpathPackageScanner.getFullyQualifiedClassNameList();
        List<Class> classList = new ArrayList<Class>();
        for (String string : list) {
            classList.add(Class.forName(string));
        }
        return classList;

    }
}

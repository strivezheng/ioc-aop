package com.mySpring.autowired;


import com.mySpring.utils.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by 10033 on 2017/5/9.
 */

/**
 * 获取某个包下的类
 */
public class ClasspathPackageScanner {

    private String basePackage;
    private ClassLoader classLoader;

    /**
     * Construct an instance and specify the base package it should scan.
     * @param basePackage The base package to scan.
     */
    public ClasspathPackageScanner(String basePackage) {
        this.basePackage = basePackage;
        this.classLoader = getClass().getClassLoader();//获得类加载器

    }

    /**
     * Construct an instance with base package and class loader.
     * @param basePackage The base package to scan.
     * @param classLoader Use this class load to locate the package.
     */
    public ClasspathPackageScanner(String basePackage, ClassLoader classLoader) {
        this.basePackage = basePackage;
        this.classLoader = classLoader;
    }

    /**
     * Get all fully qualified names located in the specified package
     * and its sub-package.
     *
     * @return A list of fully qualified names.
     * @throws IOException
     * IoC的载入
     */
    public List<String> getFullyQualifiedClassNameList() throws IOException {

        return doScan(basePackage, new ArrayList());
    }

    /**
     * Actually perform the scanning procedure.
     *
     * @param basePackage
     * @param nameList A list to contain the result.
     * @return A list of fully qualified names.
     *
     * @throws IOException
     */
    private List<String> doScan(String basePackage, List<String> nameList) throws IOException {
        // replace dots with splashes
        String splashPath = StringUtil.dotToSplash(basePackage);

        // get file path
        URL url = classLoader.getResource(splashPath);

        String filePath = StringUtil.getRootPath(url);

        List<String> names;
        if (isJarFile(filePath)) {

            names = readFromJarFile(filePath, splashPath);
        } else {

            names = readFromDirectory(filePath);
        }

        for (String name : names) {
            if (isClassFile(name)) {
                nameList.add(toFullyQualifiedName(name, basePackage));
            } else {
                //递归过程
                doScan(basePackage + "." + name, nameList);
            }
        }

        return nameList;
    }

    private String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(StringUtil.trimExtension(shortName));

        return sb.toString();
    }

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {

        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();

        List<String> nameList = new ArrayList();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                nameList.add(name);
            }

            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    private List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }


    /*public static void main(String[] args) throws Exception {
        ClasspathPackageScanner scan =
                new ClasspathPackageScanner("temp");
        List<String> list=scan.getFullyQualifiedClassNameList();
        for(String s:list) {
            System.out.println(s);
        }
    }*/

}
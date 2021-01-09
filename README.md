


# 说明
本项目是手动实现一个类似Spring的IOC和AOP功能的demo


## 说明

首选代码的测试运行代码见
- Application.java

```
 public static void main(String[] args) throws ClassNotFoundException {
        //初始化环境
        init();

        //模拟spring启动
        Class.forName("com.mySpring.autowired.BeanFactory");

        ClassesService classesService = (ClassesService) BeanFactory.getBean("classesService");
        TeacherService teacherService = (TeacherService) BeanFactory.getBean("teacherService");
        StudentService studentService = (StudentService) BeanFactory.getBean("studentService");

        classesService.showClasses();
        System.out.println();

        teacherService.show();
        System.out.println();

        studentService.show();

    }

```
运行结果：

```
surround  Before~~~~
切面计算执行耗时：5
surround  After~~~~
计算机班
after~~~~~~

surround  Before~~~~
my name is苍老师
切面计算执行耗时：1201
surround  After~~~~

before~~~~~
嘻嘻
```
从代码运行结果可看到，成功通过容器注入了实例，并且实现了简单的AOP功能（before、after、surround以及计算方法执行时间），下面简单讲解下项目的大概框架

### IOC的实现

依赖注入功能的实现见目录

> com.mySpring.autowired

这里定义了两个注解
- MyBean.java 
- MyAutowired.java 

分别等同于Spring的bean注解和Autowired注解。



Bean的初始化由BeanFactory这个类实现
- BeanFactory.java

在BeanFactory里面定义了一个静态代码块，所以在BeanFactory里会最先执行这段代码，所有的Bean实例化后会存储在beanMap这个HashMap中，beanMap的key是bean的Name，value是bean的实例。具体方法如下：

```
    /**
     * 初始化IoC容器
     */
    static {
        //实例化对象bean，填充到map中存储
        AutomaticInjection.automaticInjection(KEY, beanMap);

        //将实际的bean设置成cglib代理的bean
        ProxyFactory.makeProxyBean(beanMap);

        //生成代理后重新注入,eg：classesService里面的成员变量teacherService此时是null、需要重新赋值
        for (String key : beanMap.keySet()) {
            Class c = beanMap.get(key).getClass().getSuperclass();
            try {
                AutomaticInjection.reinjection(beanMap, c, beanMap.get(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
```

同时定义了一个getBean方法用于获取Bean

```
   /**
     * 根据beanName 返回bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return beanMap.get(name);
    }
```





### AOP的实现
AOP动态代理生成代理类是使用了CGLIB的API
见pom依赖

```
   <dependency>
      <groupId>cglib</groupId>
      <artifactId>cglib</artifactId>
      <version>2.2.2</version>
    </dependency>
```
在本项目中，实现AOP功能的主要代码见目录：
> com.mySpring.aop

这里定义了两个注解
- PointCut.java     （注明需要生成代理的类）
- Ignore.java       （用于需要忽略的方法）


定义了三个Advice，均继承自基础Advice接
- AfterAdvice
- BeforeAdvice
- SurroundAdvice

他们分别由
> com.mySpring.aop.aspect

目录下的
- AfterAspect.java
- BeforeAspect.java
- SurroundAspect.java

实现

生成代理类的入口在前面提到的BeanFactory中的静态代码调用

```
ProxyFactory.makeProxyBean(beanMap);
```

本项目我配置的代理类生成地址是
> D:\classes

在D:\classes\com\mySpring\service目录下可以见到很多生成的代理类class文件

```
ClassesService$$EnhancerByCGLIB$$4d3cff49$$FastClassByCGLIB$$73e115db.class
ClassesService$$EnhancerByCGLIB$$4d3cff49.class
... 
...
```
这里提一句，以前在一家公司面试，被问到过spring生成代理类的命名规则是什么样的。
这里有关cglib的具体代码可以见
> https://blog.csdn.net/woshilijiuyi/article/details/83448407





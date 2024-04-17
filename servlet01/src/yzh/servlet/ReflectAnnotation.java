package yzh.servlet;

import jakarta.servlet.annotation.WebServlet;

public class ReflectAnnotation {
    public static void main(String[] args) throws ClassNotFoundException {
        //使用反射机制将类上面的注解进行解析
        //获取类class对象
        Class<?> welcomeServletClass = Class.forName("yzh.servlet.WelcomeServlet");
        //获取这个类的注解对象
        //先判断这个类上面有没有注解对象，如果有注解对象就获取该注解对象
        boolean annotationPresent = welcomeServletClass.isAnnotationPresent(WebServlet.class);
        System.out.println(annotationPresent);
        if(annotationPresent)
        {
            //获取这类上的注解对象
            WebServlet webServletannotation = welcomeServletClass.getAnnotation(WebServlet.class);
            //获取注解的value属性值
            String[] value = webServletannotation.value();
            for (int i = 0; i < value.length; i++) {
                System.out.println(value[i]);
            }
        }
    }
}

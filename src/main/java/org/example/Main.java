package org.example;

public class Main {
    public static void main(String[] args) {
        SomeBean someBean=new SomeBean();
        (new Injector()).inject(someBean, "config.properties");
        someBean.doSome();
    }
}
package org.example;

public class SomeBean {
    @AutoInjectable
    private SomeInterface field;

    public void doSome(){
        field.someMethod();
    }
}

package org.example;

public class SomeBean {
    @AutoInjectable
    private SomeInterface field1;
    @AutoInjectable
    private OtherInterface field2;
    String field3;

    public void doSome(){
        field1.someMethod();
        field2.otherMethod();
    }
}

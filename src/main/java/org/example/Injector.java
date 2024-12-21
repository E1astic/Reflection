package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Injector {
    public <T> void inject(T obj, String configFileName) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Properties properties=loadProperties(configFileName);

        for(Field field : obj.getClass().getFields()){
            if(field.isAnnotationPresent(AutoInjectable.class)){
                String implementationClassName = properties.getProperty(field.getType().getName());
                Class<?> implementationClass;
                if(implementationClassName!=null){
                    implementationClass = Class.forName(implementationClassName);
                    Object instance = implementationClass.getDeclaredConstructor().newInstance();

                    field.setAccessible(true);
                    field.set(obj, instance);
                }
            }
        }
    }

    private Properties loadProperties(String configFileName){
        Properties properties=new Properties();

        try(InputStream inputStream=new FileInputStream(configFileName)){
            properties.load(inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return properties;
    }
}

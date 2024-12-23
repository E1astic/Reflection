package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {
    public <T> void inject(T obj, String configFileName) {
        // получаем все настройки из конфигурационного файла
        Properties properties=loadProperties(configFileName);
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoInjectable.class)) {
                    // получаем название класса, который должен быть присвоен полю
                    String implementationClassName = properties.getProperty(field.getType().getName());
                    if (implementationClassName != null) {
                        Class<?> implementationClass = Class.forName(implementationClassName);
                        // инициализируем нужный объект с помощью конструктора
                        Object instance = implementationClass.getDeclaredConstructor().newInstance();
                        // присваиваем полю проинициализированный объект
                        field.setAccessible(true);
                        field.set(obj, instance);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private Properties loadProperties(String configFileName){
        Properties properties=new Properties();

        try(InputStream inputStream=getClass().getClassLoader().getResourceAsStream(configFileName)){
            properties.load(inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return properties;
    }
}

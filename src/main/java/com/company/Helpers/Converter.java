package com.company.Helpers;

import com.company.Models.Transform_date;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.time.ZonedDateTime;

public class Converter {
    private final org.simpleframework.xml.core.Persister Persister;
    private static Converter instance;

    private Converter(){

        RegistryMatcher matchers = new RegistryMatcher();
        matchers.bind(ZonedDateTime.class, Transform_date.class);// создаем способ записи ZonedDateTime
        Strategy strategy = new AnnotationStrategy();
        Persister = new Persister( strategy , matchers );
    }

    public static Converter getInstance() {
        if(instance == null){
            instance = new Converter();
        }
        return instance;
    }
    public <T> String Write(T obj) {
        try {
            Writer writer = new StringWriter();
            Persister.write(obj, writer);
            return writer.toString();
        }
        catch (Exception e){
            return null;
        }
    }
    public <T> T Read(Class<? extends T> T, String str) {
        try {
            return Persister.read(T, str);
        }
        catch (Exception ignored){
            return null;
        }
    }

}
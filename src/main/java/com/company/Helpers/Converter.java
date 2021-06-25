package com.company.Helpers;

import com.company.Commands.Exist;
import com.company.Main;
import com.company.Models.Transform_date;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.*;
import java.time.ZonedDateTime;

public class Converter {
    private final org.simpleframework.xml.core.Persister Persister;

    public Converter(){

        RegistryMatcher matchers = new RegistryMatcher();
        matchers.bind(ZonedDateTime.class, Transform_date.class);// создаем способ записи ZonedDateTime
        Strategy strategy = new AnnotationStrategy();
        Persister = new Persister( strategy , matchers );
    }


    public Exist GetCommand(byte[] buffer){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            return (Exist) inputStream.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public byte[] GetResponce(com.company.Models.Writer responce){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(stream);
            outputStream.writeObject(responce);
            return stream.toByteArray();
        }
        catch (Exception e){
            Main.Printer.WriteLine(e.getMessage());
            return null;
        }
    }

    public <T> String Serialize(T obj) {
        try {
            Writer writer = new StringWriter();
            Persister.write(obj, writer);
            return writer.toString();
        }
        catch (Exception e){
            return null;
        }
    }
    public <T> T Deserialize(Class<T> ClassObject, String str) {
        try {
            return Persister.read(ClassObject, str);
        }
        catch (Exception ignored){
            return null;
        }
    }

}
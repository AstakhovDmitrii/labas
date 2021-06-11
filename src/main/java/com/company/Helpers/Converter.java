package com.company.Helpers;



import com.company.Commands.Exist;
import com.company.Models.Transform_date;
import com.company.Writers.Printer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.*;
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
    public <T> void write_to_file(T obj, String path){
        try {
            Persister.write(obj, new File(path));
        }
        catch (Exception ignored){

        }
    }
    public <T> T Read_file(Class<? extends T> T, String path) {
        try {
            return Persister.read(T, new File(path));
        }
        catch (Exception ignored){
            return null;
        }
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
            Printer.getInstance().WriteLine(e.getMessage());
            return null;
        }
    }

}

package com.company.Helpers;



import com.company.Commands.Exist;
import com.company.Interfaces.IConverter;
import com.company.Main;
import com.company.Models.Transform_date;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.*;
import java.time.ZonedDateTime;

public class Converter implements IConverter {
    private final org.simpleframework.xml.core.Persister Persister;

    public Converter(){
        RegistryMatcher matchers = new RegistryMatcher();
        matchers.bind(ZonedDateTime.class, Transform_date.class);// создаем способ записи ZonedDateTime
        Strategy strategy = new AnnotationStrategy();
        Persister = new Persister( strategy , matchers );
    }

    @Override
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


    @Override
    public <T> T Deserialize(Class<? extends T> T, String str) {
        try {
            return Persister.read(T, str);
        }
        catch (Exception ignored){
            return null;
        }
    }

    @Override
    public <T> void WriteToFile(T obj, String path){
        try {
            Persister.write(obj, new File(path));
        }
        catch (Exception e){
            Main.printer.WriteLine("ошибка в записи в файл");
        }
    }


    @Override
    public <T> T ReadFromFile(Class<? extends T> T, String path) {
        try {
            return Persister.read(T, new File(path));
        }
        catch (Exception e){
            return null;
        }
    }


    @Override
    public Exist DeSerializeCommand(byte[] buffer){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            return (Exist) inputStream.readObject();
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public byte[] SerializeResponce(com.company.Models.Writer responce){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(stream);
            outputStream.writeObject(responce);
            return stream.toByteArray();
        }
        catch (Exception e){
            Main.printer.WriteLine("ошибка в разборе обьекта");
            return null;
        }
    }

}

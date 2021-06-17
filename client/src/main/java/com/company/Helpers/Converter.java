package com.company.Helpers;


import com.company.Command;
import com.company.Interfaces.IConverter;
import com.company.Main;
import com.company.Models.Transform_date;
import com.company.Writes.Printer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.*;
import java.time.ZonedDateTime;

public class Converter implements IConverter {
    private final Persister Persister;

    public Converter(){
        RegistryMatcher matchers = new RegistryMatcher();
        matchers.bind(ZonedDateTime.class, Transform_date.class);
        Strategy strategy = new AnnotationStrategy();
        Persister = new Persister( strategy , matchers );
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

    public com.company.Models.Writer DeSerializeResponce(byte[] buffer){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            return (com.company.Models.Writer) inputStream.readObject();
        }
        catch (Exception e){
            Main.printer.WriteLine(e.getMessage());
            return null;
        }
    }
    public byte[] SerializeResponce(Command responce){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(stream);
            outputStream.writeObject(responce);
            return stream.toByteArray();
        }
        catch (Exception e){
            Main.printer.WriteLine(e.getMessage());
            return null;
        }
    }

}

package com.company.Writes;

import com.company.Interfaces.IPrinter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Printer implements IPrinter {
    private final PrintStream writer;
    private final Scanner scanner;


    public Printer(InputStream reader, PrintStream Writer) {
        writer = Writer;
        scanner = new Scanner(reader, StandardCharsets.UTF_8);
    }


    public String ReadLine(){
        return scanner.nextLine();
    }

    public void WriteLine(Object str){
        try {
            byte[] buffer = (str + "\r\n").getBytes(Charset.forName("windows-1251"));
            writer.write(buffer);
        }
        catch (IOException e){
            System.out.println("вывод прерван. Сообщение выведенно в стандартный поток\r\n");
        }
    }

    public void InvalidValue(){
        WriteLine("значение неверно");
    }
    public void Write(Object s){
        try {
            byte[] buffer = (s.toString()).getBytes(Charset.forName("windows-1251"));
            writer.write(buffer);
        }
        catch (IOException e){
            System.out.println("вывод прерван. Сообщение выведенно в стандартный поток\r\n");
        }
    }

}

package com.company.Writers;

import com.company.Interfaces.IPrinter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Printer implements IPrinter {
    public Scanner scanner;
    public PrintStream writer;

    public Printer(InputStream inputStream, PrintStream printStream){
        scanner = new Scanner(inputStream);
        writer = printStream;
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

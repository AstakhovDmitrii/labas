package com.company;

import com.company.Commands.Exist;
import com.company.Helpers.Converter;
import com.company.Models.*;
import com.company.Writes.Printer;
import com.company.Writes.Sender;
import org.reflections.Reflections;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Main {
    public static ArrayList<Command> commands = new ArrayList<>();
    public static String error = "поле введено неверно. Заменено на ";
    public static Printer printer;
    public static Converter converter;
    public static Sender sender;


    public static void main(String[] args) throws Exception {

        printer = new Printer(System.in, System.out);
        converter = new Converter();


        String ip = "";
        int host;
        while (true){
            try {
                printer.WriteLine("введите ip");
                ip = printer.ReadLine();
                InetAddress.getByName(ip);
                printer.WriteLine("введите host");
                host = Integer.parseInt(printer.ReadLine());
                break;
            }
            catch (Exception e){
                printer.WriteLine("неверно ввведены значения");
            }
        }


        try {
            for (Class<? extends Command> class1 : (new Reflections("com.company.Commands")).getSubTypesOf(Command.class)) {// получаем все классы наследуемые от command
                if(class1 != Exist.class) {
                    commands.add(class1.getConstructor().newInstance());// добавляем
                }
            }
        }
        catch (Exception ignored){

        }


        sender = new Sender(InetAddress.getByName(ip), host);

        while (true){
            String next = printer.ReadLine().trim();
            Command SendMsg = null;
            for (Command command : commands) {
                if (next.startsWith(command.getName()) || next.startsWith(command.getName().toLowerCase(Locale.ROOT))) {
                    command.args = new ArrayList<>(Arrays.asList(next.split(",")));
                    command.args.remove(0);
                    command.Execute();

                    SendMsg = new Exist();
                    SendMsg.setName(command.getName());
                    SendMsg.args = command.args;
                }
            }
            if(SendMsg == null){
                SendMsg = new Exist();
                SendMsg.setName(next.split(",")[0]);
                SendMsg.args = new ArrayList<>(Arrays.asList(next.split(",")));
                SendMsg.args.remove(0);
            }

            byte[] buffer = converter.SerializeResponce(SendMsg);
            sender.Send(buffer);


            byte[] a = sender.Recieve();
            Writer writer = converter.DeSerializeResponce(a);
            for (String str : writer.getResponces()) {
                printer.WriteLine(str);
            }
        }
    }
}

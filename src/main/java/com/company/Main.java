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
    public static String username = "";
    public static String password = "";
    public static Sender sender;
    public static Printer printer = new Printer(System.in, System.out);
    public static Converter converter = new Converter();


    public static void main(String[] args) throws Exception {

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

        printer.WriteLine("введите имя пользователя");
        username = printer.ReadLine();

        printer.WriteLine("введите пароль");
        password = printer.ReadLine();


        try {
            for (Class<? extends Command> class1 : (new Reflections("com.company.Commands")).getSubTypesOf(Command.class)) {// получаем все классы наследуемые от command
                if(class1 != Exist.class) {
                    commands.add(class1.getConstructor().newInstance());
                }
            }
        }
        catch (Exception ignored){

        }
        sender = new Sender(InetAddress.getByName(ip), host);

        while (true){
            String next = printer.ReadLine().trim();
            Command server_send = null;
            for (Command command : commands) {
                if (next.startsWith(command.getName()) || next.startsWith(command.getName().toLowerCase(Locale.ROOT))) {
                    command.args = new ArrayList<>(Arrays.asList(next.split(",")));
                    command.args.remove(0);
                    command.Execute();

                    server_send = new Exist();
                    server_send.setName(command.getName());
                    server_send.args = command.args;
                }
            }
            if(server_send == null){
                server_send = new Exist();
                server_send.setName(next.split(",")[0]);
                server_send.args = new ArrayList<>(Arrays.asList(next.split(",")));
                server_send.args.remove(0);
            }

            server_send.setPassword(password);
            server_send.setUsername(username);

            byte[] buff = converter.GetCommand(server_send);
            sender.Send(buff);


            byte[] a = sender.Recieve();
            Writer writer = converter.GetResponce(a);
            for (String str : writer.getResponces()) {
                printer.WriteLine(str);
            }
        }
    }
}

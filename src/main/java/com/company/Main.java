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


    public static void main(String[] args) throws Exception {

        String ip = "";
        int host;
        while (true){
            try {
                Printer.getInstance().WriteLine("введите ip");
                ip = Printer.getInstance().ReadLine();
                InetAddress.getByName(ip);
                Printer.getInstance().WriteLine("введите host");
                host = Integer.parseInt(Printer.getInstance().ReadLine());
                break;
            }
            catch (Exception e){
                Printer.getInstance().WriteLine("неверно ввведены значения");
            }
        }

        Printer.getInstance().WriteLine("введите имя пользователя");
        username = Printer.getInstance().ReadLine();

        Printer.getInstance().WriteLine("введите пароль");
        password = Printer.getInstance().ReadLine();

        try {
            for (Class<? extends Command> class1 : (new Reflections("com.company.Commands")).getSubTypesOf(Command.class)) {// получаем все классы наследуемые от command
                if(class1 != Exist.class) {
                    commands.add(class1.getConstructor().newInstance());// добавляем
                }
            }
        }
        catch (Exception ignored){

        }
        Sender.Init(InetAddress.getByName(ip), host);

        while (true){
                String next = Printer.getInstance().ReadLine().trim();
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

                String send_str = Converter.getInstance().Write(server_send);
                Sender.getInstance().Send(send_str);


                String a = Sender.getInstance().Recieve();
                Writer writer = Converter.getInstance().Read(Writer.class,a);
                for (String str : writer.getResponces()) {
                    Printer.getInstance().WriteLine(str);
                }
        }
    }
}

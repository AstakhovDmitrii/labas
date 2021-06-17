package com.company;

import com.company.Commands.*;
import com.company.Helpers.Converter;
import com.company.Helpers.Create;
import com.company.Interfaces.IPrinter;
import com.company.Interfaces.ISender;
import com.company.Models.Tickets;
import com.company.Models.Writer;
import com.company.Writers.Printer;
import com.company.Writers.Sender;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Main {


    public static ArrayList<Command> commands = new ArrayList<>();
    public static Tickets tickets = new Tickets();
    public static int ids = 0;
    public static LocalDateTime start;
    public static String ip = "192.168.71.1";
    public static int port = 1112;
    public static Thread console;
    public static org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);
    public static String path = "C:\\file.txt";
    public static Converter converter = new Converter();
    public static ISender sender;
    public static IPrinter printer = new Printer(System.in, System.out);

    public static void console_thread(){
        while (true){
            try {
                printer.WriteLine("введите команду");
                String next = printer.ReadLine().trim();
                boolean isCommand = false;
                Writer writer = new Writer();
                for (Command command : commands) {
                    if (next.startsWith(command.getName()) || next.toLowerCase(Locale.ROOT).startsWith(command.getName().toLowerCase(Locale.ROOT))) {
                        command.args.addAll(Arrays.asList(next.split(",")));
                        command.args.remove(0);
                        String CommandName = command.getName();
                        if((CommandName.startsWith("Insert") || CommandName.startsWith("Replace_if_greater") || CommandName.startsWith("Replace_if_lower") || CommandName.startsWith("Update")) && !CommandName.startsWith("Save")){
                            command.args.add(converter.Serialize(Create.Set_Fields()));
                        }
                        writer = command.Execute();
                        isCommand = true;
                        command.args.clear();
                    }
                }
                if (!isCommand) {
                    printer.WriteLine("нет такой команды");
                }
                for (String str : writer.getResponces()){
                    printer.WriteLine(str);
                }
            }
            catch (Exception e){
                printer.WriteLine("вы каким то образом поломали программу, что ж вы за человек");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {


        InetAddress address;
        try{
            address = InetAddress.getByName(System.getenv("address"));
        }
        catch (UnknownHostException e){
            address = InetAddress.getByName(ip);
        }

        sender = new Sender(port, address);

        printer.WriteLine(address);
        console = new Thread(Main::console_thread);
        console.start();

        start = LocalDateTime.now();
        logger.info("старт программы: " + start.toString());


        commands.add(new Clear());
        commands.add(new Count_less_than_price());
        commands.add(new Help());
        commands.add(new Info());
        commands.add(new Insert());
        commands.add(new Print_field_descending_person());
        commands.add(new Remove_greater_key());
        commands.add(new Remove_key());
        commands.add(new Replace_if_greater());
        commands.add(new Replace_if_lower());
        commands.add(new Save());
        commands.add(new Show());
        commands.add(new Sum_of_price());
        commands.add(new Update());

        logger.info("установленны все команды");

        if(args.length == 0){
            path = "C:\\file.txt";
        }
        else{
            path = args[0];
        }

        try{
            tickets = converter.ReadFromFile(Tickets.class, path);
            if(tickets == null){
                tickets = new Tickets();
            }
        }
        catch (Exception ignored){
            tickets = new Tickets();
        }


        logger.info("сервер запущен");


        try {
            while (true) {
                Command command = sender.Recieve();
                boolean is = false;
                Writer writer = new Writer();
                for (Command command1 : commands) {
                    if (command.getName().startsWith(command1.getName()) || command.getName().toLowerCase(Locale.ROOT).startsWith(command1.getName().toLowerCase(Locale.ROOT))) {
                        command1.args = command.args;
                        writer = command1.Execute();
                        is = true;
                    }
                }
                if (!is) {
                    writer.AddResponce("такой команды не существует");
                }
                writer.AddResponce("введите команду");

                sender.Send(writer);
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }

        try{
            converter.WriteToFile(tickets, args[0]);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }

        logger.info("сервер закончил работу");
    }
}

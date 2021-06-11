package com.company;

import com.company.Commands.*;
import com.company.Helpers.Converter;
import com.company.Helpers.Create;
import com.company.Models.Tickets;
import com.company.Models.Writer;
import com.company.Writers.MyLogger;
import com.company.Writers.Printer;
import org.reflections.Reflections;
import java.io.PrintStream;
import java.net.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Main {

    public static DatagramPacket recieve = new DatagramPacket(new byte[2048], 2048);
    public static DatagramPacket send;
    public static DatagramSocket server;
    public static ArrayList<Command> commands = new ArrayList<>();
    public static Writer writer = new Writer();
    public static Tickets tickets = new Tickets();
    public static int ids = 0;
    public static LocalDateTime start;
    public static String ip = "192.168.71.1";
    public static int port = 1112;
    public static Thread console;
    public static MyLogger logger;
    public static String path = "C:\\file.txt";

    public static void console_thread(){
        while (true){
            try {
                Printer.getInstance().WriteLine("введите команду");
                String next = Printer.getInstance().ReadLine().trim();
                boolean isCommand = false;
                for (Command command : commands) {
                    if (next.startsWith(command.getName()) || next.toLowerCase(Locale.ROOT).startsWith(command.getName().toLowerCase(Locale.ROOT))) {
                        command.args.addAll(Arrays.asList(next.split(",")));
                        command.args.remove(0);
                        String CommandName = command.getName();
                        if(CommandName.startsWith("Insert") || CommandName.startsWith("Replace_if_greater") || CommandName.startsWith("Replace_if_lower") || CommandName.startsWith("Update")){
                            command.args.add(Converter.getInstance().Write(Create.Set_Fields()));
                        }
                        command.Execute(true);
                        isCommand = true;
                        command.args.clear();
                    }
                }
                if (!isCommand) {
                    Printer.getInstance().WriteLine("нет такой команды");
                }
            }
            catch (Exception e){
                Printer.getInstance().WriteLine("вы каким то образом поломали программу, что ж вы за человек");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Printer.Init(System.in, new PrintStream(System.out, true, Charset.forName("windows-1251")));
        InetAddress address;
        try{
            address = InetAddress.getByName(System.getenv("address"));
        }
        catch (UnknownHostException e){
            address = InetAddress.getByName(ip);
        }
        try{
             logger = new MyLogger();
        }
        catch (Exception ignored){

        }
        Printer.getInstance().WriteLine(address);
        console = new Thread(Main::console_thread);
        console.start();

        start = LocalDateTime.now();
        logger.WriteLine("старт программы: " + start.toString());

        try {
            for (Class<? extends Command> class1 : (new Reflections("com.company.Commands")).getSubTypesOf(Command.class)) {// получаем все классы наследуемые от command
                if(class1 != Exist.class) {
                    commands.add(class1.getConstructor().newInstance());// добавляем
                }
            }
        }
        catch (Exception ignored){

        }
        logger.WriteLine("установленны все команды");

        if(args.length == 0){
            path = "C:\\file.txt";
        }
        else{
            path = args[0];
        }
        try{
            tickets = Converter.getInstance().Read_file(Tickets.class, path);
            if(tickets == null){
                tickets = new Tickets();
            }
        }
        catch (Exception ignored){
            tickets = new Tickets();
        }
        server = new DatagramSocket(port, address);
        logger.WriteLine("сервер запущен");
        try {
            while (true) {
                recieve = new DatagramPacket(new byte[2048], 2048);
                server.receive(recieve);
                logger.WriteLine("получено сообщение от клиента c ip: " + recieve.getAddress());
                System.out.println(new String(recieve.getData()));
                Command command = Converter.getInstance().GetCommand(recieve.getData());
                logger.WriteLine("текст сообщения: " + new String(recieve.getData()));
                boolean is = false;
                for (Command command1 : commands) {
                    if (command.getName().startsWith(command1.getName()) || command.getName().toLowerCase(Locale.ROOT).startsWith(command1.getName().toLowerCase(Locale.ROOT))) {
                        command1.args = command.args;
                        command1.Execute(false);
                        is = true;
                    }
                }
                if (!is) {
                    writer.AddResponce("такой команды не существует");
                }
                writer.AddResponce("введите команду");

                byte[] response = Converter.getInstance().GetResponce(writer);


                writer.getResponces().clear();
                send = new DatagramPacket(response, response.length, recieve.getAddress(), port-1);
                logger.WriteLine("отправлен ответ на: " + recieve.getAddress());
                server.send(send);
            }
        }
        catch (Exception e){

        }
        try {
            Converter.getInstance().write_to_file(tickets, args[0]);
            Printer.getInstance().Close();
        }
        catch (Exception ignored){

        }
        logger.WriteLine("сервер закончил работу");
    }
}

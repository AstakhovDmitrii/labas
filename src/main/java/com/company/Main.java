package com.company;

import com.company.Commands.*;
import com.company.Helpers.Converter;
import com.company.Helpers.Create;
import com.company.Models.*;
import com.company.Writers.Logger;
import com.company.Writers.Printer;
import org.reflections.Reflections;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;

public class Main {

    public static DatagramPacket recieve = new DatagramPacket(new byte[2048], 2048);
    public static DatagramPacket send;
    public static DatagramSocket server;
    public static ArrayList<Command> commands = new ArrayList<>();
    public static Writer writer = new Writer();
    public static Tickets tickets = new Tickets();
    public static int ids = 0;
    public static LocalDateTime start;
    public static String ip = "192.168.5.1";
    public static int port = 1112;
    public static Thread console;
    public static Logger logger;
    public  static user user = new user( "admin", "admin",0);
    public static DB db;

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
                        if(command.getClass() == Insert.class || command.getClass() == Replace_if_greater.class || command.getClass() == Replace_if_lower.class || command.getClass() == Update.class){
                            command.args.add(Converter.getInstance().Write(Create.Set_Fields()));
                        }
                        command.Execute(true, user);
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
        db = new DB("jdbc:postgresql://localhost:5432/db", "postgres","postgres");

        Printer.Init(System.in, new PrintStream(System.out, true, Charset.forName("windows-1251")));
        InetAddress address;
        try{
            address = InetAddress.getByName(System.getenv("address"));
        }
        catch (UnknownHostException e){
            address = InetAddress.getByName(ip);
        }
        address = InetAddress.getByName(ip);
        try{
             logger = new Logger(new FileOutputStream("C:\\log.txt"));
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

        try{
            for (keyset keyset : db.GetAllTicket()){
                tickets.getTickets().put(keyset.key, keyset.ticket);
            }
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
                String out = new String(recieve.getData(), StandardCharsets.UTF_16);
                Command command = Converter.getInstance().Read(Exist.class, out);
                logger.WriteLine("текст сообщения: " + out);
                boolean is = false;
                for (Command command1 : commands) {
                    if (command.getName().startsWith(command1.getName()) || command.getName().toLowerCase(Locale.ROOT).startsWith(command1.getName().toLowerCase(Locale.ROOT))) {
                        command1.args = command.args;
                        if(!command.getName().toLowerCase(Locale.ROOT).equals("register")) {
                            try {
                                command1.Execute(false, db.GetUser(command.getUsername(), command.getPassword()));
                            } catch (NoSuchElementException e) {
                                writer.getResponces().add("пользователя не существует");
                                e.printStackTrace();
                            }
                        }
                        else{
                            db.AddUser(new user(command.getUsername(), command.getPassword()));
                        }
                        is = true;
                    }
                }
                if (!is) {
                    writer.getResponces().add("такой команды не существует");
                }
                writer.getResponces().add("введите команду");

                byte[] response = Converter.getInstance().Write(writer).getBytes(StandardCharsets.UTF_16);
                writer.getResponces().clear();
                send = new DatagramPacket(response, response.length, recieve.getAddress(), port-1);
                logger.WriteLine("отправлен ответ на: " + recieve.getAddress() + ".Текст: " + Converter.getInstance().Write(writer));
                server.send(send);
            }
        }
        catch (Exception e){

        }
        try {
            Main.tickets.getTickets().forEach((s, ticket) -> Main.db.AddTicket(ticket, s));
            Printer.getInstance().Close();
        }
        catch (Exception ignored){

        }
        logger.WriteLine("сервер закончил работу");
        logger.log_steam.close();
    }
}

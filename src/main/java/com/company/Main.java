package com.company;

import com.company.Commands.*;
import com.company.Helpers.Converter;
import com.company.Helpers.Create;
import com.company.Models.*;
import com.company.Writers.Logger;
import com.company.Writers.Printer;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static ExecutorService service = Executors.newFixedThreadPool(3);
    public static DatagramPacket recieve = new DatagramPacket(new byte[2048], 2048);
    public static DatagramPacket send;
    public static DatagramSocket server;
    public static ArrayList<Command> commands = new ArrayList<>();
    public static Tickets tickets = new Tickets();
    public static LocalDateTime start;
    public static String ip = "192.168.5.1";
    public static int port = 1112;
    public static Thread console;
    public static org.slf4j.Logger logger= LoggerFactory.getLogger(Main.class);
    public  static user user = new user( "admin", "admin",0);
    public static DB db;
    public static Converter converter = new Converter();
    public static Printer printer = new Printer(System.in, System.out);

    public static void console_thread(){
        while (true){
            try {
                Main.printer.WriteLine("введите команду");
                String next = Main.printer.ReadLine().trim();
                boolean isCommand = false;
                Writer writer = new Writer();
                for (Command command : commands) {
                    if (next.startsWith(command.getName()) || next.toLowerCase(Locale.ROOT).startsWith(command.getName().toLowerCase(Locale.ROOT))) {
                        command.args.addAll(Arrays.asList(next.split(",")));
                        command.args.remove(0);
                        String NameCommand = command.getName();
                        if(NameCommand.toLowerCase(Locale.ROOT).startsWith("insert") || NameCommand.toLowerCase(Locale.ROOT).startsWith("replace_if_greater") || NameCommand.toLowerCase(Locale.ROOT).startsWith("replace_if_lower") || NameCommand.toLowerCase(Locale.ROOT).startsWith("update")){
                            command.args.add(converter.Serialize(Create.Set_Fields()));
                        }
                        writer = command.Execute(user);
                        isCommand = true;
                        command.args.clear();
                    }
                }
                for (String s : writer.getResponces()){
                    Main.printer.WriteLine(s);
                }
                if (!isCommand) {
                    Main.printer.WriteLine("нет такой команды");
                }
            }
            catch (Exception e){
                Main.printer.WriteLine("вы каким то образом поломали программу, что ж вы за человек");
                e.printStackTrace();
            }
        }
    }

    public static void send(){
        try {
            while (true) {
                recieve = new DatagramPacket(new byte[2048], 2048);
                server.receive(recieve);
                logger.info("получено сообщение от клиента c ip: " + recieve.getAddress() + recieve.getData());
                Exist command = converter.GetCommand(recieve.getData());
                boolean is = false;
                Writer writer = new Writer();
                for (Command command1 : commands) {
                    if (command.getName().startsWith(command1.getName()) || command.getName().toLowerCase(Locale.ROOT).startsWith(command1.getName().toLowerCase(Locale.ROOT))) {
                        command1.args = command.args;
                        if (!command.getName().toLowerCase(Locale.ROOT).equals("register")) {
                            try {
                                user user = db.GetUser(command.getUsername(), command.getPassword());
                                if (user != null) {
                                    writer = command1.Execute(user);
                                } else {
                                    writer.getResponces().add("пользователя не существует");
                                }
                            } catch (NoSuchElementException e) {
                                writer.getResponces().add("пользователя не существует");
                            }
                        } else {
                            db.AddUser(new user(command.getUsername(), command.getPassword()));
                        }
                        is = true;
                    }
                }
                if (!is) {
                    writer.getResponces().add("такой команды не существует");
                }
                System.out.println(recieve.getAddress());
                writer.getResponces().add("введите команду");
                byte[] response = converter.GetResponce(writer);
                writer.getResponces().clear();
                send = new DatagramPacket(response, response.length, recieve.getAddress(), port - 1);
                logger.info("отправлен ответ на: " + recieve.getAddress());
                Thread thread = new Thread(() -> {
                    try {
                        server.send(send);
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                });
                thread.start();
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        db = new DB("jdbc:postgresql://localhost:5432/db", "postgres","postgres");


        InetAddress address;
        try{
            address = InetAddress.getByName(System.getenv("address"));
        }
        catch (UnknownHostException e){
            logger.error("ip адекс не найден. Заменен на ip по умолчанию");
            address = InetAddress.getByName(ip);
        }



        Main.printer.WriteLine(address);
        console = new Thread(Main::console_thread);
        console.start();

        start = LocalDateTime.now();
        logger.info("старт программы: " + start.toString());

        try {
            for (Class<? extends Command> class1 : (new Reflections("com.company.Commands")).getSubTypesOf(Command.class)) {// получаем все классы наследуемые от command
                if(class1 != Exist.class) {
                    commands.add(class1.getConstructor().newInstance());// добавляем
                }
            }
        }
        catch (Exception e){
            logger.error("не все команды установлены верно");
        }
        logger.info("установленны все команды");

        try{
            for (keyset keyset : db.GetAllTicket()){
                tickets.getTickets().put(keyset.key, keyset.ticket);
            }
            if(tickets == null){
                tickets = new Tickets();
            }
        }
        catch (Exception e){
            logger.error("создан новый список, т.к бд недоступна");
            tickets = new Tickets();
        }
        server = new DatagramSocket(port, address);
        logger.info("сервер запущен");
        for (int i = 0; i < 4; i++)
            service.submit(Main::send);
    }
}

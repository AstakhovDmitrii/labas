package com.company;

import com.company.Helpers.CommandsController;
import com.company.Helpers.ConsoleThreadController;
import com.company.Helpers.Converter;
import com.company.Helpers.SendThreads;
import com.company.Models.*;
import com.company.Writers.Printer;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.time.LocalDateTime;

public class Main {
    public static DatagramPacket Recieve = new DatagramPacket(new byte[2048], 2048);
    public static DatagramPacket Send;
    public static DatagramSocket ServerSocket;
    public static Tickets Tickets = new Tickets();
    public static LocalDateTime StartProgram;
    public static String Ip = "192.168.5.1";
    public static int Port = 1112;
    public static ConsoleThreadController ControllerThread;
    public static SendThreads SendThreads;
    public static org.slf4j.Logger Logger = LoggerFactory.getLogger(Main.class);
    public  static user Admin = new user( "admin", "admin",0);
    public static DB DB;
    public static Converter Converter = new Converter();
    public static Printer Printer = new Printer(System.in, System.out);
    public static CommandsController controller = new CommandsController();


    public static void main(String[] args) throws Exception {
        DB = new DB("jdbc:postgresql://localhost:5432/db", "postgres","postgres");

        InetAddress address;
        try{
            address = InetAddress.getByName(System.getenv("address"));
        }
        catch (UnknownHostException e){
            Logger.error("ip адекс не найден. Заменен на ip по умолчанию");
            address = InetAddress.getByName(Ip);
        }


        Main.Printer.WriteLine(address);


        ControllerThread = new ConsoleThreadController();

        StartProgram = LocalDateTime.now();
        Logger.info("старт программы: " + StartProgram.toString());


        Logger.info("установленны все команды");

        try{
            for (keyset keyset : DB.GetAllTicket()){
                Tickets.getTickets().put(keyset.key, keyset.ticket);
            }
            if(Tickets == null){
                Tickets = new Tickets();
            }
        }
        catch (Exception e){
            Logger.error("создан новый список, т.к бд недоступна");
            Tickets = new Tickets();
        }
        ServerSocket = new DatagramSocket(Port, address);
        Logger.info("сервер запущен");
        SendThreads = new SendThreads();
    }
}

package com.company.Writers;

import com.company.Command;
import com.company.Interfaces.ISender;
import com.company.Main;
import com.company.Models.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender implements ISender {

    public DatagramPacket recieve = new DatagramPacket(new byte[2048], 2048);
    public DatagramPacket send;
    public int port;
    public DatagramSocket server;

    public Sender(int port, InetAddress address) {
        try {
            server = new DatagramSocket(port, address);
            this.port = port;
        }
        catch (Exception e){
            Main.printer.WriteLine("ошибка в создании сервера");
        }
    }

    public void Send(Writer writer){
        try {
            byte[] response = Main.converter.SerializeResponce(writer);
            send = new DatagramPacket(response, response.length, recieve.getAddress(), port - 1);
            Main.logger.info("отправлен ответ на: " + recieve.getAddress());
            server.send(send);
        }
        catch (Exception e){
            Main.printer.WriteLine("ошибка отправки");
        }
    }

    public Command Recieve(){
        try {
            recieve = new DatagramPacket(new byte[2048], 2048);
            server.receive(recieve);
            Main.logger.info("получено сообщение от клиента c ip: " + recieve.getAddress());
            System.out.println(new String(recieve.getData()));
            Command command = Main.converter.DeSerializeCommand(recieve.getData());
            Main.logger.info("текст сообщения: " + new String(recieve.getData()));
            return command;
        }
        catch (Exception e){
            Main.printer.WriteLine("сообщение не принято");
            return null;
        }
    }
}

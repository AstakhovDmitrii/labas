package com.company.Writes;

import com.company.Interfaces.ISender;
import com.company.Main;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class Sender implements ISender {
    public DatagramChannel socket;
    public InetAddress address_server;
    public int port_server;

    public Sender(InetAddress address, int port) {
        try {
            address_server = address;
            socket = DatagramChannel.open();
            port_server = port;
            socket.socket().bind(new InetSocketAddress(port));
        }
        catch (Exception ignored){
            System.out.println("1");
        }
    }


    @Override
    public byte[] Recieve(){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            socket.receive(buffer);
            return buffer.array();
        }
        catch (IOException ignored){
            Main.printer.WriteLine("ошибка принятия сообщения" + ignored.getMessage());
            return null;
        }
    }

    @Override
    public void Send(byte[] buff) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(buff);
        socket.send(buffer, new InetSocketAddress(address_server, port_server + 1));
    }

}

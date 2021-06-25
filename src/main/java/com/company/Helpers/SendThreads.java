package com.company.Helpers;

import com.company.Command;
import com.company.Commands.Exist;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Models.user;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendThreads implements ThreadController {
    ExecutorService ThreadControl = Executors.newFixedThreadPool(3);

    public SendThreads() {
        for (int i = 0; i < 4; i++)
            ThreadControl.submit(this::start);
    }

    @Override
    public void start() {
        try {
            while (true) {
                Main.Recieve = new DatagramPacket(new byte[2048], 2048);
                Main.ServerSocket.receive(Main.Recieve);
                Main.Logger.info("получено сообщение от клиента c ip: " + Main.Recieve.getAddress() + Main.Recieve.getData());
                Exist command = Main.Converter.GetCommand(Main.Recieve.getData());
                boolean is = false;
                Writer writer = new Writer();
                for (Command command1 : Main.controller.getCommands()) {
                    if (command.getName().startsWith(command1.getName()) || command.getName().toLowerCase(Locale.ROOT).startsWith(command1.getName().toLowerCase(Locale.ROOT))) {
                        command1.args = command.args;
                        if (!command.getName().toLowerCase(Locale.ROOT).equals("register")) {
                            try {
                                user user = Main.DB.GetUser(command.getUsername(), command.getPassword());
                                if (user != null) {
                                    writer = command1.Execute(user);
                                } else {
                                    writer.getResponces().add("пользователя не существует");
                                }
                            } catch (NoSuchElementException e) {
                                writer.getResponces().add("пользователя не существует");
                            }
                        } else {
                            Main.DB.AddUser(new user(command.getUsername(), command.getPassword()));
                        }
                        is = true;
                    }
                }
                if (!is) {
                    writer.getResponces().add("такой команды не существует");
                }
                System.out.println(Main.Recieve.getAddress());
                writer.getResponces().add("введите команду");
                byte[] response = Main.Converter.GetResponce(writer);
                writer.getResponces().clear();
                Main.Send = new DatagramPacket(response, response.length, Main.Recieve.getAddress(), Main.Port - 1);
                Main.Logger.info("отправлен ответ на: " + Main.Recieve.getAddress());
                Thread thread = new Thread(() -> {
                    try {
                        Main.ServerSocket.send(Main.Send);
                    } catch (IOException e) {
                        Main.Logger.error(e.getMessage());
                    }
                });
                thread.start();
            }
        }
        catch (Exception e){
            Main.Logger.error(e.getMessage());
        }
    }
}

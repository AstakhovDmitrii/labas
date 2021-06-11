package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Writers.Printer;

public class Remove_key  extends Command {
    @Override
    public void Execute(boolean isServerSend) {
        if(args.size() == 1){
            if(Main.tickets.getTickets().remove(args.get(0)) != null){
                if(isServerSend){
                    Printer.getInstance().WriteLine("удаление успешно");
                }
                else {
                    Main.writer.AddResponce("удаление успешно");
                }
            }
            else{
                if(isServerSend){
                    Printer.getInstance().WriteLine("удаление не удалось");
                }
                else {
                    Main.writer.AddResponce("удаление не удалось");
                }
            }
        }
        else{
            if(isServerSend){
                Printer.getInstance().WriteLine("неверное кол-во аргументов");
            }
            else {
                Main.writer.AddResponce("неверное кол-во аргументов");
            }
        }
    }
}

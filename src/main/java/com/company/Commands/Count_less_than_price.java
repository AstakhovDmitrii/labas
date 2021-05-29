package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.user;
import com.company.Writers.Printer;

import java.util.ArrayList;

public class Count_less_than_price extends Command {
    @Override
    public void Execute(boolean is_thread, user user) {
        if(args.size() == 1){
            try {
                int count = 0;
                int price = Integer.parseInt(args.get(0).trim());
                for (Ticket ticket : Main.tickets.getTickets().values()){
                    if(ticket.getPrice() < price ){
                        count++;
                    }
                }
                if(is_thread){
                    Printer.getInstance().WriteLine("количество " + count);
                }
                else {
                    Main.writer.getResponces().add("количество " + count);
                }
            }
            catch (NumberFormatException e){
                if(is_thread){
                    Printer.getInstance().WriteLine("неправильно введено число");
                }
                else {
                    Main.writer.getResponces().add("неправильно введено число");
                }
            }
        }
        else{
            if(is_thread){
                Printer.getInstance().WriteLine("неверное кол-во аргументов");
            }
            else {
                Main.writer.getResponces().add("неверное кол-во аргументов");
            }
        }
    }
}
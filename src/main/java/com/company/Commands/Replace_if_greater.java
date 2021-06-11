package com.company.Commands;

import com.company.Command;
import com.company.Helpers.Converter;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Writers.Printer;

public class Replace_if_greater  extends Command {
    @Override
    public void Execute(boolean isServerSend) {
        if(args.size() == 2){
            Ticket ticket = Converter.getInstance().Read(Ticket.class, args.get(1));
            if(Main.tickets.getTickets().get(args.get(0)) != null) {
                if (Main.tickets.getTickets().get(args.get(0)).compareTo(ticket) > 0) {
                    Main.tickets.getTickets().replace(args.get(0), ticket);
                    if(isServerSend){
                        Printer.getInstance().WriteLine("успех");
                    }
                    else {
                        Main.writer.AddResponce("успех");
                    }
                }
                else{
                    if(isServerSend){
                        Printer.getInstance().WriteLine("неудача");
                    }
                    else {
                        Main.writer.AddResponce("неудача");
                    }
                }
            }
            else{
                if(isServerSend){
                    Printer.getInstance().WriteLine("такого нет");
                }
                else {
                    Main.writer.AddResponce("такого нет");
                }
            }
        }
        else{
            if(isServerSend){
                Printer.getInstance().WriteLine("неверное кол-во аргуметов");
            }
            else {
                Main.writer.AddResponce("неверное кол-во аргуметов");
            }
        }
    }
}

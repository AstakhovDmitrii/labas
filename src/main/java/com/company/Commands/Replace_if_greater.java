package com.company.Commands;

import com.company.Command;
import com.company.Helpers.Converter;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Replace_if_greater  extends Command {
    @Override
    public void Execute(boolean is_thread, user user) {
        if(args.size() == 2){
            Ticket ticket = Converter.getInstance().Read(Ticket.class, args.get(1));
            if(Main.tickets.getTickets().get(args.get(0)) != null) {
                if (Main.tickets.getTickets().get(args.get(0)).compareTo(ticket) > 0 && (Main.tickets.getTickets().get(args.get(0)).getCreate() == user.getId() || user.getId() == 0)) {
                    Main.tickets.getTickets().replace(args.get(0), ticket);
                    if(is_thread){
                        Printer.getInstance().WriteLine("успех");
                    }
                    else {
                        Main.writer.getResponces().add("успех");
                    }
                }
                else{
                    if(is_thread){
                        Printer.getInstance().WriteLine("неудача");
                    }
                    else {
                        Main.writer.getResponces().add("неудача");
                    }
                }
            }
            else{
                if(is_thread){
                    Printer.getInstance().WriteLine("такого нет");
                }
                else {
                    Main.writer.getResponces().add("такого нет");
                }
            }
        }
        else{
            if(is_thread){
                Printer.getInstance().WriteLine("неверное кол-во аргуметов");
            }
            else {
                Main.writer.getResponces().add("неверное кол-во аргуметов");
            }
        }
    }
}

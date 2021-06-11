package com.company.Commands;

import com.company.Command;
import com.company.Helpers.Converter;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Writers.Printer;

public class Insert extends Command {
    @Override
    public void Execute(boolean isServerSend) throws Exception {
        if(args.size() >= 2) {
            Main.tickets.getTickets().put(args.get(0), Converter.getInstance().Read(Ticket.class, args.get(1)));
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

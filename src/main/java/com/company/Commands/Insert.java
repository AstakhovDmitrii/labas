package com.company.Commands;

import com.company.Command;
import com.company.Helpers.Converter;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Insert extends Command {
    @Override
    public void Execute(boolean is_thread, user user, Writer writer) throws Exception {
        if(args.size() >= 2) {
            Ticket ticket = Converter.getInstance().Read(Ticket.class, args.get(1));
            ticket.setCreate(user.getId());
            Main.tickets.getTickets().put(args.get(0), ticket);
        }
        else{
            if(is_thread){
                Printer.getInstance().WriteLine("неверное кол-во аргументов");
            }
            else {
                writer.getResponces().add("неверное кол-во аргументов");
            }
        }
    }
}

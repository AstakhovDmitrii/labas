package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;

public class Insert extends Command {
    @Override
    public Writer Execute() throws Exception {
        Writer writer = new Writer();
        if(args.size() >= 2) {
            Main.tickets.getTickets().put(args.get(0), Main.converter.Deserialize(Ticket.class, args.get(1)));
        }
        else{
            writer.AddResponce("неверное кол-во аргументов");
        }
        return writer;
    }
}

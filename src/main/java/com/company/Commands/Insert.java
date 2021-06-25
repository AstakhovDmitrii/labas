package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;

public class Insert extends Command {
    @Override
    public Writer Execute(user user) throws Exception {
        Writer writer = new Writer();
        if(args.size() >= 2) {
            Ticket ticket = Main.Converter.Deserialize(Ticket.class, args.get(1));
            ticket.setCreate(user.getId());
            Main.Tickets.getTickets().put(args.get(0), ticket);
        }
        else{
                writer.getResponces().add("неверное кол-во аргументов");
        }
        return writer;
    }
}

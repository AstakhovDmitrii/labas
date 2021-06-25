package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;

public class Show extends Command{
    @Override
    public Writer Execute(user user) throws Exception {
        Writer writer = new Writer();
        if(Main.Tickets.getTickets().size() != 0) {
            for (Ticket ticket : Main.Tickets.getTickets().values()) {
                writer.AddResponce(ticket.toString());
            }
        }
        else{
            writer.AddResponce("нет ни одного элмента");
        }
        return writer;
    }
}

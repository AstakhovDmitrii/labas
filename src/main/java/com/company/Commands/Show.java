package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Show extends Command{
    @Override
    public Writer Execute(user user) throws Exception {
        Writer writer = new Writer();
        if(Main.tickets.getTickets().size() != 0) {
            for (Ticket ticket : Main.tickets.getTickets().values()) {
                writer.AddResponce(ticket.toString());
            }
        }
        else{
            writer.AddResponce("нет ни одного элмента");
        }
        return writer;
    }
}

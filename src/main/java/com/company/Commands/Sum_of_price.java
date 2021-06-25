package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;

public class Sum_of_price extends Command {
    @Override
    public Writer Execute(user user) {
        Writer writer = new Writer();
        float count = 0;
        for (Ticket ticket: Main.Tickets.getTickets().values()) {
            count += ticket.getPrice();
        }
        writer.AddResponce(String.valueOf(count));
        return writer;
    }
}

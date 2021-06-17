package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Writers.Printer;

public class Sum_of_price extends Command {
    @Override
    public Writer Execute() {
        Writer writer = new Writer();
        float count = 0;
        for (Ticket ticket: Main.tickets.getTickets().values()) {
            count += ticket.getPrice();
        }
        writer.AddResponce(String.valueOf(count));
        return writer;
    }
}

package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Sum_of_price extends Command {
    @Override
    public void Execute(boolean is_thread, user user, Writer writer) {
        float count = 0;
        for (Ticket ticket: Main.tickets.getTickets().values()) {
            count += ticket.getPrice();
        }
        if(is_thread){
            Printer.getInstance().WriteLine(String.valueOf(count));
        }
        else {
            writer.getResponces().add(String.valueOf(count));
        }
    }
}

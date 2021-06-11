package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Writers.Printer;

public class Sum_of_price extends Command {
    @Override
    public void Execute(boolean isServerSend) {
        float count = 0;
        for (Ticket ticket: Main.tickets.getTickets().values()) {
            count += ticket.getPrice();
        }
        if(isServerSend){
            Printer.getInstance().WriteLine(String.valueOf(count));
        }
        else {
            Main.writer.AddResponce(String.valueOf(count));
        }
    }
}

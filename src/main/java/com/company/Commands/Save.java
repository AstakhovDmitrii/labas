package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;

import java.util.function.BiConsumer;

public class Save extends Command {
    @Override
    public void Execute(boolean is_thread, user user, Writer writer) throws Exception {
        if(is_thread){
            Main.db.DeleteAllTickets();
            Main.tickets.getTickets().forEach((s, ticket) -> Main.db.AddTicket(ticket, s));
        }
    }
}

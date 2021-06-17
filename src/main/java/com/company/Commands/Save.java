package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;

import java.util.function.BiConsumer;

public class Save extends Command {
    @Override
    public Writer Execute(user user) throws Exception {
        Writer writer = new Writer();
        if(user.getId() == 0) {
            Main.db.DeleteAllTickets();
            Main.tickets.getTickets().forEach((s, ticket) -> Main.db.AddTicket(ticket, s));
            writer.AddResponce("все значения удалены");
        }
        else{
            writer.AddResponce("вы не можете получить доступ");
        }
        return writer;
    }
}

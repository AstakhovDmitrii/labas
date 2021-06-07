package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Clear extends Command {
    @Override
    public void Execute(boolean is_thread, user user, Writer writer) throws Exception {
        if(user.getId() == 0) {
            Main.tickets.getTickets().clear();
        }
        else{
            ArrayList<String> tickets = new ArrayList<>();
            Main.tickets.getTickets().forEach((s, ticket) -> {
                if(ticket.getCreate() == user.getId()){
                    tickets.add(s);
                }
            });
            for (String ticket : tickets){
                Main.tickets.getTickets().remove(ticket);
            }
        }
        if(is_thread){
            Printer.getInstance().WriteLine("список очищен");
        }
        else {
            writer.getResponces().add("список очищен");
        }
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}

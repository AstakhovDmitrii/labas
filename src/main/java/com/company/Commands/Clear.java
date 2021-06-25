package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Models.user;

public class Clear extends Command {// ни одна команда не используется напрямую. То есть их получение происходит в момент исполнения
    @Override
    public Writer Execute(user user) throws Exception {
        Writer writer = new Writer();
        if(user.getId() != 0) {
            for (String key : Main.Tickets.getTickets().keySet()) {
                if (Main.Tickets.getTickets().get(key).getCreate() == user.getId()) {
                    Main.Tickets.getTickets().remove(key);
                }
            }
        }
        else{
            Main.Tickets.getTickets().clear();
        }
        writer.AddResponce("список очищен");
        return writer;
    }
}

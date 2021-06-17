package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Clear extends Command {// ни одна команда не используется напрямую. То есть их получение происходит в момент исполнения
    @Override
    public Writer Execute(user user) throws Exception {
        Writer writer = new Writer();
        if(user.getId() != 0) {
            for (String key : Main.tickets.getTickets().keySet()) {
                if (Main.tickets.getTickets().get(key).getCreate() == user.getId()) {
                    Main.tickets.getTickets().remove(key);
                }
            }
        }
        else{
            Main.tickets.getTickets().clear();
        }
        writer.AddResponce("список очищен");
        return writer;
    }
}

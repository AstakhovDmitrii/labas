package com.company.Commands;

import com.company.Command;
import com.company.Helpers.Converter;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;

import java.math.MathContext;

public class Replace_if_lower extends Command {
    @Override
    public Writer Execute(user user) {
        Writer writer = new Writer();
        if(args.size() == 2){
            Ticket ticket = Main.converter.Deserialize(Ticket.class, args.get(1));
            if(Main.tickets.getTickets().get(args.get(0)).getCreate() == user.getId()) {
                if (Main.tickets.getTickets().get(args.get(0)) != null) {
                    if (Main.tickets.getTickets().get(args.get(0)).compareTo(ticket) < 0) {
                        Main.tickets.getTickets().replace(args.get(0), ticket);
                        writer.AddResponce("успех");
                    } else {
                        writer.AddResponce("неудача");
                    }
                } else {
                    writer.AddResponce("такого нет");
                }
            }
        }
        else{
            writer.AddResponce("неверное кол-во аргументов");
        }
        return writer;
    }
}

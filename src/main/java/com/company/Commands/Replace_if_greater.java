package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;

public class Replace_if_greater  extends Command {
    @Override
    public Writer Execute(user user) {
        Writer writer = new Writer();
        if(args.size() == 2){
            Ticket ticket = Main.Converter.Deserialize(Ticket.class, args.get(1));
            if(Main.Tickets.getTickets().get(args.get(0)).getCreate() == user.getId()) {
                if (Main.Tickets.getTickets().get(args.get(0)) != null) {
                    if (Main.Tickets.getTickets().get(args.get(0)).compareTo(ticket) > 0) {
                        Main.Tickets.getTickets().replace(args.get(0), ticket);
                        writer.AddResponce("успех");
                    } else {
                        writer.AddResponce("неудача");
                    }
                }
                else{
                    writer.AddResponce("такого нет");
                }
            }
            else{
                writer.AddResponce("этот обьект не ваш");
            }
        }
        else{
            writer.AddResponce("неверное кол-во аргуметов");
        }
        return writer;
    }
}

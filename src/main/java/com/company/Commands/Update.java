package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;

import java.util.Map;

public class Update extends Command {
    @Override
    public Writer Execute() {
        Writer writer = new Writer();
        if(args.size() == 2){
            for (Map.Entry<String, Ticket> t : Main.tickets.getTickets().entrySet()) {
                if(t.getValue().getId().equals(Integer.parseInt(args.get(0)))){
                    Main.tickets.getTickets().replace(t.getKey(), Main.converter.Deserialize(Ticket.class, args.get(1)));
                    writer.AddResponce("успех");
                    break;
                }
            }
        }
        else{
            writer.AddResponce("неверное кол-во аргументов");
        }
        return writer;
    }
}

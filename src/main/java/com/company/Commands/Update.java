package com.company.Commands;

import com.company.Command;
import com.company.Helpers.Converter;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

import java.util.Map;

public class Update extends Command {
    @Override
    public void Execute(boolean is_thread, user user, Writer writer) {
        if(args.size() == 2){
            for (Map.Entry<String, Ticket> t : Main.tickets.getTickets().entrySet()) {
                if(t.getValue().getId().equals(Integer.parseInt(args.get(0))) && (user.getId() == t.getValue().getCreate() || user.getId() == 0)){
                    Main.tickets.getTickets().replace(t.getKey(), Converter.getInstance().Read(Ticket.class, args.get(1)));
                    if(is_thread){
                        Printer.getInstance().WriteLine("успех");
                    }
                    else {
                        writer.getResponces().add("успех");
                    }
                    break;
                }
            }
        }
        else{
            if(is_thread){
                Printer.getInstance().WriteLine("неверное кол-во аргументов");
            }
            else {
                writer.getResponces().add("неверное кол-во аргументов");
            }
        }
    }
}

package com.company.Commands;

import com.company.Command;
import com.company.Helpers.Converter;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Writers.Printer;

import java.util.Map;

public class Update extends Command {
    @Override
    public void Execute(boolean isServerSend) {
        if(args.size() == 2){
            for (Map.Entry<String, Ticket> t : Main.tickets.getTickets().entrySet()) {
                if(t.getValue().getId().equals(Integer.parseInt(args.get(0)))){
                    Main.tickets.getTickets().replace(t.getKey(), Converter.getInstance().Read(Ticket.class, args.get(1)));
                    if(isServerSend){
                        Printer.getInstance().WriteLine("успех");
                    }
                    else {
                        Main.writer.AddResponce("успех");
                    }
                    break;
                }
            }
        }
        else{
            if(isServerSend){
                Printer.getInstance().WriteLine("неверное кол-во аргументов");
            }
            else {
                Main.writer.AddResponce("неверное кол-во аргументов");
            }
        }
    }
}

package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Writers.Printer;

public class Show extends Command{
    @Override
    public void Execute(boolean isServerSend) throws Exception {
        if(Main.tickets.getTickets().size() != 0) {
            for (Ticket ticket : Main.tickets.getTickets().values()) {
                if(isServerSend){
                    Printer.getInstance().WriteLine(ticket.toString());
                }
                else {
                    Main.writer.AddResponce(ticket.toString());
                }
            }
        }
        else{
            if(isServerSend){
                Printer.getInstance().WriteLine("нет ни одного элмента");
            }
            else {
                Main.writer.AddResponce("нет ни одного элмента");
            }
        }
    }
}

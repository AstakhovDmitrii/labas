package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Show extends Command{
    @Override
    public void Execute(boolean is_thread, user user) throws Exception {
        if(Main.tickets.getTickets().size() != 0) {
            for (Ticket ticket : Main.tickets.getTickets().values()) {
                if(is_thread){
                    Printer.getInstance().WriteLine(ticket.toString());
                }
                else {
                    Main.writer.getResponces().add(ticket.toString());
                }
            }
        }
        else{
            if(is_thread){
                Printer.getInstance().WriteLine("нет ни одного элмента");
            }
            else {
                Main.writer.getResponces().add("нет ни одного элмента");
            }
        }
    }
}

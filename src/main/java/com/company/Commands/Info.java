package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Writers.Printer;

public class Info extends Command {
    @Override
    public void Execute(boolean isServerSend)  {
        if(isServerSend){
            Printer.getInstance().WriteLine("начало старта: " + Main.start.toString() + "\r\nкол-во элементов: " + Main.tickets.getTickets().size());
        }
        else {
            Main.writer.AddResponce("начало старта: " + Main.start.toString() + "\r\nкол-во элементов: " + Main.tickets.getTickets().size());
        }
    }
}

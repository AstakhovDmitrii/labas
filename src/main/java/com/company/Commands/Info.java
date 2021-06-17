package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Info extends Command {
    @Override
    public Writer Execute(user user)  {
        Writer writer = new Writer();
        writer.AddResponce("начало старта: " + Main.start.toString() + "\r\nкол-во элементов: " + Main.tickets.getTickets().size());
        return writer;
    }
}

package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Models.user;

public class Info extends Command {
    @Override
    public Writer Execute(user user)  {
        Writer writer = new Writer();
        writer.AddResponce("начало старта: " + Main.StartProgram.toString() + "\r\nкол-во элементов: " + Main.Tickets.getTickets().size());
        return writer;
    }
}

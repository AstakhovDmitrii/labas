package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Writers.Printer;

public class Clear extends Command {// ни одна команда не используется напрямую. То есть их получение происходит в момент исполнения
    @Override
    public Writer Execute() throws Exception {
        Writer writer = new Writer();
        Main.tickets.getTickets().clear();
        writer.AddResponce("список очищен");
        return writer;
    }
}

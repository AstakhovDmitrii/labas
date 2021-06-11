package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Writers.Printer;

public class Clear extends Command {// ни одна команда не используется напрямую. То есть их получение происходит в момент исполнения
    @Override
    public void Execute(boolean isServerSend) throws Exception {
        Main.tickets.getTickets().clear();
        if(isServerSend){
            Printer.getInstance().WriteLine("список очищен");
        }
        else {
            Main.writer.AddResponce("список очищен");
        }
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}

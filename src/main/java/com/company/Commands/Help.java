package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Writers.Printer;

public class Help extends Command {
    @Override
    public void Execute(boolean isServerSend) {
        for (Command a: Main.commands) {
            if(isServerSend){
                Printer.getInstance().WriteLine("команда: " + a.getName());
            }
            else {
                Main.writer.AddResponce("команда: " + a.getName());
            }
        }
    }
}

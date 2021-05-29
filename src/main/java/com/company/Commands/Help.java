package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Help extends Command {
    @Override
    public void Execute(boolean is_thread, user user) {
        for (Command a: Main.commands) {
            if(is_thread){
                Printer.getInstance().WriteLine("команда: " + a.getName());
            }
            else {
                Main.writer.getResponces().add("команда: " + a.getName());
            }
        }
    }
}

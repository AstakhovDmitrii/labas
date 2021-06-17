package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Help extends Command {
    @Override
    public Writer Execute(user user) {
        Writer writer = new Writer();
        for (Command a: Main.commands) {
            writer.AddResponce("команда: " + a.getName());
        }
        return writer;
    }
}

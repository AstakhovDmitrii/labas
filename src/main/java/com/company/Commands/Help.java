package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Writers.Printer;

public class Help extends Command {
    @Override
    public Writer Execute() {
        Writer writer = new Writer();
        for (Command a: Main.commands) {
            writer.AddResponce("команда: " + a.getName());
        }
        return writer;
    }
}

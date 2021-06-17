package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;

public class Save extends Command {
    @Override
    public Writer Execute() throws Exception {
        Main.converter.WriteToFile(Main.tickets, Main.path);
        return null;
    }
}

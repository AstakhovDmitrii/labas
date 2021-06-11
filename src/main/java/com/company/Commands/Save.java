package com.company.Commands;

import com.company.Command;
import com.company.Helpers.Converter;
import com.company.Main;

public class Save extends Command {
    @Override
    public void Execute(boolean isServerSend) throws Exception {
        if(isServerSend){
            Converter.getInstance().write_to_file(Main.tickets, Main.path);
        }
    }
}

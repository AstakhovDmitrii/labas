package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Writers.Printer;

public class Remove_key  extends Command {
    @Override
    public Writer Execute() {
        Writer writer = new Writer();
        if(args.size() == 1){
            if(Main.tickets.getTickets().remove(args.get(0)) != null){
                writer.AddResponce("удаление успешно");
            }
            else{
                writer.AddResponce("удаление не удалось");
            }
        }
        else{
            writer.AddResponce("неверное кол-во аргументов");
        }
        return writer;
    }
}

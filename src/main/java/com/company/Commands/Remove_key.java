package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Models.user;

public class Remove_key  extends Command {
    @Override
    public Writer Execute(user user) {
        Writer writer = new Writer();
        if(args.size() == 1){
            if(Main.Tickets.getTickets().get(args.get(0)).getCreate() == user.getId()) {
                if (Main.Tickets.getTickets().remove(args.get(0)) != null) {
                    writer.AddResponce("удаление успешно");
                } else {
                    writer.AddResponce("удаление не удалось");
                }
            }
            else{
                writer.AddResponce("этот обьект создали не вы");
            }
        }
        else{
            writer.AddResponce("неверное кол-во аргументов");
        }
        return writer;
    }
}

package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;
import com.company.Models.user;

public class Remove_greater_key  extends Command {
    @Override
    public Writer Execute(user user) {
        Writer writer = new Writer();
        try {
            if (args.size() == 1) {
                for (String str : Main.Tickets.getTickets().keySet()) {// получаем все ключи
                    if (args.get(0).compareTo(str) > 0 && Main.Tickets.getTickets().get(str).getCreate() == user.getId()) {//удаляем если ключ больше
                        Main.Tickets.getTickets().remove(str);
                        writer.AddResponce("успех");
                    }
                }
            } else {
                writer.AddResponce("неврное клд-во аргументов");
            }
        }
        catch (Exception we){
            writer.AddResponce(we.getMessage());
        }
        return writer;
    }
}

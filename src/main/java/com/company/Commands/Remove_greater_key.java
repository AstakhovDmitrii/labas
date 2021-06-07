package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Remove_greater_key  extends Command {
    @Override
    public void Execute(boolean is_thread, user user, Writer writer) {
        try {
            if (args.size() == 1) {
                ArrayList<String> keys = new ArrayList<>();
                if (user.getId() != 0) {
                    Main.tickets.getTickets().forEach((s, ticket) -> {
                        if (ticket.getCreate() == user.getId())
                            keys.add(s);
                    });
                }
                else{
                    keys.addAll(Main.tickets.getTickets().keySet());
                }
                for (String str : keys) {// получаем все ключи
                    if (args.get(0).compareTo(str) > 0) {//удаляем если ключ больше
                        Main.tickets.getTickets().remove(str);
                        if(is_thread){
                            Printer.getInstance().WriteLine("успех");
                        }
                        else {
                            writer.getResponces().add("успех");
                        }
                    }
                }
            } else {
                if(is_thread){
                    Printer.getInstance().WriteLine("неврное клд-во аргументов");
                }
                else {
                    writer.getResponces().add("неврное клд-во аргументов");
                }
            }
        }
        catch (Exception we){
            writer.getResponces().add(we.getMessage());
        }
    }
}

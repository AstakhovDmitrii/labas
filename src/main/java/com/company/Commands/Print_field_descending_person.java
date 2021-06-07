package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

import java.util.Arrays;
import java.util.Comparator;

public class Print_field_descending_person  extends Command {
    @Override
    public void Execute(boolean is_thread, user user, Writer writer) {
        try {
            Object[] arrays = Main.tickets.getTickets().values().toArray();
            Arrays.sort(arrays, Comparator.comparing(o -> ((Ticket)o)));// сортируем массив по убыванию
            for (Object ticket : arrays) {// выводим элементы
                if(is_thread){
                    Printer.getInstance().WriteLine(ticket.toString());
                }
                else {
                    writer.getResponces().add(ticket.toString());
                }
            }
        }
        catch (Exception e){
            writer.getResponces().add(e.getMessage());
        }
    }
}

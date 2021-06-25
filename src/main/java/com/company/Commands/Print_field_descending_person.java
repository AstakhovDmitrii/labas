package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;

import java.util.Arrays;
import java.util.Comparator;

public class Print_field_descending_person  extends Command {
    @Override
    public Writer Execute(user user) {
        Writer writer = new Writer();
        try {
            Object[] arrays = Main.Tickets.getTickets().values().toArray();
            Arrays.sort(arrays, Comparator.comparing(o -> ((Ticket)o)));// сортируем массив по убыванию
            for (Object ticket : arrays) {// выводим элементы
                writer.AddResponce(ticket.toString());
            }
        }
        catch (Exception e){
            writer.AddResponce(e.getMessage());
        }
        return writer;
    }
}

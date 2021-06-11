package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Writers.Printer;

import java.util.Arrays;
import java.util.Comparator;

public class Print_field_descending_person  extends Command {
    @Override
    public void Execute(boolean isServerSend) {
        try {
            Object[] arrays = Main.tickets.getTickets().values().toArray();
            Arrays.sort(arrays, Comparator.comparing(o -> ((Ticket)o)));// сортируем массив по убыванию
            for (Object ticket : arrays) {// выводим элементы
                if(isServerSend){
                    Printer.getInstance().WriteLine(ticket.toString());
                }
                else {
                    Main.writer.AddResponce(ticket.toString());
                }
            }
        }
        catch (Exception e){
            Main.writer.AddResponce(e.getMessage());
        }
    }
}

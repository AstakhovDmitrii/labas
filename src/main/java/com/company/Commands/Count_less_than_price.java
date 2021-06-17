package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Models.Writer;
import com.company.Models.user;
import com.company.Writers.Printer;

public class Count_less_than_price extends Command {
    @Override
    public Writer Execute(user user){
        Writer writer = new Writer();
        if(args.size() == 1){
            try {
                int count = 0;
                int price = Integer.parseInt(args.get(0).trim());// trim нужен чтобы убрать пробелы сначала и с конца
                for (Ticket ticket : Main.tickets.getTickets().values()){
                    if(ticket.getPrice() < price ){
                        count++;
                    }
                }
                writer.AddResponce("количество " + count);
            }
            catch (NumberFormatException e){
                writer.AddResponce("неправильно введено число");
            }
        }
        else{
             writer.AddResponce("неверное кол-во аргументов");
        }
        return writer;
    }
}
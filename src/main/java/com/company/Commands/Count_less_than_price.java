package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Models.Ticket;
import com.company.Writers.Printer;

public class Count_less_than_price extends Command {
    @Override
    public void Execute(boolean isServerSend) {
        if(args.size() == 1){
            try {
                int count = 0;
                int price = Integer.parseInt(args.get(0).trim());// trim нужен чтобы убрать пробелы сначала и с конца
                for (Ticket ticket : Main.tickets.getTickets().values()){
                    if(ticket.getPrice() < price ){
                        count++;
                    }
                }
                if(isServerSend){
                    Printer.getInstance().WriteLine("количество " + count);
                }
                else {
                    Main.writer.AddResponce("количество " + count);
                }
            }
            catch (NumberFormatException e){
                if(isServerSend){
                    Printer.getInstance().WriteLine("неправильно введено число");
                }
                else {
                    Main.writer.AddResponce("неправильно введено число");
                }
            }
        }
        else{
            if(isServerSend){
                Printer.getInstance().WriteLine("неверное кол-во аргументов");
            }
            else {
                Main.writer.AddResponce("неверное кол-во аргументов");
            }
        }
    }
}
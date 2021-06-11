package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Writers.Printer;

public class Remove_greater_key  extends Command {
    @Override
    public void Execute(boolean isServerSend) {
        try {
            if (args.size() == 1) {
                for (String str : Main.tickets.getTickets().keySet()) {// получаем все ключи
                    if (args.get(0).compareTo(str) > 0) {//удаляем если ключ больше
                        Main.tickets.getTickets().remove(str);
                        if(isServerSend){
                            Printer.getInstance().WriteLine("успех");
                        }
                        else {
                            Main.writer.AddResponce("успех");
                        }
                    }
                }
            } else {
                if(isServerSend){
                    Printer.getInstance().WriteLine("неврное клд-во аргументов");
                }
                else {
                    Main.writer.AddResponce("неврное клд-во аргументов");
                }
            }
        }
        catch (Exception we){
            Main.writer.AddResponce(we.getMessage());
        }
    }
}

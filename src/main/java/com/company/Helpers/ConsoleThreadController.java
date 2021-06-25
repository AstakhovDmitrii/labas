package com.company.Helpers;

import com.company.Command;
import com.company.Main;
import com.company.Models.Writer;

import java.util.Arrays;
import java.util.Locale;

public class ConsoleThreadController implements ThreadController{
    Thread thread = new Thread(this::start);

    public ConsoleThreadController() {
        thread.start();
    }

    @Override
    public void start() {
        while (true){
            try {
                Main.Printer.WriteLine("введите команду");
                String next = Main.Printer.ReadLine().trim();
                boolean isCommand = false;
                Writer writer = new Writer();
                for (Command command : Main.controller.getCommands()) {
                    if (next.startsWith(command.getName()) || next.toLowerCase(Locale.ROOT).startsWith(command.getName().toLowerCase(Locale.ROOT))) {
                        command.args.addAll(Arrays.asList(next.split(",")));
                        command.args.remove(0);
                        String NameCommand = command.getName();
                        if(NameCommand.toLowerCase(Locale.ROOT).startsWith("insert") || NameCommand.toLowerCase(Locale.ROOT).startsWith("replace_if_greater") || NameCommand.toLowerCase(Locale.ROOT).startsWith("replace_if_lower") || NameCommand.toLowerCase(Locale.ROOT).startsWith("update")){
                            command.args.add(Main.Converter.Serialize(Create.Set_Fields()));
                        }
                        writer = command.Execute(Main.Admin);
                        isCommand = true;
                        command.args.clear();
                    }
                }
                for (String s : writer.getResponces()){
                    Main.Printer.WriteLine(s);
                }
                if (!isCommand) {
                    Main.Printer.WriteLine("нет такой команды");
                }
            }
            catch (Exception e){
                Main.Printer.WriteLine("вы каким то образом поломали программу, что ж вы за человек");
                e.printStackTrace();
            }
        }
    }
}

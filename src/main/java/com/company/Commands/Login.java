package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Writes.Printer;

public class Login extends Command {
    @Override
    public void Execute() throws Exception {
        Printer.getInstance().WriteLine("введите имя пользователя");
        Main.username = Printer.getInstance().ReadLine();

        Printer.getInstance().WriteLine("введите пароль");
        Main.password = Printer.getInstance().ReadLine();
    }
}

package com.company.Commands;

import com.company.Command;
import com.company.Main;
import com.company.Writes.Printer;

public class Login extends Command {
    @Override
    public void Execute() throws Exception {
        Main.printer.WriteLine("������� ��� ������������");
        Main.username = Main.printer.ReadLine();

        Main.printer.WriteLine("������� ������");
        Main.password = Main.printer.ReadLine();
    }
}

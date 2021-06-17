package com.company.Helpers;


import com.company.Main;
import com.company.Models.*;
import com.company.Writes.Printer;

public class Create {
    public static String getString(String name){
        String str = "";
        while (str.equals("")){
            Main.printer.WriteLine("Введите поле " + name);
            str = Main.printer.ReadLine();
        }
        return str;
    }
    public static int getInt(String name){
        int str;
        while (true){
            try {
                Main.printer.WriteLine("Введите поле " + name);
                str = Integer.parseInt(Main.printer.ReadLine());
                break;
            }
            catch (Exception ignored){

            }
        }
        return str;
    }
    public static long getLong(String name){
        long str;
        while (true){
            try {
                Main.printer.WriteLine("Введите поле " + name);
                str = Long.parseLong(Main.printer.ReadLine());
                break;
            }
            catch (Exception ignored){

            }
        }
        return str;
    }
    public static double getDouble(String name){
        double str;
        while (true){
            try {
                Main.printer.WriteLine("Введите поле " + name);
                str = Double.parseDouble(Main.printer.ReadLine());
                break;
            }
            catch (Exception ignored){

            }
        }
        return str;
    }
    public static float getFloat(String name){
        float str;
        while (true){
            try {
                Main.printer.WriteLine("Введите поле " + name);
                str = Float.parseFloat(Main.printer.ReadLine());
                break;
            }
            catch (Exception ignored){

            }
        }
        return str;
    }
    public static Ticket Set_Fields() {
        Ticket product = new Ticket();//создаем перемнные
        product.setCoordinates(new Coordinates());
        product.setPerson(new Person());
        String name = getString("name");
        while (name == null || name.equals("")) {
            Main.printer.InvalidValue();
            name = getString("name");
        }

        int price = getInt("price");
        while (price <= 0) {
            Main.printer.InvalidValue();
            price = getInt("price");
        }

        TicketType type = null;
        while (true){
            try{
                Main.printer.WriteLine("Введите TicketType");
                for (TicketType ticketType: TicketType.values()) {
                    Main.printer.Write("\t\t\t\t" + ticketType);
                }
                Main.printer.WriteLine("");
                String next = Main.printer.ReadLine();
                if(next.equals("null") || next.equals("")){
                    break;
                }
                type = TicketType.valueOf(next);
                break;
            }
            catch (Exception ignored){

            }
        }
        Main.printer.WriteLine("Вводится coordinate");

        double x = getDouble("X");
        while (x > 629) {
            Main.printer.InvalidValue();
            x = getDouble("X");
        }

        float y = getFloat("Y");
        while (y <= -825) {
            Main.printer.InvalidValue();
            y = getFloat("Y");
        }

        Main.printer.WriteLine("Вводится Person");

        long height = getLong("height");
        while (height <= 0) {
            Main.printer.InvalidValue();
            height = getLong("height");
        }

        long weight = getLong("weight");
        while (weight <= 0) {
            Main.printer.InvalidValue();
            weight = getLong("weight");
        }

        product.setName(name);
        product.setCoordinates(new Coordinates(x, y));
        product.setPrice(price);
        product.setType(type);
        product.setPerson(new Person(height,weight));

        return product;
    }
}

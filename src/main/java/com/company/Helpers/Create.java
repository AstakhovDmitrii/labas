package com.company.Helpers;


import com.company.Main;
import com.company.Models.*;

public class Create {
    public static String getString(String name){
        String str = "";
        while (str.equals("")){
            Main.Printer.WriteLine("Введите поле " + name);
            str = Main.Printer.ReadLine();
        }
        return str;
    }
    public static int getInt(String name){
        int str;
        while (true){
            try {
                Main.Printer.WriteLine("Введите поле " + name);
                str = Integer.parseInt(Main.Printer.ReadLine());
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
                Main.Printer.WriteLine("Введите поле " + name);
                str = Long.parseLong(Main.Printer.ReadLine());
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
                Main.Printer.WriteLine("Введите поле " + name);
                str = Double.parseDouble(Main.Printer.ReadLine());
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
                Main.Printer.WriteLine("Введите поле " + name);
                str = Float.parseFloat(Main.Printer.ReadLine());
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
            Main.Printer.InvalidValue();
            name = getString("name");
        }

        int price = getInt("price");
        while (price <= 0) {
            Main.Printer.InvalidValue();
            price = getInt("price");
        }

        TicketType type = null;
        while (true){
            try{
                Main.Printer.WriteLine("Введите TicketType");
                for (TicketType ticketType: TicketType.values()) {
                    Main.Printer.Write("\t\t\t\t" + ticketType);
                }
                Main.Printer.WriteLine("");
                String next = Main.Printer.ReadLine();
                if(next.equals("null") || next.equals("")){
                    break;
                }
                type = TicketType.valueOf(next);
                break;
            }
            catch (Exception ignored){

            }
        }
        Main.Printer.WriteLine("Вводится coordinate");

        double x = getDouble("X");
        while (x > 629) {
            Main.Printer.InvalidValue();
            x = getDouble("X");
        }

        float y = getFloat("Y");
        while (y <= -825) {
            Main.Printer.InvalidValue();
            y = getFloat("Y");
        }

        Main.Printer.WriteLine("Вводится Person");

        long height = getLong("height");
        while (height <= 0) {
            Main.Printer.InvalidValue();
            height = getLong("height");
        }

        long weight = getLong("weight");
        while (weight <= 0) {
            Main.Printer.InvalidValue();
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

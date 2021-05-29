package com.company.Helpers;


import com.company.Models.*;
import com.company.Writers.Printer;

public class Create {
    public static String getString(String name){
        String str = "";
        while (str.equals("")){
            Printer.getInstance().WriteLine("Введите поле " + name);
            str = Printer.getInstance().ReadLine();
        }
        return str;
    }
    public static int getInt(String name){
        int str;
        while (true){
            try {
                Printer.getInstance().WriteLine("Введите поле " + name);
                str = Integer.parseInt(Printer.getInstance().ReadLine());
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
                Printer.getInstance().WriteLine("Введите поле " + name);
                str = Long.parseLong(Printer.getInstance().ReadLine());
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
                Printer.getInstance().WriteLine("Введите поле " + name);
                str = Double.parseDouble(Printer.getInstance().ReadLine());
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
                Printer.getInstance().WriteLine("Введите поле " + name);
                str = Float.parseFloat(Printer.getInstance().ReadLine());
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
            Printer.getInstance().InvalidValue();
            name = getString("name");
        }

        int price = getInt("price");
        while (price <= 0) {
            Printer.getInstance().InvalidValue();
            price = getInt("price");
        }

        TicketType type = null;
        while (true){
            try{
                Printer.getInstance().WriteLine("Введите TicketType");
                for (TicketType ticketType: TicketType.values()) {
                    Printer.getInstance().Write("\t\t\t\t" + ticketType);
                }
                Printer.getInstance().WriteLine("");
                String next = Printer.getInstance().ReadLine();
                if(next.equals("null") || next.equals("")){
                    break;
                }
                type = TicketType.valueOf(next);
                break;
            }
            catch (Exception ignored){

            }
        }
        Printer.getInstance().WriteLine("Вводится coordinate");

        double x = getDouble("X");
        while (x > 629) {
            Printer.getInstance().InvalidValue();
            x = getDouble("X");
        }

        float y = getFloat("Y");
        while (y <= -825) {
            Printer.getInstance().InvalidValue();
            y = getFloat("Y");
        }

        Printer.getInstance().WriteLine("Вводится Person");

        long height = getLong("height");
        while (height <= 0) {
            Printer.getInstance().InvalidValue();
            height = getLong("height");
        }

        long weight = getLong("weight");
        while (weight <= 0) {
            Printer.getInstance().InvalidValue();
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

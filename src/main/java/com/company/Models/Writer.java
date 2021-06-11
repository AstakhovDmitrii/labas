package com.company.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Writer implements Serializable {

    private static final long serialVersionUID = 0x0fffa1;

    public ArrayList<String> responces;

    public ArrayList<String> getResponces() {
        return responces;
    }

    public void addResponce(String str){
        responces.add(str);
    }

    public void setResponces(ArrayList<String> responces) {
        this.responces = responces;
    }

    public Writer(){
        responces = new ArrayList<>();
    }

    public Writer(ArrayList<String> strings){
        this.responces = strings;
    }
}

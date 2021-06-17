package com.company;

import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public abstract class Command implements Serializable {// интерфей который обьединяет все команды

    private static final long serialVersionUID = 0x123;

    public abstract com.company.Models.Writer Execute() throws Exception;//метод для вызова команды

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public List<String> args = new ArrayList<>();

    public Command(){
        name = getClass().getSimpleName();
    }
}

package com.company;

import com.company.Models.Writer;
import com.company.Models.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Command implements Serializable {// интерфей который обьединяет все команды

    private static final long serialVersionUID = 0x123;

    public abstract void Execute(boolean is_thread, user user, Writer writer) throws Exception;//метод для вызова команды

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

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

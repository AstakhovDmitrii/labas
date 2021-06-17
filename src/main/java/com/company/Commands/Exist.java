package com.company.Commands;

import com.company.Command;
import com.company.Models.user;

import java.io.Serializable;

public class Exist extends Command implements Serializable {

    private static final long serialVersionUID = 0x123;
    @Override
    public com.company.Models.Writer Execute(user user) throws Exception {
        return null;
    }
}

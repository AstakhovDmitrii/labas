package com.company.Commands;

import com.company.Command;
import com.company.Models.Writer;
import com.company.Models.user;

import java.io.Serializable;

public class Exist extends Command implements Serializable {
    private static final long serialVersionUID = 0x123;
    @Override
    public void Execute(boolean is_thread, user user, Writer writer) throws Exception {

    }
}

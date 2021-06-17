package com.company.Commands;

import com.company.Command;
import com.company.Helpers.Converter;
import com.company.Helpers.Create;
import com.company.Main;

public class Replace_if_lowe extends Command {
    @Override
    public void Execute(){
        args.add(Main.converter.Serialize(Create.Set_Fields()));
    }
}

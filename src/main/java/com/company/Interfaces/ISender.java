package com.company.Interfaces;

import com.company.Command;
import com.company.Models.Writer;

public interface ISender {
    void Send(Writer writer);
    Command Recieve();
}

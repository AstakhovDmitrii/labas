package com.company.Interfaces;

import java.io.IOException;

public interface ISender {
    byte[] Recieve();

    void Send(byte[] buff) throws IOException;
}

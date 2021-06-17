package com.company.Interfaces;

import com.company.Command;
import com.company.Commands.Exist;

public interface IConverter {
    <T> String Serialize(T obj);

    <T> T Deserialize(Class<T> T, String str);

    com.company.Models.Writer DeSerializeResponce(byte[] buffer);

    byte[] SerializeResponce(Command responce);
}

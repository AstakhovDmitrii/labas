package com.company.Interfaces;

import com.company.Commands.Exist;

public interface IConverter {
    <T> String Serialize(T obj);

    <T> T Deserialize(Class<? extends T> T, String str);

    <T> void WriteToFile(T obj, String path);

    <T> T ReadFromFile(Class<? extends T> T, String path);

    Exist DeSerializeCommand(byte[] buffer);

    byte[] SerializeResponce(com.company.Models.Writer responce);
}

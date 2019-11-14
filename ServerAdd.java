package com.tavisca.polymorphicServer;

import java.io.*;
import java.net.Socket;

public class ServerAdd implements Operation, Serializable {
    @Override
    public int performOp(int a, int b) {
        return a+b;
    }

    public static void main(String[] args) throws IOException {
        ServerAdd serverAdd=new ServerAdd();
        Socket s=new Socket("localhost",2000);

        DataOutputStream dataOutputStream=new DataOutputStream(s.getOutputStream());
        DataInputStream dataInputStream=new DataInputStream(s.getInputStream());
        dataOutputStream.writeUTF("Server");
        dataOutputStream.writeUTF("add");
        ObjectOutputStream oout=new ObjectOutputStream(dataOutputStream);
        oout.writeObject(new ServerAdd());

        String response=dataInputStream.readUTF();
        System.out.println(response);
    }
}

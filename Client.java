package com.tavisca.polymorphicServer;

import com.tavisca.serialization.socket.Server;

import java.io.*;
import java.net.Socket;

public class Client {

    static Operation lookup(String name) throws IOException, ClassNotFoundException {
        Socket s = new Socket("localhost", 2000);
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
        dataOutputStream.writeUTF("Client");
        dataOutputStream.writeUTF(name);

        ObjectInputStream oin = new ObjectInputStream(dataInputStream);
        Operation op = (Operation) oin.readObject();
        return op;

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Operation operation = lookup("add");
        System.out.println(operation.performOp(2, 3));
    }
}

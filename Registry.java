package com.tavisca.polymorphicServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Registry {
    HashMap<String, Object> register;
    ServerSocket ss;

    public Registry() throws IOException {
        register = new HashMap<>();
        ss = new ServerSocket(2000);
    }

    public static void main(String[] args) throws IOException {
        Registry r = new Registry();
        try {
            while (true) {
                Socket socket = r.ss.accept();
                DataInputStream registryInput = new DataInputStream(socket.getInputStream());
                DataOutputStream registryOutput = new DataOutputStream(socket.getOutputStream());
                String hostType = registryInput.readUTF();

                if (hostType.equals("Client")) {
                    String type=registryInput.readUTF();
                    ObjectOutputStream oout=new ObjectOutputStream(registryOutput);
                    oout.writeObject(r.register.get(type));

                } else if (hostType.equals("Server")) {
                    String serverName = registryInput.readUTF();
                    ObjectInputStream oin = new ObjectInputStream(registryInput);
                    try {
                        Object o = oin.readObject();
                        r.register.put(serverName,o);
                        registryOutput.writeUTF("registered");

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

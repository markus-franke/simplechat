package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public Client(String ip, int port) {
        System.out.println("Running in client mode...");
        System.out.printf("Connecting to %s:%d...\n", ip, port);
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Connected");
            OutputStream outputStream = socket.getOutputStream();
            String line;
            do {
                System.out.print("> ");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                line = bufferedReader.readLine();
                if (!line.isEmpty()) {
                    outputStream.write(line.getBytes());
                }
            } while (!line.isEmpty());
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

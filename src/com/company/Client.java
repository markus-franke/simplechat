package com.company;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final String ip;
    private final int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        System.out.println("Running in client mode...");
    }

    public void run() throws IOException {
        System.out.printf("Connecting to %s:%d...\n", ip, port);
        Socket socket = new Socket(ip, port);
        System.out.println("Connected");
        CommunicationMgr.run(socket);
    }
}

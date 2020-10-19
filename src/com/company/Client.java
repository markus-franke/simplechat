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

    public void run() {
        System.out.printf("Connecting to %s:%d...\n", ip, port);
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Connected");

            MsgSender msgSender = new MsgSender(socket);
            Thread senderThread = new Thread(msgSender);
            senderThread.start();

            MsgReceiver msgReceiver = new MsgReceiver(socket);
            Thread receiverThread = new Thread(msgReceiver);
            receiverThread.start();

            receiverThread.join();
            senderThread.interrupt();
            senderThread.join();

            System.out.println("Disconnecting...");

            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

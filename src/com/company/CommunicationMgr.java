package com.company;

import java.io.IOException;
import java.net.Socket;

public class CommunicationMgr {
    public static void run(Socket socket) {
        MsgReceiver msgReceiver = new MsgReceiver(socket);
        Thread receiverThread = new Thread(msgReceiver);
        receiverThread.start();

        MsgSender msgSender = new MsgSender(socket);
        Thread senderThread = new Thread(msgSender);
        senderThread.start();

        try {
            receiverThread.join();
            senderThread.interrupt();
            senderThread.join();

            System.out.println("Disconnecting...");
            socket.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

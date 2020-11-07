package com.company;

import java.io.IOException;
import java.net.Socket;

public class CommunicationMgr {
    public static void run(Socket socket) throws InterruptedException, IOException {
        UnhandledExceptionHandler unhandledExceptionHandler = new UnhandledExceptionHandler();

        MsgReceiver msgReceiver = new MsgReceiver(socket);
        Thread receiverThread = new Thread(msgReceiver);
        receiverThread.setUncaughtExceptionHandler(unhandledExceptionHandler);
        receiverThread.start();

        MsgSender msgSender = new MsgSender(socket);
        Thread senderThread = new Thread(msgSender);
        senderThread.setUncaughtExceptionHandler(unhandledExceptionHandler);
        senderThread.start();

        receiverThread.join();
        senderThread.interrupt();
        senderThread.join();

        // process any unhandled exceptions from other threads
        unhandledExceptionHandler.process();

        System.out.println("Disconnecting...");
    }
}

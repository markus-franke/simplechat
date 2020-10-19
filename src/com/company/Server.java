package com.company;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    private static final int DEFAULT_PORT = 12345;

    private final int port = DEFAULT_PORT;
    private InetAddress inetAddress;

    public Server() {
        System.out.println("Running in server mode...");

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assert inetAddress != null;
    }

    public void run() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(port, 0, inetAddress);
        String ip = serverSocket.getInetAddress().getHostAddress();
        int port = serverSocket.getLocalPort();

        while (true) {
            System.out.printf("Listening on %s:%d...\n", ip, port);
            System.out.printf("Run another instance of this application with command line option '%s %s:%d'\n", Constants.INSTANCE_PARAM, ip, port);

            Socket clientSocket = serverSocket.accept();
            System.out.printf("Connected to %s:%d\n", clientSocket.getLocalAddress().getHostAddress(), clientSocket.getLocalPort());

            MsgReceiver msgReceiver = new MsgReceiver(clientSocket);
            Thread receiverThread = new Thread(msgReceiver);
            receiverThread.start();

            MsgSender msgSender = new MsgSender(clientSocket);
            Thread senderThread = new Thread(msgSender);
            senderThread.start();

            receiverThread.join();
            senderThread.interrupt();
            senderThread.join();

            System.out.println("Disconnecting...");
            clientSocket.close();
        }
    }
}

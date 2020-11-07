package com.company;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int DEFAULT_PORT = 12345;

    private final int port = DEFAULT_PORT;

    public Server() {
        System.out.println("Running in server mode...");
    }

    public void run() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getLocalHost());
        String ip = serverSocket.getInetAddress().getHostAddress();
        int port = serverSocket.getLocalPort();

        while (true) {
            System.out.printf("Listening on %s:%d...\n", ip, port);
            System.out.printf("Run another instance of this application with command line option '%s %s:%d'\n", Constants.INSTANCE_PARAM, ip, port);

            Socket clientSocket = serverSocket.accept();
            System.out.printf("Connected to %s:%d\n", clientSocket.getLocalAddress().getHostAddress(), clientSocket.getLocalPort());

            try (clientSocket) {
                CommunicationMgr.run(clientSocket);
            }
        }
    }
}

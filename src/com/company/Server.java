package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    private static final int DEFAULT_PORT = 12345;

    private int port = -1;
    private InetAddress inetAddress = null;

    public Server() {
        System.out.println("Running in server mode...");

        port = DEFAULT_PORT;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assert inetAddress != null;
    }

    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port, 0, inetAddress);
        String ip = serverSocket.getInetAddress().getHostAddress();
        int port = serverSocket.getLocalPort();
        System.out.printf("Listening on %s:%d...\n", ip, port);
        System.out.printf("Run another instance of this application with command line option '--other-instance %s:%d'\n", ip, port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Connected to %s:%d\n", clientSocket.getLocalAddress().getHostAddress(), clientSocket.getLocalPort());
            InputStream inputStream = clientSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            do {
                bytesRead = inputStream.read(buffer);
                if (bytesRead != -1) {
                    String incomingMessage = new String(buffer, 0, bytesRead);
                    System.out.printf("Peer< %s\n", incomingMessage);
                }
            } while (bytesRead != -1);

            System.out.println("Disconnecting...");
            clientSocket.close();
        }
    }
}

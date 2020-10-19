package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MsgReceiver implements Runnable {
    private final Socket socket;

    public MsgReceiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            do {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead != -1) {
                    String incomingMessage = new String(buffer, 0, bytesRead);

                    if (incomingMessage.equals(Constants.QUIT_COMMAND)) {
                        break;
                    } else {
                        System.out.printf("< %s\n", incomingMessage);
                    }
                } else {
                    break;
                }
            } while (true);
            inputStream.close();
        } catch (IOException e) {
            if (!socket.isClosed()) {
                e.printStackTrace();
            }
        }

        System.out.println("MsgReceiver: Disconnected.");
    }
}

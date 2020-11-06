package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MsgSender implements Runnable {

    private final Socket socket;

    public MsgSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            String outgoingMessage = "";
            do {
                System.out.print("> ");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                boolean doCancel = false;
                while (!bufferedReader.ready()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        doCancel = true;
                        break;
                    }
                }
                if (doCancel) break;

                outgoingMessage = new String(bufferedReader.readLine().getBytes(), Constants.DEFAULT_CHARSET);
                if (!outgoingMessage.isEmpty()) {
                    outputStream.write(outgoingMessage.getBytes());
                }
            } while (!outgoingMessage.isEmpty());
            outputStream.close();
        } catch (IOException e) {
            if (!socket.isClosed()) {
                e.printStackTrace();
            }
        }

        System.out.println("MsgSender: Disconnected.");
    }
}

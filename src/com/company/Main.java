package com.company;

import java.io.IOException;

public class Main {

    private static final String INSTANCE_PARAM = "--other-instance";

    private static void usage() {
        System.out.printf("Usage: SimpleChat [%s <IP>:<Port>]\n", INSTANCE_PARAM);
    }

    public static void main(String[] args) {
        switch (args.length) {
            case 0 -> {
                Server server = new Server();
                try {
                    server.run();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Unable to run server mode.");
                }
            }
            case 2 -> {
                if (args[0].equals(INSTANCE_PARAM)) {
                    String[] endpoint = args[1].split(":");
                    if (endpoint.length == 2) {
                        try {
                            int port = Integer.parseInt(endpoint[1]);
                            Client client = new Client(endpoint[0], port);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Got invalid port number " + endpoint[1]);
                        }
                    }
                }
                usage();
            }
            default -> usage();
        }
    }
}
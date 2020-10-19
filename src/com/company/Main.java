package com.company;

public class Main {

    private static void usage() {
        System.out.printf("Usage: SimpleChat [%s <IP>:<Port>]\n", Constants.INSTANCE_PARAM);
    }

    public static void main(String[] args) {
        // NOTE: Very basic cmdline parsing.
        // In a more complex scenario one should probably use something like
        // http://commons.apache.org/cli/ or
        // http://argparse4j.github.io/
        switch (args.length) {
            case 0 -> {
                Server server = new Server();
                try {
                    server.run();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Unable to run server mode.");
                }
            }
            case 2 -> {
                if (args[0].equals(Constants.INSTANCE_PARAM)) {
                    String[] endpoint = args[1].split(":");
                    if (endpoint.length == 2) {
                        try {
                            int port = Integer.parseInt(endpoint[1]);
                            Client client = new Client(endpoint[0], port);
                            client.run();
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
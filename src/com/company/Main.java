package com.company;

public class Main {

    private static void handleMisusage() {
        System.out.printf("Usage: SimpleChat [%s <IP>:<Port>]\n", Constants.INSTANCE_PARAM);
        exitCode = -1;
    }

    private static void handleFatalException(Exception e, String message) {
        System.out.println(message);
        e.printStackTrace();
        exitCode = -1;
    }

    private static int exitCode = 0;

    public static void main(String[] args) {
        // NOTE: Very basic cmdline parsing.
        // In a more complex scenario one should probably use something like
        // http://commons.apache.org/cli/ or
        // http://argparse4j.github.io/
        switch (args.length) {
            case 0:
                Server server = new Server();
                try {
                    server.run();
                } catch (Exception e) {
                    handleFatalException(e, "Unable to run server mode.");
                }
                break;
            case 2:
                if (args[0].equals(Constants.INSTANCE_PARAM)) {
                    String[] endpoint = args[1].split(":");
                    if (endpoint.length == 2) {
                        try {
                            int port = Integer.parseInt(endpoint[1]);
                            Client client = new Client(endpoint[0], port);
                            try {
                                client.run();
                            } catch (Exception e) {
                                handleFatalException(e, "Error while communicating to server.");
                            }
                        } catch (NumberFormatException e) {
                            handleFatalException(e, "Got invalid port number " + endpoint[1]);
                        }
                    }
                } else {
                    handleMisusage();
                }
                break;
            default:
            	handleMisusage();
            	break;
        }

        System.exit(exitCode);
    }
}
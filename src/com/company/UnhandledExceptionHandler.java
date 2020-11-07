package com.company;

import java.io.IOException;

public class UnhandledExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Throwable unhandledThrowable = null;

    public void process() throws IOException {
        if (unhandledThrowable != null) {
            if (unhandledThrowable instanceof IOException) {
                throw new IOException(unhandledThrowable);
            } else {
                throw new RuntimeException(unhandledThrowable);
            }
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // NOTE: This implementation only keeps track of the last uncaught exception
        // ignoring anything that has happened before.
        this.unhandledThrowable = e;
    }
}

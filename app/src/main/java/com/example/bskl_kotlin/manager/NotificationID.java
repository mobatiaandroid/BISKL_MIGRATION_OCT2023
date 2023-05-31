package com.example.bskl_kotlin.manager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by krishnaraj on 17/08/18.
 */

public class NotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}

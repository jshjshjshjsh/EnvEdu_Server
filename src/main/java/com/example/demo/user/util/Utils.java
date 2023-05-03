package com.example.demo.user.util;

import java.util.UUID;

public class Utils {
    public static String generateAuthNum() {
        return UUID.randomUUID().toString().substring(0, 4);
    }
}

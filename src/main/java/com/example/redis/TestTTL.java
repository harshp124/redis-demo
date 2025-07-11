package com.example.redis;

import redis.clients.jedis.Jedis;
import java.security.SecureRandom;

public class TestTTL {
    public static void main(String[] args) {
        String redisHost = "<Redis DB connection string>";
        int redisPort = 14742;
        // String username = "your-username"; // Uncomment if using ACLs
        String password = "Test@123"; // Uncomment if using authentication

        // Generate a large random string (~100 KB)
        int stringSize = 100 * 1024; // 100 KB
        String largeRandomString = generateRandomString(stringSize);
        System.out.println("Sample string size: " + largeRandomString.length()/1024 + " KB");

        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            jedis.auth(password); // Uncomment if needed

            for (int i = 0; i < 100; i++) {
                long counter = jedis.incr("key:counter");
                String key = "key:" + counter;
                jedis.setex(key, (i+1), largeRandomString);
            }
            System.out.println("Done inserting random string entries with Redis counter keys.");
        }
    }

    // Helper method to generate a large random alphanumeric string
    private static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}

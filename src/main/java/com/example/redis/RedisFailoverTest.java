package com.example.redis;

import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisFailoverTest {
    public static void main(String[] args) {
        // Use Redis Enterprise FQDN with URL-encoded password
        String redisUri = "<Redis DB URL>";

        String key = "myKey";
        String value = "myValue";

        int maxAttempts = 5;
        // long baseDelay = 100; // milliseconds

        try (JedisPooled jedis = new JedisPooled(redisUri)) {
            jedis.set(key, value);
            System.out.println("Key inserted: " + key);

            for (int i = 0; i < 3000; i++) {
                int attempt = 0;
                while (attempt < maxAttempts) {
                    try {
                        String val = jedis.get(key);
                        System.out.println("Attempt " + (i + 1) + ": Value = " + val);
                        break; // Success
                    } catch (JedisConnectionException e) {
                        attempt++;
                        System.out.println("Connection error. Retrying... Attempt " + attempt);
                        if (attempt == maxAttempts) {
                            System.out.println("Max retry attempts reached.");
                            e.printStackTrace();
                            break;
                        }
                        try {
                            // long delay = baseDelay * (1L << (attempt - 1)); // exponential backoff
                            long delay = attempt * 1000; // delay in milliseconds: 1000ms, 2000ms, 3000ms...
                            Thread.sleep(delay);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Unexpected error:");
            e.printStackTrace();
        }
    }
}

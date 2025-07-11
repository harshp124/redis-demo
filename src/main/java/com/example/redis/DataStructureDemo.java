package com.example.redis;

import redis.clients.jedis.Jedis;
import java.util.*;

public class DataStructureDemo {
    public static void main(String[] args) {
        // Connect to Redis (adjust host, port, and authentication as needed)
        try (Jedis jedis = new Jedis("<Redis DB connection string>", 14742)) {
            // If authentication is required:
            jedis.auth("Test@123");

            // 1. String
            jedis.set("stringKey", "Hello Redis!");
            String stringValue = jedis.get("stringKey");
            System.out.println("String: " + stringValue);

            // 2. Hash
            jedis.hset("user:1000", "name", "Alice");
            jedis.hset("user:1000", "email", "alice@example.com");
            Map<String, String> userHash = jedis.hgetAll("user:1000");
            System.out.println("Hash: " + userHash);
            
            // Get a specific field (e.g., name)
            String name = jedis.hget("user:1000", "name");
            System.out.println("Specific field (name): " + name);

            // 3. List
            // delete the List key first
            jedis.del("fruits");
            jedis.lpush("fruits", "apple", "banana", "orange");
            List<String> fruits = jedis.lrange("fruits", 0, -1);
            System.out.println("List: " + fruits);

            // 4. Set
            jedis.sadd("tags", "java", "redis", "database");
            Set<String> tags = jedis.smembers("tags");
            System.out.println("Set: " + tags);

            // 5. Sorted Set
            Map<String, Double> scores = new HashMap<>();
            scores.put("Alice", 100.0);
            scores.put("Bob", 80.5);
            scores.put("Charlie", 92.2);
            jedis.zadd("ranking", scores);
            List<String> ranking = jedis.zrevrange("ranking", 0, -1);
            System.out.println("Sorted Set: " + ranking);
        }
    }
}

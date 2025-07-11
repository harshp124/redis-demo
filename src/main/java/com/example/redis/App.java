package com.example.redis;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try (Jedis jedis = new Jedis("<Redis DB connection string>", 14742)) {
            jedis.auth("Test@123"); // if authentication is enabled
            jedis.set("foo", "bar");
            String value = jedis.get("foo");
            System.out.println("Value for 'foo': " + value);
        }
    }
}

package netflix;

import util.TestUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TimedCache {

    /***
     * Implement a cache where each entry has a TTL (time-to-live). Entries are automatically considered expired after their TTL elapses.
     */

    private static class Entry {
        String value;
        long expiryTimeMs;

        Entry(String value, long expiryTimeMs){
            this.value = value;
            this.expiryTimeMs = expiryTimeMs;
        }
    }

    private final Map<String, Entry> map;

    private int defaultTtlMs = 10 * 1000;
    private Integer capacity;

    TimedCache(int ttlSeconds){
        this.defaultTtlMs = ttlSeconds * 1000;
        map = new HashMap<>();
    }

    TimedCache(int ttl, int capacity){
        this.defaultTtlMs = ttl;
        this.capacity = capacity;
        map = new LinkedHashMap<>(16, 0.75f, true);
    }

    public String get(String key){
        Entry entry = map.get(key);

        if(entry != null){
            if(entry.expiryTimeMs > System.currentTimeMillis()){
                return entry.value;
            } else {
                map.remove(key);
                return null;
            }
        } else {
            return null;
        }
    }

    public String put(String key, String value){
        return put(key,value, defaultTtlMs);
    }

    public String put(String key, String value, int ttl){
        long expiryTimeMs = System.currentTimeMillis() + ttl;

        if(capacity == null){
            map.put(key, new Entry(value, expiryTimeMs));
        } else {
            map.remove(key);
            if(map.size() == capacity){
                String leastRecentlyUsedKey = map.entrySet().iterator().next().getKey();
                map.remove(leastRecentlyUsedKey);
            }
            map.put(key, new Entry(value, expiryTimeMs));
        }
        return value;
    }


    static void main() throws InterruptedException {
        TimedCache cache = new TimedCache(2);
        cache.put("a", "1");
        TestUtil.check(cache.get("a").equals("1"));

        Thread.sleep(3000);
        TestUtil.check(cache.get("a") == null); // expired

        cache.put("b", "2");
        cache.put("b", "3"); // update same key
        TestUtil.check(cache.get("b").equals("3"));

// Capacity test
        TimedCache cache2 = new TimedCache(60, 2);
        cache2.put("x", "1");
        cache2.put("y", "2");
        cache2.put("z", "3"); // evicts "x" (oldest)
        TestUtil.check(cache2.get("x") == null);
        TestUtil.check(cache2.get("y").equals("2"));


        // LRU test
        TimedCache cache3 = new TimedCache(60, 2);
        cache3.put("x", "1");
        cache3.put("y", "2");
        cache3.get("x"); // y is now the least recently used
        cache3.put("z", "3"); // evicts "y" (oldest)
        TestUtil.check(cache3.get("x") != null);
        TestUtil.check(cache3.get("y") == null);
    }
}

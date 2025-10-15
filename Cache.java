import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A generic, fixed-capacity Least Recently Used (LRU) cache.
 * <p>
 * Backed by a LinkedHashMap configured with access order, so any successful
 * get() or put() marks the entry as most recently used. When size exceeds
 * capacity, the least recently used entry is evicted automatically.
 * <p>
 * Thread-safety: All operations are guarded by a single ReentrantLock,
 * providing mutual exclusion and linearizable semantics. Because LRU recency
 * updates happen on reads, a ReadWriteLock would not improve concurrency.
 * <p>
 * Metrics: Tracks hit/miss counts for get() calls.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class Cache<K, V> {
    private final int capacity;
    private final LinkedHashMap<K, V> map;
    private final ReentrantLock lock = new ReentrantLock();
    private long hits = 0;
    private long misses = 0;

    /**
     * Creates a cache with the specified positive capacity.
     *
     * @param capacity maximum number of entries to retain (> 0)
     * @throws IllegalArgumentException if capacity <= 0
     */
    public Cache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.capacity = capacity;
        this.map = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > Cache.this.capacity;
            }
        };
    }

    /**
     * Returns the value associated with the given key and updates its recency
     * if present (LRU access). Increments hit/miss metrics accordingly.
     *
     * Thread-safe: acquires the internal lock.
     *
     * @param key the key to look up
     * @return the cached value, or null if absent
     */
    public V get(K key) {
        lock.lock();
        try {
            V value = map.get(key);
            if (value == null) {
                misses++;
            } else {
                hits++;
            }
            return value;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Inserts or updates the value for the given key and marks it as most
     * recently used. May evict the least recently used entry if capacity
     * would be exceeded.
     *
     * Thread-safe: acquires the internal lock.
     *
     * @param key key to insert/update
     * @param value value to cache (may be null)
     */
    public void put(K key, V value) {
        lock.lock();
        try {
            map.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes and returns the value associated with the key, if present.
     *
     * Thread-safe: acquires the internal lock.
     *
     * @param key key to remove
     * @return the previous value, or null if none
     */
    public V remove(K key) {
        lock.lock();
        try {
            return map.remove(key);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the current number of entries in the cache.
     *
     * Thread-safe: acquires the internal lock.
     *
     * @return current size
     */
    public int size() {
        lock.lock();
        try {
            return map.size();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the number of successful get() lookups since the last clear
     * (or since construction).
     *
     * Thread-safe: acquires the internal lock.
     *
     * @return hit count
     */
    public long getHitCount() {
        lock.lock();
        try {
            return hits;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the number of unsuccessful get() lookups since the last clear
     * (or since construction).
     *
     * Thread-safe: acquires the internal lock.
     *
     * @return miss count
     */
    public long getMissCount() {
        lock.lock();
        try {
            return misses;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Clears all entries and resets the metrics.
     *
     * Thread-safe: acquires the internal lock.
     */
    public void clear() {
        lock.lock();
        try {
            map.clear();
            hits = 0;
            misses = 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a snapshot copy of the cache contents in LRU order
     * (from least to most recently used) at the moment of copying.
     * The returned map is independent of internal state.
     *
     * Thread-safe: acquires the internal lock.
     *
     * @return a shallow copy of the current contents
     */
    public Map<K, V> snapshot() {
        lock.lock();
        try {
            return new LinkedHashMap<>(map);
        } finally {
            lock.unlock();
        }
    }
}

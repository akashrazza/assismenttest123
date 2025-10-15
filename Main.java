import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        System.out.println("Challange 1 : ");
        // Demo for Challenge 1: Custom Immutable Class
        List<String> hobbies = Arrays.asList("reading", "hiking", "coding");
        Person person = Person.create("John Doe", 30, hobbies);
        System.out.println(person.getName());
        System.out.println(person.getHobbies());


        System.out.println("Challange 2 : ");
        // Demo for Challenge 2: Generic thread-safe LRU Cache
        Cache<String, String> cache = new Cache<>(3);
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        System.out.println(cache.get("key1")); // value1 (hit)
        cache.put("key4", "value4"); // Evicts least recently used (key2)
        System.out.println(cache.get("key2")); // null (miss)

        System.out.println("Hits=" + cache.getHitCount() + ", Misses=" + cache.getMissCount());
    }
}
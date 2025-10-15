public final class Person {
    private final String name;
    private final int age;
    private final java.util.List<String> hobbies;

    private Person(String name, int age, java.util.List<String> hobbies) {
        this.name = name;
        this.age = age;
        // Defensive copy and make unmodifiable internally
        java.util.List<String> safe = (hobbies == null)
                ? java.util.Collections.emptyList()
                : new java.util.ArrayList<>(hobbies);
        this.hobbies = java.util.Collections.unmodifiableList(safe);
    }

    public static Person create(String name, int age, java.util.List<String> hobbies) {
        return new Person(name, age, hobbies);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Defensive copy on getter
    public java.util.List<String> getHobbies() {
        return new java.util.ArrayList<>(hobbies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age &&
                java.util.Objects.equals(name, person.name) &&
                java.util.Objects.equals(hobbies, person.hobbies);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, age, hobbies);
    }
}

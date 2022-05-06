package P3;

import java.util.HashSet;
import java.util.Set;

public class Person {
    private String thisName; //The name of the person
    private Set<Person> friends; //Friends of the person

    public Person(String name) {
        // construction method of Person
            this.thisName = name;
            this.friends = new HashSet<>();
    }

    public String getThisName() {
        return this.thisName;
    }
    public void addFriend(Person newFriend) {
        this.friends.add(newFriend);
    }

    public Set<Person> getFriends() {
        return this.friends;
    }
}


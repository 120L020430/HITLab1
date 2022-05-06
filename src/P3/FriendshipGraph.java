package P3;

import java.util.*;

public class FriendshipGraph {
    public Set<Person> allPersons= new HashSet<>();
    //The set of all persons
    public Set<String> allPersonNames = new HashSet<>();
    //The set of all persons' name

    /**
     * Add a person to the social net. if the person's name is not a new one, the program will print hint.
     * @param freshMan
     *              an instance of the Person
     */
    public void addVertex(Person freshMan) {
        if(allPersonNames.contains(freshMan.getThisName())) { //Duplicate name
            System.out.println(freshMan.getThisName() + " is not a new one.");
            System.exit(-1);
        }
        allPersonNames.add(freshMan.getThisName());
        allPersons.add(freshMan);
    }

    /**
     * Add edges to the graph, namely build the friendship in the social net. If the two persons are the same one, or
     * one name isn't added before, hint will be printed.
     * @param per1
     *          an instance of Person
     * @param per2
     *          an instance of Person
     */
    public void addEdge(Person per1, Person per2) {
        if (per1.getThisName().equals(per2.getThisName())) { //the same names
            System.out.println("The two persons names are the same");
        }
        else if (!allPersonNames.contains(per1.getThisName()) | !allPersonNames.contains(per2.getThisName())) {
            //The name is invalid
            System.out.println("One person's name isn't added before.");
            System.exit(-1);
        }
        else {
            per1.addFriend(per2);
        }
    }

    /**Use breadth-first search to calculate the distance from per1 to per2
     *
     * @param per1
     *          an instance of Person
     * @param per2
     *          an instance of Person
     * @return the distance of per1 and per2
     */
    public int getDistance(Person per1, Person per2) {
        if (per1.getThisName().equals(per2.getThisName())) {
            return 0;
        }
        else {
            Queue<Person> personQueue = new LinkedList<>();
            Map<Person, Integer> personIntegerMap = new HashMap<>(); //Save distance from everyone
            personQueue.offer(per1);
            personIntegerMap.put(per1, 0);
            int distance = 0;
            while (!personQueue.isEmpty()) { //Breadth-first search
                Person first = personQueue.poll();
                Set<Person> firstFriends = first.getFriends();
                for (Person i:firstFriends) {
                    if (!personIntegerMap.containsKey(i)) {
                        personQueue.offer(i);
                        personIntegerMap.put(i, distance + 1);
                        if (i.getThisName().equals(per2.getThisName())) {
                            return distance + 1;
                        }
                    }
                }
                distance ++;
            }
            return -1;
        }
    }
    public static void main(String[] args) {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));
        //should print 1
        System.out.println(graph.getDistance(rachel, ben));
        //should print 2
        System.out.println(graph.getDistance(rachel, rachel));
        //should print 0
        System.out.println(graph.getDistance(rachel, kramer));
        //should print ©\1
    }
}


package P3;

import org.junit.Test;

import static org.junit.Assert.*;

public class FriendshipGraphTest {
    /**
     * construct one graph and four persons to test the methods in Friend
     */
    FriendshipGraph graph = new FriendshipGraph();
    Person tom = new Person("Tom");
    Person jerry = new Person("Jerry");
    Person zhang = new Person("Zhang");
    Person li = new Person("Li");

    @Test
    public void addVertexTest() {
        //Test that it can be added normally
        graph.addVertex(tom);
        graph.addVertex(jerry);
        assertTrue(graph.allPersonNames.contains(tom.getThisName()));
        assertTrue(graph.allPersons.contains(tom));
        graph.addVertex(zhang);
        graph.addVertex(li);
        //Adding nodes will not cause the original nodes to disappear
        assertTrue(graph.allPersonNames.contains(tom.getThisName()));
        assertTrue(graph.allPersons.contains(tom));
        assertTrue(graph.allPersonNames.contains(li.getThisName()));
        assertTrue(graph.allPersons.contains(li));
    }

    @Test
    public void addEdgeTest() {
        graph.addVertex(tom);
        graph.addVertex(jerry);
        graph.addVertex(zhang);
        graph.addVertex(li);
        graph.addEdge(tom, jerry);
        graph.addEdge(zhang, tom);
        graph.addEdge(tom, zhang);
        //Test that the edges can be added normally, and that
        //adding one-way edges does not result in the inverse edges being added
        assertTrue(tom.getFriends().contains(jerry));
        assertFalse(jerry.getFriends().contains(tom));
        assertTrue(zhang.getFriends().contains(tom));
        //Test edges with the same node are not added
        graph.addEdge(li, li);
        assertFalse(li.getFriends().contains(li));
    }

    @Test
    public void getDistanceTest() {
        Person jack = new Person("Jack");
        graph.addVertex(jack);
        graph.addVertex(tom);
        graph.addVertex(jerry);
        graph.addVertex(zhang);
        graph.addVertex(li);
        graph.addEdge(tom, jerry);
        graph.addEdge(zhang, tom);
        graph.addEdge(tom, zhang);
        graph.addEdge(jack, zhang);
        graph.addEdge(jerry, jack);
        assertEquals(0, graph.getDistance(tom, tom));
        assertEquals(1, graph.getDistance(tom, jerry));
        assertEquals(2, graph.getDistance(tom, jack));
        assertEquals(1, graph.getDistance(tom, zhang));
        assertEquals(3, graph.getDistance(zhang, jack));
        assertEquals(-1, graph.getDistance(tom, li));
        assertEquals(3, graph.getDistance(jack, jerry));
        assertEquals(2, graph.getDistance(jack, tom));
    }
}
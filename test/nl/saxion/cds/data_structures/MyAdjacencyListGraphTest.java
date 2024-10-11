package nl.saxion.cds.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyAdjacencyListGraphTest {
    private MyAdjacencyListGraph<String> graph;

    @Test
    public void GivenEmptyGraph_WhenAddingEdges_ConfirmCorrectSizeAndWeight(){
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "C", 3);

        assertEquals(3, graph.size());
        assertEquals(6, graph.getTotalWeight());
    }

    @Test
    public void GivenEmptyGraph_WhenAddingEdges_ConfirmCorrectEdgesSize(){
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "C", 3);

        assertEquals(2, graph.getEdges("A").size());
        assertEquals(1, graph.getEdges("B").size());
    }

    @Test
    public void GivenEmptyGraph_WhenAddingEdges_ConfirmCorrectEdgesValues(){
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "C", 3);

        // assert correct edges for A
        assertEquals("B", graph.getEdges("A").get(0).to());
        assertEquals("C", graph.getEdges("A").get(1).to());

        // assert correct edges for B
        assertEquals("C", graph.getEdges("B").get(0).to());
    }

    @Test
    public void GivenEmptyGraph_WhenAddingBidirectionalEdges_ConfirmCorrectSizeAndWeight(){
        graph.addEdgeBidirectional("A", "B", 1);
        graph.addEdgeBidirectional("A", "C", 2);
        graph.addEdgeBidirectional("B", "C", 3);

        assertEquals(3, graph.size());
        assertEquals(12, graph.getTotalWeight());
    }

    @Test
    public void GivenEmptyGraph_WhenAddingBidirectionalEdges_ConfirmCorrectEdgesSize(){
        graph.addEdgeBidirectional("A", "B", 1);
        graph.addEdgeBidirectional("A", "C", 2);
        graph.addEdgeBidirectional("B", "C", 3);

        assertEquals(2, graph.getEdges("A").size());
        assertEquals(2, graph.getEdges("B").size());
        assertEquals(2, graph.getEdges("C").size());
    }

    @Test
    public void GivenEmptyGraph_WhenAddingBidirectionalEdges_ConfirmCorrectEdgesValues(){
        graph.addEdgeBidirectional("A", "B", 1);
        graph.addEdgeBidirectional("A", "C", 2);
        graph.addEdgeBidirectional("B", "C", 3);

        // assert correct edges for A
        assertEquals("B", graph.getEdges("A").get(0).to());
        assertEquals("C", graph.getEdges("A").get(1).to());

        // assert correct edges for B
        assertEquals("A", graph.getEdges("B").get(0).to());
        assertEquals("C", graph.getEdges("B").get(1).to());

        // assert correct edges for C
        assertEquals("A", graph.getEdges("C").get(0).to());
        assertEquals("B", graph.getEdges("C").get(1).to());
    }

    @Test
    public void GivenEmptyGraph_ConfirmIsEmpty(){
        assertTrue(graph.isEmpty());
    }

    @Test
    public void GivenGraphWithEdges_ConfirmIsNotEmpty(){
        graph.addEdge("A", "B", 1);
        assertFalse(graph.isEmpty());
    }

    @Test
    public void GivenGraphWithElements_WhenIterating_ConfirmCorrectOrder(){
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("A", "E", 3);
        graph.addEdge("B", "D", 4);
        graph.addEdge("B", "F", 4);
        graph.addEdge("C", "G", 4);
        graph.addEdge("E", "F", 4);

        ArrayList<String> list = new ArrayList<>();
        for (String vertex : graph) {
            list.add(vertex);
        }

        List<String> possibleListB = List.of("A", "B", "C", "E", "D", "F", "G");

        assertEquals(7, graph.size());
        assertEquals(list, possibleListB);
    }

    @Test
    public void test(){
        MyAdjacencyListGraph<Integer> intGraph = new MyAdjacencyListGraph<>();

        intGraph.addEdge(2, 0, 1);
        intGraph.addEdge(2, 3, 1);
        intGraph.addEdge(0, 1, 1);
        intGraph.addEdge(3, 1, 1);
        intGraph.addEdge(3, 4, 1);
        intGraph.addEdge(4, 5, 1);

        List<Integer> intList = new ArrayList<>();
        for (int i : intGraph){
            intList.add(i);
        }
        List<Integer> possibleList = List.of(2, 0, 3, 1, 4, 5);
        assertEquals(intList, possibleList);
    }

    @BeforeEach
    public void setUp() {
        graph = new MyAdjacencyListGraph<>();
    }
}

package nl.saxion.cds.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    public void setUp() {
        graph = new MyAdjacencyListGraph<>();
    }
}

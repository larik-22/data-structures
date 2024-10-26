package nl.saxion.cds.solution.data_structures;

import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.solution.data_models.Coordinate;
import nl.saxion.cds.utils.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyAdjacencyListGraphTest {
    private MyAdjacencyListGraph<String> graph;

    @Test
    public void GivenEmptyGraph_WhenAddingEdges_ConfirmCorrectSizeAndWeight() {
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "C", 3);

        assertEquals(3, graph.size());
        assertEquals(6, graph.getTotalWeight());
    }

    @Test
    public void GivenEmptyGraph_WhenAddingEdges_ConfirmCorrectEdgesSize() {
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "C", 3);

        assertEquals(2, graph.getEdges("A").size());
        assertEquals(1, graph.getEdges("B").size());
    }

    @Test
    public void GivenEmptyGraph_WhenAddingEdges_ConfirmCorrectEdgesValues() {
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
    public void GivenEmptyGraph_WhenAddingBidirectionalEdges_ConfirmCorrectSizeAndWeight() {
        graph.addEdgeBidirectional("A", "B", 1);
        graph.addEdgeBidirectional("A", "C", 2);
        graph.addEdgeBidirectional("B", "C", 3);

        assertEquals(3, graph.size());
        assertEquals(12, graph.getTotalWeight());
    }

    @Test
    public void GivenEmptyGraph_WhenAddingBidirectionalEdges_ConfirmCorrectEdgesSize() {
        graph.addEdgeBidirectional("A", "B", 1);
        graph.addEdgeBidirectional("A", "C", 2);
        graph.addEdgeBidirectional("B", "C", 3);

        assertEquals(2, graph.getEdges("A").size());
        assertEquals(2, graph.getEdges("B").size());
        assertEquals(2, graph.getEdges("C").size());
    }

    @Test
    public void GivenEmptyGraph_WhenAddingBidirectionalEdges_ConfirmCorrectEdgesValues() {
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
    public void GivenEmptyGraph_ConfirmIsEmpty() {
        assertTrue(graph.isEmpty());
    }

    @Test
    public void GivenGraphWithEdges_ConfirmIsNotEmpty() {
        graph.addEdge("A", "B", 1);
        assertFalse(graph.isEmpty());
    }

    @Test
    public void GivenGraphWithStringElements_WhenIterating_ConfirmCorrectOrder() {
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
        assertIterableEquals(list, possibleListB);
    }

    @Test
    public void GivenGraphWithIntegerElements_WhenIterating_ConfirmCorrectOrder() {
        MyAdjacencyListGraph<Integer> intGraph = new MyAdjacencyListGraph<>();

        intGraph.addEdge(2, 0, 1);
        intGraph.addEdge(2, 3, 1);
        intGraph.addEdge(0, 1, 1);
        intGraph.addEdge(3, 1, 1);
        intGraph.addEdge(3, 4, 1);
        intGraph.addEdge(4, 5, 1);
        intGraph.addEdge(1, 0, 2);

        List<Integer> intList = new ArrayList<>();
        for (int i : intGraph) {
            intList.add(i);
        }
        List<Integer> possibleList = List.of(2, 0, 3, 1, 4, 5);
        assertEquals(intList, possibleList);
    }

    @Test
    public void AStarTest() {
        MyAdjacencyListGraph<String> graph = new MyAdjacencyListGraph<>();

        // Add nodes
        String nodeA = "A";
        String nodeB = "B";
        String nodeC = "C";
        String nodeD = "D";
        String nodeE = "E";

        // Add edges (undirected for this case, but can also be directed if needed)
        graph.addEdge(nodeA, nodeB, 1);  // A -> B (weight 1)
        graph.addEdge(nodeA, nodeC, 2);  // A -> C (weight 2)
        graph.addEdge(nodeB, nodeC, 1);  // B -> C (weight 1)
        graph.addEdge(nodeB, nodeD, 3);  // B -> D (weight 3)
        graph.addEdge(nodeC, nodeD, 4);  // C -> D (weight 4)
        graph.addEdge(nodeD, nodeE, 1);  // D -> E (weight 1)

        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            return 1.0;
        };

        // Find the shortest path from node A to node E
        SaxList<SaxGraph.DirectedEdge<String>> result = graph.shortestPathAStar(nodeA, nodeE, estimator);

        // Verify the result - the expected path should be A -> B -> D -> E
        assertNotNull(result); // Ensure the result is not null
        assertEquals(3, result.size()); // 3 edges in the shortest path

        // Verify the edges are correct
        assertEquals(nodeA, result.get(0).from());
        assertEquals(nodeB, result.get(0).to());

        assertEquals(nodeB, result.get(1).from());
        assertEquals(nodeD, result.get(1).to());

        assertEquals(nodeD, result.get(2).from());
        assertEquals(nodeE, result.get(2).to());

        // Convert the result (edges) to a list of nodes
        SaxList<String> nodeList = graph.aStar(nodeA, nodeE, estimator);

        // Print the list of nodes
        System.out.println("Shortest path (nodes): " + nodeList);
    }

    @Test
    public void AStarTracks() throws FileNotFoundException {
        // read tracks.csv and construct a graph;
        // read stations.csv to get coordinates for estimation
        // link station code to station name and coordinates
        // get a-star the shortest path
        // print the path
        class aStarStation {
            String code;
            String name;
            Coordinate coordinate;

            public aStarStation(String code, String name, Coordinate coordinate) {
                this.code = code;
                this.name = name;
                this.coordinate = coordinate;
            }
        }

        CsvReader tracksReader = new CsvReader("resources/tracks.csv", true);
        CsvReader stationsReader = new CsvReader("resources/stations.csv", true);
        tracksReader.setSeparator(",");
        stationsReader.setSeparator(",");

        MyAdjacencyListGraph<String> graph = new MyAdjacencyListGraph<>();
        MySpHashMap<String, aStarStation> stations = new MySpHashMap<>();

        while (tracksReader.readLine()) {
            graph.addEdge(tracksReader.readString(0), tracksReader.readString(1), tracksReader.readDouble(3));
        }

        while (stationsReader.readLine()) {
            Coordinate coordinate = new Coordinate(stationsReader.readDouble(4), stationsReader.readDouble(5));
            aStarStation station = new aStarStation(stationsReader.readString(0), stationsReader.readString(1), coordinate);

            stations.add(station.code, station);
        }

        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            aStarStation currentStation = stations.get(current);
            aStarStation goalStation = stations.get(goal);

            return Coordinate.haversineDistance(currentStation.coordinate, goalStation.coordinate);
        };

        SaxList<String> result = graph.aStar("ASD", "DV", estimator);

        // convert codes to names
        SaxList<String> names = new MyArrayList<>();
        for (String code : result) {
            names.addLast(stations.get(code).name);
        }

        System.out.println("Shortest path (codes): " + result);
        System.out.println("Shortest path (stations): " + names);
    }

    @BeforeEach
    public void setUp() {
        graph = new MyAdjacencyListGraph<>();
    }
}

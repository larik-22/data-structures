package nl.saxion.cds.solution.data_structures;

import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.solution.data_models.Coordinate;
import nl.saxion.cds.utils.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyAdjacencyListGraphTest {
    private MyAdjacencyListGraph<String> graph;

    private static final String[] expectedCodesDeventerEnschede = {"DV", "DVC", "HON", "RSN", "WDN", "AML", "AMRI", "BN", "HGL", "ESK", "ES"};
    private static final String[] expectedCodesAmsterdamDeventer = {"ASD", "ASDM", "ASSP", "DMN", "WP", "NDB", "BSMZ", "HVSM", "HVS", "BRN", "AMF", "HVL", "APD", "APDO", "TWL", "DV"};
    private static final String AMSTERDAM_CODE = "ASD";
    private static final String DEVENTER_CODE = "DV";
    private static final String ENSCHEDE_CODE = "ES";

//    private static final double DEVENTER_ENSCHEDE_DISTANCE = 59.8;
//    private static final double AMSTERDAM_DEVENTER_DISTANCE = 98.2;

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

    // main test
    @Test
    public void GivenTwoStations_WhenUsingAStar_ConfirmShortestRouteIsDeterminedCorrectly() throws FileNotFoundException {
        MySpHashMap<String, SimpleStation> stations = readStations();
        MyAdjacencyListGraph<String> graph = readTracks();

        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            SimpleStation currentStation = stations.get(current);
            SimpleStation goalStation = stations.get(goal);

            return Coordinate.haversineDistance(currentStation.coordinate, goalStation.coordinate);
        };

        // TEST 1: Amsterdam to Deventer
        SaxList<String> amsToDv = graph.aStar(AMSTERDAM_CODE, DEVENTER_CODE, estimator);

        // Verify the result
        for (int i = 0; i < amsToDv.size(); i++) {
            assertEquals(expectedCodesAmsterdamDeventer[i], amsToDv.get(i));
        }

        // TEST 2: Deventer to Enschede
        SaxList<String> dvToEs = graph.aStar(DEVENTER_CODE, ENSCHEDE_CODE, estimator);

        // Verify the result
        for (int i = 0; i < dvToEs.size(); i++) {
            assertEquals(expectedCodesDeventerEnschede[i], dvToEs.get(i));
        }

    }

    @Test
    public void GivenGraphWithNoPath_WhenUsingAStar_ConfirmEmptyList() {
        graph.addEdge("A", "B", 1);
        graph.addEdge("C", "D", 1);

        SaxGraph.Estimator<String> estimator = (current, goal) -> 0.0;
        assertNull(graph.shortestPathAStar("A", "D", estimator));
    }

    // main test
    @Test
    public void GivenTwoStations_WhenUsingDijkstra_ConfirmShortestRouteIsDeterminedCorrectly() throws FileNotFoundException {
        MyAdjacencyListGraph<String> graph = readTracks();

        SaxList<String> dvToEs = graph.dijkstra(DEVENTER_CODE, ENSCHEDE_CODE);
        for (int i = 0; i < dvToEs.size(); i++) {
            assertEquals(expectedCodesDeventerEnschede[i], dvToEs.get(i));
        }

        SaxList<String> amsToDv = graph.dijkstra(AMSTERDAM_CODE, DEVENTER_CODE);
        for (int i = 0; i < expectedCodesAmsterdamDeventer.length; i++) {
            assertEquals(expectedCodesAmsterdamDeventer[i], amsToDv.get(i));
        }
    }

    @Test
    public void GivenGraph_WhenUsingMinimumCostSpanningTree_ConfirmCorrectTotalWeight() {
        // Create a more complex graph
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 2);
        graph.addEdge("B", "D", 5);
        graph.addEdge("C", "D", 3);
        graph.addEdge("D", "E", 6);
        graph.addEdge("C", "E", 7);
        graph.addEdge("A", "E", 8);
        graph.addEdge("B", "E", 9);

        // check graphviz output before...
        System.out.println(graph.graphViz("complexTest"));
        SaxGraph<String> mst = graph.minimumCostSpanningTree();

        // Expected total weight of the MST
        // The MST should include edges (A-B, B-C, C-D, D-E)
        // Total weight = 1 + 2 + 3 + 6 = 12
        assertEquals(12, mst.getTotalWeight());

        // Check graphviz output after...
        System.out.println(mst.graphViz("complexTest"));
    }

    @Test
    public void GivenEmptyListOfDirectedEdges_WhenConvertingEdgesToNodes_ConfirmEmptyList() {
        SaxList<SaxGraph.DirectedEdge<String>> edges = new MyArrayList<>();
        SaxList<String> path = graph.convertEdgesToNodes(edges);

        assertTrue(path.isEmpty());
    }

    @Test
    public void GivenGraphWithEdges_WhenGettingVertices_ConfirmCorrectVertices() {
        graph.addEdge("A", "B", 1);

        SaxList<SaxGraph.DirectedEdge<String>> vertices = graph.getVertices();
        assertEquals("A", vertices.get(0).from());
        assertEquals("B", vertices.get(0).to());
    }

    @Test
    public void GivenGraphWithNoEdges_WhenGettingIterator_ConfirmEmptyIterator() {
        Iterator<String> iterator = graph.iterator();
        assertNull(iterator);
    }

    @BeforeEach
    public void setUp() {
        graph = new MyAdjacencyListGraph<>();
    }

    private MySpHashMap<String, SimpleStation> readStations() throws FileNotFoundException {
        CsvReader reader = new CsvReader("resources/stations.csv", true);
        reader.setSeparator(",");

        MySpHashMap<String, SimpleStation> stations = new MySpHashMap<>();

        while (reader.readLine()) {
            Coordinate coordinate = new Coordinate(reader.readDouble(4), reader.readDouble(5));
            SimpleStation station = new SimpleStation(reader.readString(0), reader.readString(1), coordinate);

            stations.add(station.code, station);
        }

        return stations;
    }

    private MyAdjacencyListGraph<String> readTracks() throws FileNotFoundException {
        CsvReader reader = new CsvReader("resources/tracks.csv", true);
        reader.setSeparator(",");

        MyAdjacencyListGraph<String> graph = new MyAdjacencyListGraph<>();

        while (reader.readLine()) {
            graph.addEdge(reader.readString(0), reader.readString(1), reader.readDouble(3));
        }

        return graph;
    }

    static class SimpleStation {
        String code;
        String name;
        Coordinate coordinate;

        public SimpleStation(String code, String name, Coordinate coordinate) {
            this.code = code;
            this.name = name;
            this.coordinate = coordinate;
        }
    }
}

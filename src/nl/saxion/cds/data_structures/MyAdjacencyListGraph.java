package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;

import java.util.Iterator;

public class MyAdjacencyListGraph<V> implements SaxGraph<V> {
    private final MySpHashMap<V, SaxList<DirectedEdge<V>>> edges;

    public MyAdjacencyListGraph() {
        edges = new MySpHashMap<>();
    }

    /**
     * The JavaDoc in interface is contradicting itself.
     * It says that if a node doesn't exist, it is automatically added. Why the do we need an exception then?
     * @param fromValue originating node value
     * @param toValue   connected node value
     * @param weight   weight of the edge
     * @throws KeyNotFoundException
     */
    @Override
    public void addEdge(V fromValue, V toValue, double weight) throws KeyNotFoundException {
        SaxList<DirectedEdge<V>> fromEdges = getOrCreateVertex(fromValue);
        getOrCreateVertex(toValue); // Ensure that the toValue exists
        DirectedEdge<V> edge = new DirectedEdge<>(fromValue, toValue, weight);
        fromEdges.addLast(edge);
    }

    /**
     * Get or create a vertex
     * @param value the value of the vertex
     * @return the list of edges of given vertex
     */
    public SaxList<DirectedEdge<V>> getOrCreateVertex(V value) {
        if (!edges.contains(value)) {
            MyArrayList<DirectedEdge<V>> list = new MyArrayList<>();
            edges.add(value, list);
            return list;
        } else {
            return edges.get(value);
        }
    }

    @Override
    public void addEdgeBidirectional(V fromValue, V toValue, double weight) {
        addEdge(fromValue, toValue, weight);
        addEdge(toValue, fromValue, weight);
    }

    @Override
    public SaxList<DirectedEdge<V>> getEdges(V value) {
        return edges.get(value);
    }

    @Override
    public double getTotalWeight() {
        double totalWeight = 0;
        for (V vertex : edges.getKeys()) {
            SaxList<DirectedEdge<V>> vertexEdges = edges.get(vertex);
            for (DirectedEdge<V> edge : vertexEdges) {
                totalWeight += edge.weight();
            }
        }

        return totalWeight;
    }

    @Override
    public SaxGraph<V> shortestPathsDijkstra(V startNode) {
        return null;
    }

    @Override
    public SaxList<DirectedEdge<V>> shortestPathAStar(V startNode, V endNode, Estimator<V> estimator) {
        return null;
    }

    @Override
    public SaxGraph<V> minimumCostSpanningTree() {
        return null;
    }

    @Override
    public Iterator<V> iterator() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return edges.isEmpty();
    }

    @Override
    public int size() {
        return edges.size();
    }

    @Override
    public String graphViz(String name) {
        return "";
    }
}

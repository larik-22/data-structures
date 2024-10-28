package nl.saxion.cds.solution.data_structures;

import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;

import java.util.Iterator;

public class MyAdjacencyListGraph<V> implements SaxGraph<V> {
    private final MySpHashMap<V, SaxList<DirectedEdge<V>>> edges;

    public MyAdjacencyListGraph() {
        edges = new MySpHashMap<>();
    }

    public SaxList<DirectedEdge<V>> getVertices(){
        SaxList<DirectedEdge<V>> vertices = new MyArrayList<>();

        for (V vertex : edges.getKeys()) {
            SaxList<DirectedEdge<V>> vertexEdges = edges.get(vertex);
            for (DirectedEdge<V> edge : vertexEdges) {
                vertices.addLast(edge);
            }
        }

        return vertices;
    }

    /**
     * The JavaDoc in interface is contradicting itself.
     * It says that if a node doesn't exist, it is automatically added. Why the do we need an exception then?
     *
     * @param fromValue originating node value
     * @param toValue   connected node value
     * @param weight    weight of the edge
     */
    @Override
    public void addEdge(V fromValue, V toValue, double weight) throws KeyNotFoundException {
        SaxList<DirectedEdge<V>> fromEdges = getOrCreateVertex(fromValue);
        getOrCreateVertex(toValue);
        DirectedEdge<V> edge = new DirectedEdge<>(fromValue, toValue, weight);
        fromEdges.addLast(edge);
    }

    /**
     * Get or create a vertex
     *
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
    public Iterator<V> iterator() {
        //breadth first search
        MyQueue<V> toVisit = new MyQueue<>();
        SaxList<V> visited = new MyArrayList<>();
        SaxList<V> traversed = new MyArrayList<>();

        if (!edges.isEmpty()) {
            toVisit.enqueue(edges.getKeys().get(0));

            while (!toVisit.isEmpty()) {
                V next = toVisit.dequeue();
                if (!visited.contains(next)) {
                    visited.addLast(next);
                    traversed.addLast(next);

                    SaxList<DirectedEdge<V>> edges = getEdges(next);
                    for (DirectedEdge<V> edge : edges) {
                        if (!visited.contains(edge.to())) {
                            toVisit.enqueue(edge.to());
                        }
                    }
                }
            }

            return traversed.iterator();
        } else {
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return edges.isEmpty();
    }

    @Override
    public int size() {
        return edges.size();
    }

    /**
     * This is the pseudocode from the sheets
     *
     * @param startNode the node to start searching
     * @param endNode   the target node
     * @param estimator a (handler) function to estimate the distance (weight) between two nodes
     * @return the list of DirectedEdges from startNode to endNode
     */
    @Override
    public SaxList<DirectedEdge<V>> shortestPathAStar(V startNode, V endNode, Estimator<V> estimator) {
        MyMinHeap<AStarNode> openList = new MyMinHeap<>(AStarNode::compareTo);
        MySpHashMap<V, AStarNode> closedList = new MySpHashMap<>();
        AStarNode start = new AStarNode(null, 0, estimator.estimate(startNode, endNode), null);
        openList.enqueue(start);

        while (!openList.isEmpty()) {
            AStarNode currentNode = openList.dequeue();

            // check if goal reached
            if (currentNode.edge != null && currentNode.edge.to().equals(endNode)) {
                // return the path from start to end
                return reconstructAStarPath(currentNode);
            }

            V current = (currentNode.edge == null) ? startNode : currentNode.edge.to();
            if (closedList.contains(current)) {
                continue;
            }

            // Add the node to the closed list
            closedList.add(current, currentNode);
            for (DirectedEdge<V> neighborEdge : getEdges(current)) {
                V neighbor = neighborEdge.to();

                if (closedList.contains(neighbor)) {
                    continue;
                }

                double neighborG = currentNode.g + neighborEdge.weight();
                double neighborH = estimator.estimate(neighbor, endNode);
                AStarNode neighborNode = new AStarNode(neighborEdge, neighborG, neighborH, currentNode);
                openList.enqueue(neighborNode);
            }
        }

        return null;
    }

    /**
     * Find the shortest path from the start node to the end node using the A* algorithm
     * Returns a list of nodes from the start node to the end node
     *
     * @param startNode the start node
     * @param endNode   the end node
     * @param estimator the heuristic function to estimate the cost from a node to the end node
     * @return the list of nodes from the start node to the end node
     */
    public SaxList<V> aStar(V startNode, V endNode, Estimator<V> estimator) {
        // Get the list of edges using the A* algorithm
        SaxList<DirectedEdge<V>> edgeList = shortestPathAStar(startNode, endNode, estimator);

        // Convert the edges to a list of nodes
        return convertEdgesToNodes(edgeList);
    }

    /**
     * Reconstruct the path from the goal node to the start node
     *
     * @param goalNode the goal node
     * @return the path from the start node to the goal node as a list of edges
     */
    private SaxList<DirectedEdge<V>> reconstructAStarPath(AStarNode goalNode) {
        SaxList<DirectedEdge<V>> path = new MyArrayList<>();
        AStarNode currentNode = goalNode;

        // Traverse the path from the goal node to the start node
        while (currentNode.edge != null) {
            path.addFirst(currentNode.edge);
            currentNode = currentNode.parent;
        }

        return path;
    }

    /**
     * Convert a list of edges to a list of nodes
     *
     * @param pathOfEdges the list of edges
     * @return the path of nodes from starting node to the goal node
     */
    public SaxList<V> convertEdgesToNodes(SaxList<DirectedEdge<V>> pathOfEdges) {
        SaxList<V> path = new MyArrayList<>();
        if (pathOfEdges.isEmpty()) {
            return path;
        }

        // Start node goes first
        DirectedEdge<V> firstEdge = pathOfEdges.get(0);
        path.addLast(firstEdge.from());

        for (DirectedEdge<V> edge : pathOfEdges) {
            path.addLast(edge.to());
        }

        return path;
    }

    @Override
    public SaxGraph<V> shortestPathsDijkstra(V startNode) {
        SaxGraph<V> result = new MyAdjacencyListGraph<>();
        MyMinHeap<SaxGraph.DirectedEdge<V>> openList = new MyMinHeap<>(SaxGraph.DirectedEdge::compareTo);
        MySpHashMap<V, SaxGraph.DirectedEdge<V>> closedList = new MySpHashMap<>();

        SaxGraph.DirectedEdge<V> startEdge = new SaxGraph.DirectedEdge<>(startNode, startNode, 0);
        openList.enqueue(startEdge);

        while (!openList.isEmpty()) {
            SaxGraph.DirectedEdge<V> currentNode = openList.dequeue();

            if (!closedList.contains(currentNode.from())) {
                // mark as visited
                closedList.add(currentNode.from(), currentNode);

                // add edge
                result.addEdge(currentNode.from(), currentNode.to(), currentNode.weight());

                // Explore neighboring edges
                for (SaxGraph.DirectedEdge<V> neighborEdge : getEdges(currentNode.from())) {
                    // Only consider unvisited neighbors
                    if (!closedList.contains(neighborEdge.to())) {
                        // Add the neighborEdge with updated weight to the queue
                        SaxGraph.DirectedEdge<V> neighborNode = new SaxGraph.DirectedEdge<>(neighborEdge.to(), currentNode.from(), neighborEdge.weight() + currentNode.weight());
                        openList.enqueue(neighborNode);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Construct the path from the start node to the end node using Dijkstra's algorithm
     *
     * @param startNode the start node
     * @param endNode   the end node
     * @return the list of edges from the start node to the end node
     */
    public SaxList<DirectedEdge<V>> getDijkstraEdges(V startNode, V endNode) {
        SaxGraph<V> result = shortestPathsDijkstra(startNode);
        SaxList<DirectedEdge<V>> path = new MyArrayList<>();
        V currentNode = endNode;

        while (!currentNode.equals(startNode)) {
            DirectedEdge<V> edge = result.getEdges(currentNode).get(0);
            path.addFirst(new DirectedEdge<>(edge.to(), edge.from(), edge.weight()));

            currentNode = edge.to();
        }
        return path;
    }

    /**
     * Find the shortest path from the start node to the end node using Dijkstra's algorithm.
     * We first get the edges and convert them to nodes.
     *
     * @param startNode the start node
     * @param endNode   the end node
     * @return the list of nodes from the start node to the end node
     */
    public SaxList<V> dijkstra(V startNode, V endNode) {
        SaxList<DirectedEdge<V>> pathOfEdges = getDijkstraEdges(startNode, endNode);
        return convertEdgesToNodes(pathOfEdges);
    }

    @Override
    public SaxGraph<V> minimumCostSpanningTree() {
        // Prim's algorithm
        SaxGraph<V> mst = new MyAdjacencyListGraph<>();
        MyMinHeap<DirectedEdge<V>> edges = new MyMinHeap<>(DirectedEdge::compareTo);
        MySpHashMap<V, Boolean> inMST = new MySpHashMap<>();

        // Select any node as root (I select first one)
        V startNode = this.edges.getKeys().get(0);
        inMST.add(startNode, true);

        // Fill the priority queue with edges from the start node
        for (DirectedEdge<V> edge : getEdges(startNode)) {
            edges.enqueue(edge);
        }

        // While there are still edges to consider
        while (!edges.isEmpty()) {
            DirectedEdge<V> edge = edges.dequeue();
            V from = edge.from();
            V to = edge.to();

            // Check if both nodes have been already added
            if (inMST.contains(to) && inMST.get(to)) {
                continue;
            }

            // Add new edge
            mst.addEdge(from, to, edge.weight());

            // Mark the nodes as visited
            if (!inMST.contains(from)) {
                inMST.add(from, true);
            }

            if (!inMST.contains(to)) {
                inMST.add(to, true);
            }

            // Add edges from the newly added node to the priority queue
            for (DirectedEdge<V> neighborEdge : getEdges(to)) {
                if (!inMST.contains(neighborEdge.to())) {
                    edges.enqueue(neighborEdge);
                }
            }
        }

        return mst;
    }

    @Override
    public String graphViz(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ").append(name).append(" {\n");
        for (V vertex : edges.getKeys()) {
            SaxList<DirectedEdge<V>> vertexEdges = edges.get(vertex);
            for (DirectedEdge<V> edge : vertexEdges) {
                sb.append("    ").append(vertex).append(" -> ").append(edge.to())
                        .append(" [label=\"").append(edge.weight()).append("\"];\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * A node in the A* algorithm.
     */
    class AStarNode implements Comparable<AStarNode> {
        final DirectedEdge<V> edge;
        final double g;
        final double h;
        final AStarNode parent;

        public AStarNode(DirectedEdge<V> edge, double g, double h, AStarNode parent) {
            this.edge = edge;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        public double getF() {
            return g + h;
        }

        @Override
        public int compareTo(AStarNode other) {
            return Double.compare(getF(), other.getF());
        }
    }
}

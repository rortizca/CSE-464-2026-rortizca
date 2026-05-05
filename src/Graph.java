import guru.nidi.graphviz.model.MutableAttributed;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Format;
import static guru.nidi.graphviz.model.Factory.*;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.util.*;

public class Graph {
    MutableGraph g;

    public enum Algorithm {
        BFS,
        DFS,
        RANDOM
    }

    private Algorithm algo = Algorithm.BFS;

    public static class Path {
        MutableNode[] path;

        public Path(MutableNode[] nodes) {
            this.path = nodes;
        }
    }

    public Graph() {
        this.g = mutGraph("graph").setDirected(true);
    }

    // FEATURE 1
    public void parseGraph(String filepath) throws Exception {
        try {
            this.g = new Parser().read(new File(filepath));
        } catch (IOException e) {
            System.err.println("Error reading DOT file: " + filepath);

            throw new RuntimeException(e);
        }
    }

    // Helper function to print visit history
    public void printVisitHistory(List<MutableNode> path) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < path.size(); i++) {
            if (i > 0) {
                s.append("-");
            }

            s.append(path.get(i).name());
        }

        System.out.println("Visit Node History: " + s.toString());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Number of nodes: ").append(this.g.nodes().size()).append("\n");

        sb.append("Nodes:\n");

        for (MutableNode node : this.g.nodes()) {
            sb.append(node.name()).append("\n");
        }

        int edgeCount = 0;

        sb.append("Edges:\n");

        for (MutableNode node : this.g.nodes()) {
            node.links().forEach(link -> {
                sb.append(node.name())
                        .append(" -> ")
                        .append(link.to().name())
                        .append("\n");

            });
            edgeCount += node.links().size();
        }

        sb.append("Number of Edges: ").append(edgeCount).append("\n");

        return sb.toString();
    }

    public void outputGraph(String filepath) {
        String output = toString();
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // FEATURE 2
    public void addNode(String label) {
        for (MutableNode node : this.g.nodes()) {
            if (node.name().toString().equals(label)) {
                System.out.println("Duplicate Node Found: " + label);
                return;
            }

        }

        this.g.add(mutNode(label));
    }

    public void addNodes(String[] labels) {

        for (String label : labels) {
            addNode(label);
        }
    }

    // FEATURE 3
    public void addEdge(String srcLabel, String dstLabel) {
        // IMPLEMENTED

        MutableNode srcNode = null;
        MutableNode dstNode = null;

        for (MutableNode node : this.g.nodes()) {
            if (node.name().toString().equals(srcLabel)) {
                srcNode = node;
            }

            if (node.name().toString().equals(dstLabel)) {
                dstNode = node;
            }
        }

        if (srcNode == null || dstNode == null) {
            System.out.println("Could not find source or destination node");
            return;
        }

        for (var link : srcNode.links()) {
            if (link.to().name().toString().equals(dstLabel)) {
                System.out.println("Requested edge already exists: " + srcLabel + " -> " + dstLabel);
                return;
            }
        }

        srcNode.addLink(dstNode);
    }

    // FEATURE 4
    public void outputDotGraph(String path) {
        // IMPLEMENTED
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(this.g.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void outputGraphics(String path, String format) {
        // Implemented
        try {

            Format fmt;

            if (format.equalsIgnoreCase("png")) {
                fmt = Format.PNG;
            } else {
                throw new IllegalArgumentException("Unsupported format");
            }

            Graphviz.fromGraph(this.g)
                    .render(fmt)
                    .toFile(new File(path));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MutableNode findNode(String label) {
        for (MutableNode node : this.g.nodes()) {
            if (node.name().toString().equals(label)) {
                return node;
            }
        }
        return null;
    }

    private MutableNode findNode(MutableGraph graph, String label) {
        for (MutableNode node : graph.nodes()) {
            if (node.name().toString().equals(label)) {
                return node;
            }
        }
        return null;
    }

    public void removeNode(String label) {
        MutableNode delNode = findNode(label);

        if (delNode == null) {
            throw new IllegalArgumentException(
                    "Chosen Node not found"
            );
        }

        MutableGraph editedGraph = mutGraph("graph").setDirected(true);

        // Add edges from current graph except specified label;
        for (MutableNode node : this.g.nodes()) {
            if (!node.name().toString().equals(label)) {
                editedGraph.add(mutNode(node.name().toString()));
            }
        }

        // Reproduce edges in edited graph
        for (MutableNode node : this.g.nodes()) {
            if (node.name().toString().equals(label)) continue;

            MutableNode srcNode = findNode(editedGraph, node.name().toString());

            node.links().forEach(link -> {
                String dstNode = link.to().name().toString();

                if (!dstNode.equals(label)) {
                    MutableNode newDstNode =
                            findNode(editedGraph, dstNode);

                    if (newDstNode != null) {
                        srcNode.addLink(newDstNode);
                    }
                }
            });
        }

        this.g = editedGraph;


    }

    public void removeNodes(String[] labels) {
        for (String label : labels) {
            removeNode(label);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        MutableNode src = findNode(srcLabel);
        MutableNode dst = findNode(dstLabel);

        if (src == null || dst == null) {
            throw new IllegalArgumentException(
                    "Source or destination node not found"
            );
        }

        boolean removed = src.links().removeIf(link ->
                link.to().name().toString().equals(dstLabel));

        if (!removed) {
            throw new IllegalArgumentException(
                    "Edge could not be found: " + srcLabel + " -> " + dstLabel
            );
        }

    }

    public Path GraphSearch(MutableNode src, MutableNode dst, Algorithm algo) {
        if (src == null || dst == null) {
            throw new IllegalArgumentException("One or two nodes are null");
        }

        if (src.equals(dst)) {
            System.out.println("Visit Node History: " + src.name());
            System.out.println("Found target node: " + src.name());
            return new Path(new MutableNode[]{src});
        }

        switch (algo) {
            case BFS:
                return new BFS(this).search(src, dst);
            case DFS:
                return new DFS(this).search(src, dst);
            case RANDOM:
                return randomWalk(src, dst);
            default:
                throw new IllegalArgumentException("Unknown algorithm");
        }
    }

    // Helper Function to find sorted neighbors
    public List<MutableNode> getSortedNeighbors(MutableNode node) {
        List<MutableNode> neighbors = new ArrayList<>();

        node.links().forEach(link -> {
            MutableNode n = findNode(link.to().name().toString());
            if (n != null) {
                neighbors.add(n);
            }
        });

        neighbors.sort(Comparator.comparing(n -> n.name().toString()));

        return neighbors;
    }

    // Helper Function to build path from parent
    public List<MutableNode> buildPath(MutableNode node, Map<MutableNode, MutableNode> parent) {
        List<MutableNode> path = new ArrayList<>();

        while (node != null) {
            path.add(node);
            node = parent.get(node);
        }

        Collections.reverse(path);
        return path;
    }

    // FEATURE: Random Walk Algorithm
    private Path randomWalk(MutableNode src, MutableNode dst) {
        Random rand = new Random();

        Set<MutableNode> visited = new HashSet<>();
        List<MutableNode> path = new ArrayList<>();

        MutableNode current = src;
        visited.add(current);
        path.add(current);

        printVisitHistory(path);

        while (true) {
            List<MutableNode> neighbors = getSortedNeighbors(current); // ADD HELPER FUNCTION

            List<MutableNode> unvisited = new ArrayList<>();
            for (MutableNode n : neighbors) {
                if (!visited.contains(n)) {
                    unvisited.add(n);
                }
            }

            if (unvisited.isEmpty()) {
                System.out.println("Reached dead end at node: " + current.name());
                return new Path(path.toArray(new MutableNode[0]));
            }

            current = unvisited.get(rand.nextInt(unvisited.size()));
            visited.add(current);
            path.add(current);

            printVisitHistory(path);

            if (current.equals(dst)) {
                System.out.println("Found target node: " + current.name());
                return new Path(path.toArray(new MutableNode[0]));
            }
        }
    }

}

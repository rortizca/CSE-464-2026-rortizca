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

public class Graph {
    MutableGraph g;

    public Graph() {
        this.g = null;
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


}

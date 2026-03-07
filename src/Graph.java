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
}

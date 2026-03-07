import guru.nidi.graphviz.model.MutableAttributed;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Format;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

public class Main {
    public static void main(String[] args) {

    }

    public static MutableGraph parseGraph(String filepath) throws Exception {
        try {
            return new Parser().read(new File(filepath));
        } catch (IOException e) {
            System.err.println("Error reading DOT file: " + filepath);

            throw new RuntimeException(e);
        }
    }

    public static String toString(MutableGraph graph) {
       return graph.toString();
    }

    public static void outputGraph(MutableGraph g, String filepath) {
        String output = toString(g);
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
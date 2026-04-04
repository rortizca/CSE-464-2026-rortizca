import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class GraphTests {
    Graph test_graph;

    @Before
    public void setup() {
        System.out.println("in setup");
        test_graph = new Graph();
    }

    @After
    public void tearDown() {
        System.out.println("in teardown");
    }


    @Test
    public void testParseGraph() throws Exception {
        // Arrange

        // Act
        test_graph.parseGraph("test/resources/parse1.dot");
        String output = test_graph.toString();

        // Assert
        String expected = Files.readString(Paths.get("test/resources/parseExpected.txt"));
        Assert.assertEquals(expected.trim(), output.trim());

    }

    @Test
    public void testAddNode() throws Exception {
        // Arrange

        test_graph.parseGraph("test/resources/addNode1.dot");

        // Act
        test_graph.addNode("C");

        String output = test_graph.toString();

        // Assert
        String expected = Files.readString(Paths.get("test/resources/addNodeExpected.txt"));
        Assert.assertEquals(expected.trim(), output.trim());

    }

    @Test
    public void testDuplicateNode() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/addNode1.dot");

        // Act
        test_graph.addNode("A"); // Node already present in input

        // Assert
        String output = test_graph.toString();

        Assert.assertTrue(output.contains("Number of nodes: 2"));

    }

    @Test
    public void testAddNodes() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/addNode1.dot");

        // Act
        String[] nodes = {"C", "D"};

        test_graph.addNodes(nodes);

        String output = test_graph.toString();

        // Assert
        String expected = Files.readString(Paths.get("test/resources/addNodesExpected.txt"));
        Assert.assertEquals(expected.trim(), output.trim());

    }

    @Test
    public void testAddEdge() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/addEdge1.dot");

        test_graph.addEdge("A", "B");

        String output = test_graph.toString();

        String expected = Files.readString(Paths.get("test/resources/addEdgeExpected.txt"));

        Assert.assertEquals(expected.trim(), output.trim());

    }

    @Test
    public void testDuplicateEdge() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/addEdge1.dot");

        // Act
        test_graph.addEdge("A", "B");
        test_graph.addEdge("A", "B");

        // Assert
        String output = test_graph.toString();

        Assert.assertTrue(output.contains("Number of Edges: 1"));
    }

    @Test
    public void testAddToMissingNode() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/addEdge1.dot");

        // Act
        test_graph.addEdge("A", "C"); // C is not present in the input

        // Assert
        String output = test_graph.toString();

        Assert.assertTrue(output.contains("Number of Edges: 0"));

    }

    @Test
    public void testOutputDOTGraph() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/outputDOTinput1.dot");

        // Act
        String outputPath = "test/resources/output_test.dot";

        test_graph.outputDotGraph(outputPath);

        File file = new File(outputPath);

        // Assert
        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.length() > 0);
    }

    @Test
    public void testOutputGraphics() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/outputDotinput1.dot");

        // Act
        String outputPath = "test/resources/output_test.png";

        test_graph.outputGraphics(outputPath, "png");

        File file = new File(outputPath);

        // Assert
        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.length() > 0);

    }

    @Test
    public void testRemoveNode() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/testRemoveGraph.dot");

        // Act
        test_graph.removeNode("B");

        String output = test_graph.toString();

        // System.out.println(output);
        // Assert
        Assert.assertFalse(output.contains("B"));
    }

    @Test
    public void testRemoveNonexistentNode() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/testRemoveGraph.dot");

        // Act && Asset
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            test_graph.removeNode("Z");
        });
    }

    @Test
    public void testRemoveNodes() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/testRemoveGraph.dot");

        // Act
        test_graph.removeNodes(new String[]{"A", "C"});

        String output = test_graph.toString();

        // Assert
        Assert.assertFalse(output.contains("A"));
        Assert.assertFalse(output.contains("C"));
    }

    @Test
    public void testRemoveNonexistentNodes() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/testRemoveGraph.dot");

        // Act && Asset
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            test_graph.removeNodes(new String[]{"A","Z"});
        });
    }

    @Test
    public void testRemoveEdge() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/testRemoveGraph.dot");

        // Act
        test_graph.removeEdge("A", "B");

        String output = test_graph.toString();

        // Assert
        Assert.assertFalse(output.contains("A -> B"));
    }

    @Test
    public void testRemoveNonexistentEdge() throws Exception {
        // Arrange
        test_graph.parseGraph("test/resources/testRemoveGraph.dot");

        // Act && Asset
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            test_graph.removeEdge("A", "C");
        });
    }


}

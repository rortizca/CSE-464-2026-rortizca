import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
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
}

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
        String expected = Files.readString(Paths.get("test/resources/expected1.txt"));
        Assert.assertEquals(expected.trim(), output.trim());

    }
}

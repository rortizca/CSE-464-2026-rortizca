public class Main {
    public static void main(String[] args) {
        try {
            Graph g = new Graph();

            // Load the provided DOT file
            g.parseGraph("input.dot");

            System.out.println(g);

            // Pick source and destination node
            var src = g.findNode("a");
            var dst = g.findNode("h");


            /*
            System.out.println("\n===== BFS =====");
            g.GraphSearch(src, dst, Graph.Algorithm.BFS);


             System.out.println("\n===== DFS =====");
            g.GraphSearch(src, dst, Graph.Algorithm.DFS);
            */


            System.out.println("\n===== RANDOM WALK =====");
            for (int i = 0; i < 9; i++) {
                System.out.println("\nRun #" + (i + 1));
                g.GraphSearch(src, dst, Graph.Algorithm.RANDOM);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
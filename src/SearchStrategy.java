import guru.nidi.graphviz.model.MutableNode;

public interface SearchStrategy {
    Graph.Path search(MutableNode src, MutableNode dst);
}

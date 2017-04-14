package Model;

import java.util.HashSet;

/**
 * Created by Tomek on 2017-03-28.
 */
public class Edge {
    private Vertex first;
    private Vertex second;

    public Edge(Vertex first, Vertex second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        return first == ((Edge)obj).getSecond() && second == ((Edge)obj).getFirst()
                || first == ((Edge)obj).getFirst() && second == ((Edge)obj).getSecond();
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    public boolean sameColor(Edge e) {
        HashSet<Integer> fSet = new HashSet<>();
        HashSet<Integer> sSet = new HashSet<>();

        fSet.add(first.getColor());
        fSet.add(second.getColor());

        sSet.add(e.getFirst().getColor());
        sSet.add(e.getSecond().getColor());

        return fSet.containsAll(sSet);
    }

    public Vertex getFirst() {
        return first;
    }

    public void setFirst(Vertex first) {
        this.first = first;
    }

    public Vertex getSecond() {
        return second;
    }

    public void setSecond(Vertex second) {
        this.second = second;
    }
}

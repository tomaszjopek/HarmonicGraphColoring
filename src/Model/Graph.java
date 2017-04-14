package Model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tomek on 2017-03-28.
 */
public class Graph {
    private Vertex[] vertices;
    private List<Integer> colors;
    private List<Edge> edges;

    public Graph() {
        colors = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void generate(int n) {
        vertices = new Vertex[n*n];

        int counter = 0;

        for(int i=0;i<n;i++) {
            for(int j = 0; j < n; j++) {
                vertices[counter++] = new Vertex(i, j);
            }
        }

        int cNum = n%2 == 0 ? 2*n : 2*n + 1;
//        Random rand = new Random();

//        HashSet<Integer> tmpSet = new HashSet<>();

//        while(tmpSet.size() != cNum) {
//            tmpSet.add(rand.nextInt());
//        }

        colors = new ArrayList<>(cNum);
        for(int i = 0; i < cNum; i++)
            colors.add(i);

        for(int i = 0; i < n*n; i++) {
            int x = vertices[i].getX();
            int y =  vertices[i].getY();

            int xLeft = x - 1;
            int yLeft = y;

            int xUp = x;
            int yUp = y - 1;

            int xRight = x + 1;
            int yRight = y;

            int xDown = x;
            int yDown = y + 1;

            Vertex tmp = getVertex(vertices, xLeft, yLeft);
            if(tmp != null) {
                vertices[i].getConstraints().add(tmp);
                edges.add(new Edge(vertices[i], tmp));
            }

            tmp = getVertex(vertices, xUp, yUp);
            if(tmp != null) {
                vertices[i].getConstraints().add(tmp);
                edges.add(new Edge(vertices[i], tmp));
            }

            tmp = getVertex(vertices, xDown, yDown);
            if(tmp != null) {
                vertices[i].getConstraints().add(tmp);
                edges.add(new Edge(vertices[i], tmp));
            }

            tmp = getVertex(vertices, xRight, yRight);
            if(tmp != null) {
                vertices[i].getConstraints().add(tmp);
                edges.add(new Edge(vertices[i], tmp));
            }

            vertices[i].setDomain(new ArrayList<>(colors));
            vertices[i].setPrimaryDomain(new ArrayList<>(colors));
        }

        edges = edges.stream().distinct().collect(Collectors.toList());
    }

    private Vertex getVertex(Vertex[] vertices, int x, int y) {
        for(int i = 0; i < vertices.length; i++)
            if(vertices[i].getX() == x && vertices[i].getY() == y)
                return vertices[i];

        return null;
    }

    public void print(int n) {
        for(int i = 0; i < vertices.length ; i++) {

            String str = String.format("%3d ", vertices[i].getColor());
            System.out.print(str);

            if(i != 0 && (i+1) % n == 0)
                System.out.println();
        }

        System.out.println("\n");
    }

    /**
     * Backtracking algorithm with fixed order of variables
     */
    public void backtrackingAlgorithm() {
        List<Vertex> unassigned = new LinkedList<>();
        unassigned.addAll(Arrays.asList(vertices));

        Deque<Vertex> queue = new ArrayDeque<>();
        queue.addFirst(unassigned.remove(0));

        int dCounter;
        boolean flag = false;
        while(!unassigned.isEmpty() || !isHarmonicGraphColored(queue)) {
            if(!isHarmonicGraphColored(queue) || flag) {
                dCounter =  colors.indexOf(queue.peek().getColor());
                if(dCounter < colors.size()-1) {
                    queue.peek().setColor(colors.get(++dCounter));
                    flag = false;
                }
                else {
                    Vertex tmp = queue.poll();
                    tmp.setColor(colors.get(0));
                    unassigned.add(0,tmp);
                    flag = true;
                }
            }
            else {
                queue.addFirst(unassigned.remove(0));
            }
        }
    }

    public void backtrackingAlgorithmWithHeuristic(Comparator<Vertex> comparator) {
        List<Vertex> unassigned = new LinkedList<>();
        unassigned.addAll(Arrays.asList(vertices));

        Deque<Vertex> queue = new ArrayDeque<>();
        unassigned.sort(comparator);
        queue.addFirst(unassigned.remove(0));
        int dCounter;
        boolean flag = false;
        while(!unassigned.isEmpty() || !isHarmonicGraphColored(queue)) {
            if(!isHarmonicGraphColored(queue) || flag) {
                dCounter =  colors.indexOf(queue.peek().getColor());
                if(dCounter < colors.size()-1) {
                    queue.peek().setColor(colors.get(++dCounter));
                    flag = false;
                }
                else {
                    Vertex tmp = queue.poll();
                    tmp.setColor(colors.get(0));
                    unassigned.add(0,tmp);
                    flag = true;
                }
            }
            else {
                unassigned.sort(comparator);
                queue.addFirst(unassigned.remove(0));
            }
        }
    }


    private boolean isGraphColored(Deque<Vertex> vertices) {
        if(vertices.size() == 1)
            return true;

        for(Vertex tmp : vertices) {
            if(!tmp.isCorrect())
                return false;
        }
        return true;
    }

    private boolean isHarmonicGraphColored(Deque<Vertex> vertices) {
        List<Edge> invokedEdges = edges.stream()
                .filter(edge -> vertices.contains(edge.getFirst()) && vertices.contains(edge.getSecond())).collect(Collectors.toList());

        if(invokedEdges.size() == 1) {
            if(invokedEdges.get(0).getFirst().getColor() == invokedEdges.get(0).getSecond().getColor())
                return false;
        }

        for(int i = 0; i < invokedEdges.size()-1;i++) {
            for(int j = i+1; j < invokedEdges.size(); j++) {
                if(invokedEdges.get(i).sameColor(invokedEdges.get(j)))
                    return false;
            }
        }

        return true;
    }


    /**
     * forward checking algorithms with fixed order of variable
      */
    public void forwardCheckingAlgorithm() {
        List<Vertex> unassigned = new LinkedList<>();
        unassigned.addAll(Arrays.asList(vertices));

        Deque<Vertex> queue = new ArrayDeque<>();
        queue.addFirst(unassigned.remove(0));

        boolean flag = false;
        while(!unassigned.isEmpty() || !isHarmonicGraphColored(queue)) {
            if(!isHarmonicGraphColored(queue) || flag) {
                if(!queue.getFirst().isDomainEmpty()) {
                    queue.getFirst().setColor(queue.getFirst().getFromDomain());
                    unassigned.forEach(Vertex::updateDomain);
                    flag = false;
                }
                else {
                    Vertex tmp = queue.poll();
                    //    tmp.update();
                    unassigned.add(0,tmp);
                    flag = true;
                    unassigned.forEach(Vertex::updateDomain);
                }
            }
            else {
                queue.addFirst(unassigned.remove(0));
            }

        }
    }

    public void forwardCheckingAlgorithmValueHeuristic() {
        List<Vertex> unassigned = new LinkedList<>();
        unassigned.addAll(Arrays.asList(vertices));

        Deque<Vertex> queue = new ArrayDeque<>();
        queue.addFirst(unassigned.remove(0));

        boolean flag = false;
        while(!unassigned.isEmpty() || !isHarmonicGraphColored(queue)) {
            if(!isHarmonicGraphColored(queue) || flag) {
                if(!queue.getFirst().isDomainEmpty()) {
                    queue.getFirst().setColor(queue.getFirst().getFromDomain());
                    unassigned.forEach(Vertex::updateDomain);
                    flag = false;
                }
                else {
                    Vertex tmp = queue.poll();
                    //    tmp.update();
                    unassigned.add(0,tmp);
                    flag = true;
                    unassigned.forEach(Vertex::updateDomain);
                }
            }
            else {
                queue.addFirst(unassigned.remove(0));
            }

        }
    }


    /**
     * Minimum remaining values (MRV) heuristics of variable ordering
     */
    public void forwardCheckingAlgorithmWithHeuristic(Comparator<? super Vertex> comparator) {

        List<Vertex> unassigned = new LinkedList<>();

        unassigned.addAll(Arrays.asList(vertices));

        Deque<Vertex> queue = new ArrayDeque<>();
        unassigned.sort(comparator);
        queue.addFirst(unassigned.remove(0));

        boolean flag = false;
        while(!unassigned.isEmpty() || !isHarmonicGraphColored(queue)) {
            if(!isHarmonicGraphColored(queue) || flag) {
                if(!queue.getFirst().isDomainEmpty()) {
                    queue.getFirst().setColor(queue.getFirst().getFromDomain());
                    unassigned.forEach(Vertex::updateDomain);
                    flag = false;
                }
                else {
                    Vertex tmp = queue.poll();
                    //    tmp.update();
                    unassigned.add(0, tmp);
                    flag = true;
                    unassigned.forEach(Vertex::updateDomain);
                }
            }
            else {
                unassigned.sort(comparator);
                queue.addFirst(unassigned.remove(0));
            }

        }
    }

    public Comparator<Vertex> MinimumRemainingValues() {
        return (o1, o2) -> {
                    if(o1.getDomain().size() == o2.getDomain().size())
                        return 0;
                    else if(o1.getDomainSize() < o2.getDomainSize()){
                        return 1;
                    }
                    else
                        return -1;
                };
    }

    public Comparator<Vertex> MostConstrainingVariable() {
        return (o1, o2) -> {
            if(countConstraint(o1) == countConstraint(o2))
                return 0;
            else if(countConstraint(o1) < countConstraint(o2)){
                return 1;
            }
            else
                return -1;
        };
    }

    public int countConstraint(Vertex vertex) {
        int counter = 0;
        for (Vertex vertice : vertices) {
            if (vertice.getConstraints().contains(vertex))
                counter++;
        }
        return counter;
    }

    private Vertex getNextConstraint(Vertex vertex, List<? extends Vertex> unassigned) {
        List<Vertex> list = vertex.getConstraints();
        for(Vertex tmp : list) {
            if(unassigned.contains(tmp))
                return tmp;
        }

        return null;
    }

}

package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tomek on 2017-03-28.
 */
public class Vertex {
    private static int counter = 0;
    private int id;
    private int x;
    private int y;
    private int color;
    private List<Integer> primaryDomain;
    private List<Integer> domain;
    private List<Vertex> constraints;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        domain = new ArrayList<>();
        primaryDomain = new ArrayList<>();
        constraints = new ArrayList<>();
        id = counter++;
    }

    public boolean isCorrect() {
        for(Vertex tmp : constraints) {
            if(tmp.getColor() == color)
                return false;
        }
        return true;
    }

    public void updateDomain() {
       domain = primaryDomain.stream().filter(integer -> !constraints.contains(integer)).collect(Collectors.toList());
    }

    public Integer getFromDomain() {
        if(domain.isEmpty())
            return null;
        else
            return domain.remove(0);
    }
/*
    public Integer getLeastConstrainingValue() {

    }*/

    public boolean isDomainEmpty() {
        return domain.isEmpty();
    }

    public int getDomainSize() {
        return domain.size();
    }

    public void update() {
        domain.addAll(primaryDomain);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<Integer> getDomain() {
        return domain;
    }

    public void setDomain(List<Integer> domain) {
        this.domain = domain;
    }

    public List<Vertex> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Vertex> constraints) {
        this.constraints = constraints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getPrimaryDomain() {
        return primaryDomain;
    }

    public void setPrimaryDomain(List<Integer> primaryDomain) {
        this.primaryDomain = primaryDomain;
    }

    @Override
    public String toString() {
        return String.valueOf(id) + "  " + String.valueOf(color);
    }
}

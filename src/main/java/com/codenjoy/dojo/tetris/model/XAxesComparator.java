package com.codenjoy.dojo.tetris.model;

import com.codenjoy.dojo.services.Point;

import java.util.Comparator;

public class XAxesComparator implements Comparator<Point> {
    @Override
    public int compare(Point o1, Point o2) {
        return o1.getX() - o2.getX();
    }
}

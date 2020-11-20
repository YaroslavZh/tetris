package com.codenjoy.dojo.tetris.model;

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.tetris.client.Board;
import com.codenjoy.dojo.tetris.client.GlassBoard;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlainPointFinder {
    private ElementsFactory factory;

    public PlainPointFinder() {
        factory = new ElementsFactory();
    }

    public Point getPoint(Board board) {
        GlassBoard glass = board.getGlass();
        List<Point> freeSpace = glass.getFreeSpace();
        List<Point> figures = glass.getFigures();
        Elements currentFigureType = board.getCurrentFigureType();

        freeSpace.sort(new YAxesComparator());

        AbstractElement currentFigure = factory.createElement(currentFigureType);
        int[] stickingPoints = currentFigure.getStickingPoints();
        Arrays.sort(stickingPoints);

        Point point = new PointImpl(0, 0);
        Optional<Point> min = freeSpace.stream().min(new YAxesComparator());
        if (min.isPresent()) {
            int minY = min.get().getY();
            List<Point> lowLevelPoints = getLevelPoints(freeSpace, minY);

            int size = lowLevelPoints.size();
            int level = minY + 1;

            while (isNotFit(stickingPoints, size) && level < glass.size()) {
                lowLevelPoints = getLevelPoints(freeSpace, level);
                level++;
                size = lowLevelPoints.size();
            }
            lowLevelPoints.sort(new XAxesComparator());
            point = lowLevelPoints.get(0);
        }
        return point;
    }

    private boolean isNotFit(int[] stickingPoints, int size) {
        return stickingPoints[0] > size;
    }

    private List<Point> getLevelPoints(List<Point> freeSpace, int minY) {
        return freeSpace.stream().filter(p -> p.getY() == minY).collect(Collectors.toList());
    }

}

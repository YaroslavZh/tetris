package com.codenjoy.dojo.tetris.model;

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.tetris.client.Board;
import com.codenjoy.dojo.tetris.client.GlassBoard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class NewPointFinder {


    private final ElementsFactory factory;

    public NewPointFinder() {
        factory = new ElementsFactory();
    }

    public Point getPoint(Board board) {
        GlassBoard glass = board.getGlass();
        List<Point> freeSpace = glass.getFreeSpace();
        Elements currentFigureType = board.getCurrentFigureType();

        freeSpace.sort(new YAxesComparator());

        AbstractElement currentFigure = factory.createElement(currentFigureType);
        int[] stickingPoints = currentFigure.getStickingPoints();
        Arrays.sort(stickingPoints);

        Point point = new PointImpl(0, 0);
        Optional<Point> min = freeSpace.stream().min(new YAxesComparator());
        if (min.isPresent()) {
            int minY = min.get().getY();
            List<Point> lowLevelPoints = getLowPoints(freeSpace, minY);
            int level = minY + 1;

            Map<Point, Integer> map = countPointAndFreeSpace(lowLevelPoints);
            int figureSize = currentFigure.getStickingPoints()[0];
            do {
                point.setY(level);
                while (!map.containsValue(figureSize - 1) && level < glass.size()) {
                    map = updatePoints(freeSpace, level);
                    level++;
                }

//                Set<Point> points = getKeys(map, figureSize);
//
//                for (Point point1 : points) {
//                    if (isValidPoint(glass, point1)) {
//                        point = point1;
//                        break;
//                    } else {
//                        point.setX(-1);
//                    }
//                }

                point = getBestPoint(map, figureSize);
                map = updatePoints(freeSpace, level);
                level++;
//            } while (point.getX() == -1);
            } while (!isValidPoint(glass, point));
        }
        return point;
    }

    private Map<Point, Integer> updatePoints(List<Point> freeSpace, int level) {
        Map<Point, Integer> map;
        List<Point> lowLevelPoints;
        lowLevelPoints = getLowPoints(freeSpace, level);
        map = countPointAndFreeSpace(lowLevelPoints);
        return map;
    }

    private Point getBestPoint(Map<Point, Integer> map, int size) {
      Point point = null;
        for (Map.Entry<Point, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(size - 1)) {
                point = entry.getKey();
            }
        }
        return point;
    }

//    private Point getBestPoint(Map<Point, Integer> map, int size) {
//        Set<Point> keys = getKeys(map, size);
//        return point;
//    }

    public <K, V> Set<K> getKeys(Map<K, V> map, V value) {
        Set<K> keys = new HashSet<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    private boolean isValidPoint(GlassBoard glass, Point point) {
        boolean result = true;
        int x = point.getX();
        for (int i = point.getY(); i < glass.size(); i++) {
            if (!glass.isFree(x, i)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private boolean isValidPoint(GlassBoard glass, Point point, AbstractElement element) {
        boolean result = true;
        int x = point.getX();
        for (int i = point.getY(); i < glass.size(); i++) {
            if (!glass.isFree(x, i)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private Map<Point, Integer> countPointAndFreeSpace(List<Point> levelPoints) {
        Map<Point, Integer> map = new HashMap<>();
        List<Integer> xArgs = levelPoints.stream().map(point -> point.getX()).collect(Collectors.toList());
        for (Integer xArg : xArgs) {
            int count = 0;
            for (int i = xArgs.indexOf(xArg); i < xArgs.size() - 1; i++) {
                if ((xArgs.get(i + 1) - xArgs.get(i)) == 1) {
                    count++;
                } else {
                    break;
                }
            }
            map.put(levelPoints.get(xArgs.indexOf(xArg)), count);
        }
        return map;
    }

    private List<Point> getLowPoints(List<Point> freeSpace, int minY) {
        return freeSpace.stream().filter(p -> p.getY() == minY).collect(Collectors.toList());
    }
}

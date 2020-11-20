package com.codenjoy.dojo.tetris.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.AbstractJsonSolver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.tetris.model.AbstractElement;
import com.codenjoy.dojo.tetris.model.Elements;
import com.codenjoy.dojo.tetris.model.ElementsFactory;
import com.codenjoy.dojo.tetris.model.NewPointFinder;
import com.codenjoy.dojo.tetris.model.PlainPointFinder;
import com.codenjoy.dojo.tetris.model.XAxesComparator;
import com.codenjoy.dojo.tetris.model.YAxesComparator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * User: your name
 * Это твой алгоритм AI для игры. Реализуй его на свое усмотрение.
 * Обрати внимание на {@see YourSolverTest} - там приготовлен тестовый
 * фреймворк для тебя.
 */
public class YourSolver extends AbstractJsonSolver<Board> {

    private Dice dice;
    private ElementsFactory factory;

    public YourSolver(Dice dice) {
        factory = new ElementsFactory();
        this.dice = dice;
    }

    @Override
    public String getAnswer(Board board) {
        StringBuilder result = new StringBuilder();

//        NewPointFinder pointFinder = new NewPointFinder();
        PlainPointFinder pointFinder = new PlainPointFinder();

        Point point = pointFinder.getPoint(board);
//
//        if (board.getCurrentFigureType().equals(Elements.CYAN)) {
//            result.append("ACT");
//            point.setX(point.getX() + 1);
//        }

        int difference = point.getX() - board.getCurrentFigurePoint().getX();


        if (difference > 0) {
            for (int i = 0; i < difference; i++) {
                result.append("RIGHT");
            }
        } else {
            for (int i = 0; i < Math.abs(difference); i++) {
                result.append("LEFT");
            }
        }


        result.append("DOWN");
        return result.toString();
    }


    public static void main(String[] args) {
        Board board = new Board();
        WebSocketRunner.runClient(
                // paste here board page url from browser after registration
                new UrlWrapper().removeTrailingParameters("http://codebattle2020.westeurope.cloudapp.azure.com/codenjoy-contest/board/player/j692lc3966db7l21fvff?code=6408746668313128402&gameName=tetris"),
                new YourSolver(new RandomDice()),
                board);
    }

}

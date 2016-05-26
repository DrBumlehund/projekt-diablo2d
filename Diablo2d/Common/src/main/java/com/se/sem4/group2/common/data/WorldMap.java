/* 
 * Copyright (C) 2016 Casper Beese Nielsen, Jakob Tøttrup, Jesper Bager Rasmussen, Oliver Vestergaard, Simon Hjortshøj Larsen & Thomas August Lemqvist
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.se.sem4.group2.common.data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ThomasLemqvist
 */
public class WorldMap {

    public static final double FEATURE_SIZE = 12;

    public ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Tile>> getWorldMap() {
        return worldMap;
    }

    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Tile>> worldMap;

    //private final Random random;
    public static long DEFAULT_SEED = 0;
    public final static int DEFAULT_TILE_SIZE = 64;
    public static final int DEFAULT_BUFFERZONE = 3;
    public static final int TILE_SIZE = 64;

    public static final float MIN_DIRT = 0.4f;
    public static final float MAX_WATER = -0.4f;

    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;

    public WorldMap(int width, int height) {
        this.worldMap = new ConcurrentHashMap<>();
        //random = new Random(seed);

        this.xMin = -DEFAULT_BUFFERZONE;
        this.yMin = -DEFAULT_BUFFERZONE;
        this.xMax = width / DEFAULT_TILE_SIZE + DEFAULT_BUFFERZONE;
        this.yMax = height / DEFAULT_TILE_SIZE + DEFAULT_BUFFERZONE;
    }

    public Tile getTile(int x, int y) {
        if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
            ConcurrentHashMap<Integer, Tile> yAxis = worldMap.get(x);
            return yAxis.get(y);
        }
        return null;
    }

    public Tile getTileByPosition(int x, int y) {
        x = x / TILE_SIZE;
        y = y / TILE_SIZE;

        return getTile(x, y);
    }

    public ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Tile>> getMap() {
        return worldMap;
    }

    public int getxMin() {
        return xMin;
    }

    public int getyMin() {
        return yMin;
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMax() {
        return yMax;
    }

    public void setxMin(int xMin) {
        this.xMin = xMin;
    }

    public void setyMin(int yMin) {
        this.yMin = yMin;
    }

    public void setxMax(int xMax) {
        this.xMax = xMax;
    }

    public void setyMax(int yMax) {
        this.yMax = yMax;
    }

    public void clearMap() {
        for(Map yCord: worldMap.values()){
            yCord.clear();
        }
        worldMap.clear();
    }

}

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
import java.util.Random;

/**
 *
 * @author ThomasLemqvist
 */
public class WorldMap {

    public static final double FEATURE_SIZE = 12;
    //indeholder chuncks

    private Tile[] tileTypes = new Tile[]{
        new Tile("com/se/sem4/group2/map/dirt.png", 1f),
        new Tile("com/se/sem4/group2/map/water.png", 1f),
        new Tile("com/se/sem4/group2/map/grass.png", 1.4f),
        new Tile("com/se/sem4/group2/map/grass1.png", 1.4f),
        new Tile("com/se/sem4/group2/map/grass2.png", 1.4f)
    };
    //public Tile[][] worldMap;

    public HashMap<Integer, HashMap<Integer, Tile>> getWorldMap() {
        return worldMap;
    }

    public Tile[] getTileTypes() {
        return tileTypes;
    }

    public OpenSimplexNoise getNoise() {
        return noise;
    }

    HashMap<Integer, HashMap<Integer, Tile>> worldMap = new HashMap<>();

    //private final Random random;
    public static long DEFAULT_SEED = 0;
    public final static int DEFAULT_SIZE = 64;
    public static final int DEFAULT_BUFFERZONE = 3;
    public static final int DEFAULT_DISPLAY_HEIGHT = 480;
    public static final int DEFAULT_DISPLAY_WIDTH = 640;

    public static final float MIN_DIRT = 0.4f;
    public static final float MAX_WATER = -0.4f;

    private int xMin = -DEFAULT_BUFFERZONE;
    private int yMin = -DEFAULT_BUFFERZONE;
    private int xMax;
    private int yMax;

    OpenSimplexNoise noise;

    public WorldMap() {
        //random = new Random();
        this(DEFAULT_SEED);
    }

    public WorldMap(long seed) {
        this(seed, DEFAULT_DISPLAY_WIDTH, DEFAULT_DISPLAY_HEIGHT);
    }

    public WorldMap(long seed, int width, int height) {
        //random = new Random(seed);
        this.DEFAULT_SEED = seed;
        noise = new OpenSimplexNoise(DEFAULT_SEED);
        this.xMax = width / DEFAULT_SIZE + DEFAULT_BUFFERZONE;
        this.yMax = height / DEFAULT_SIZE + DEFAULT_BUFFERZONE;
    }

    public void generateMap() {
        System.out.println("GENERATING");
        for (int x = xMin; x < xMax + 1; x++) {
            for (int y = yMin; y < yMax + 1; y++) {
                addTile(x, y);
            }
        }
    }

    Random r = new Random();

    public Tile generateSingleTile(int x, int y) {
        Tile noiseTile;
        double value = noise.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
        if (value >= MIN_DIRT) { // DIRT
            noiseTile = tileTypes[0];
        } else if (value <= MAX_WATER) { // WATER
            noiseTile = tileTypes[1];
        } else { // GRASS
            int rnd = r.nextInt(3);
            if (rnd == 0) {
                noiseTile = tileTypes[2];
            } else if (rnd == 1) {
                noiseTile = tileTypes[3];
            } else {
                noiseTile = tileTypes[4];
            }
        }
        //System.out.println(noiseTile.getSource() + " x: " + x + ", y: " + y);
        return noiseTile;
    }

    public Tile getTile(int x, int y) {
        if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
            HashMap<Integer, Tile> tmp = worldMap.get(x);
            return tmp.get(y);
        }
        return null;
    }

    public void addTile(int x, int y) {
        if (x < xMin) {
            xMin = x;
            worldMap.remove(xMax);
            xMax--;
        } else if (x > xMax) {
            xMax = x;
            worldMap.remove(xMin);
            xMin++;
        }
        if (y < yMin) {
            yMin = y;
            for (int i : worldMap.keySet()) {
                worldMap.get(i).remove(yMax);
            }
            yMax--;
        } else if (y > yMax) {
            yMax = y;
            for (int i : worldMap.keySet()) {
                worldMap.get(i).remove(yMin);
            }
            yMin++;
        }
        Tile tile = generateSingleTile(x, y);
        if (worldMap.get(x) != null) {
            worldMap.get(x).put(y, tile);
        } else {
            worldMap.put(x, new HashMap<Integer, Tile>());
            worldMap.get(x).put(y, tile);
        }
    }

    public HashMap<Integer, HashMap<Integer, Tile>> getMap() {
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

}

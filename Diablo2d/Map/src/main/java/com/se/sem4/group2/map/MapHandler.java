/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.map;

import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.OpenSimplexNoise;
import com.se.sem4.group2.common.data.Tile;
import com.se.sem4.group2.common.data.WorldMap;
import java.io.File;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author thomaslemqvist
 */
public class MapHandler {

    MetaData metaData;
    private WorldMap worldMap;
    Random rng;
    OpenSimplexNoise noise;
    String path;
    private Tile[] tileTypes;

    private MapHandler() {
        rng = new Random();
        setNoiseSeed(rng.nextLong());

        if (System.getProperty("os.name").startsWith("Windows")) {
            path = (new File("").getAbsolutePath() + "/diablo2d/modules/com-se-sem4-group2-Map.jar!/assets/images/");
            path = path.substring(2);
            path = path.replaceAll("\\\\", "/");
        } else {
            path = (new File("").getAbsolutePath() + "/target/diablo2d/diablo2d/modules/com-se-sem4-group2-Map.jar!/assets/images/");
        }
        tileTypes = new Tile[]{
            new Tile(path + "dirt.png", 1f),
            new Tile(path + "water.png", 0.4f),
            new Tile(path + "grass.png", 1.4f),
            new Tile(path + "grass1.png", 1.4f),
            new Tile(path + "grass2.png", 1.4f)
        };
    }

    private void setNoiseSeed(long seed) {
        noise = new OpenSimplexNoise(seed);
    }

    public static MapHandler getInstance() {
        return MapHandlerHolder.INSTANCE;
    }

    private static class MapHandlerHolder {

        private static final MapHandler INSTANCE = new MapHandler();
    }

    protected void initialize(MetaData metaData) {
        this.metaData = metaData;
        this.worldMap = metaData.getWorldMap();
    }

    protected WorldMap createMap() {
        System.out.println("New WorldMap");
        worldMap = new WorldMap(metaData.getDisplayWidth(), metaData.getDisplayHeight());
        generateMap();
        return worldMap;
    }

    protected void generateMap() {
        System.out.println("GENERATING WORLDMAP");
        for (int x = worldMap.getxMin(); x < worldMap.getxMax() + 1; x++) {
            for (int y = worldMap.getyMin(); y < worldMap.getyMax() + 1; y++) {
                addTile(x, y);
            }
        }
    }

    protected void addTile(int x, int y) {
        int xMin = worldMap.getxMin();
        int xMax = worldMap.getxMax();
        int yMax = worldMap.getyMax();
        int yMin = worldMap.getyMin();
        if (x < xMin) {
            worldMap.setxMin(x);
            worldMap.getWorldMap().get(xMax).clear();
            worldMap.getWorldMap().remove(xMax);
            worldMap.setxMax(xMax - 1);
        } else if (x > xMax) {
            worldMap.setxMax(x);
            worldMap.getWorldMap().get(xMin).clear();
            worldMap.getWorldMap().remove(xMin);
            worldMap.setxMin(xMin + 1);
        }
        if (y < yMin) {
            worldMap.setyMin(y);
            for (int i : worldMap.getWorldMap().keySet()) {
                worldMap.getWorldMap().get(i).remove(yMax);
            }
            worldMap.setyMax(yMax - 1);
        } else if (y > yMax) {
            worldMap.setyMax(y);
            for (int i : worldMap.getWorldMap().keySet()) {
                worldMap.getWorldMap().get(i).remove(yMin);
            }
            worldMap.setyMin(yMin + 1);
        }
        Tile tile = generateSingleTile(x, y);
        if (worldMap.getWorldMap().get(x) != null) {
            worldMap.getWorldMap().get(x).put(y, tile);
        } else {
            worldMap.getWorldMap().put(x, new ConcurrentHashMap<>());
            worldMap.getWorldMap().get(x).put(y, tile);
        }
    }

    protected Tile generateSingleTile(int x, int y) {
        Tile noiseTile;
        double value = noise.eval(x / WorldMap.FEATURE_SIZE, y / WorldMap.FEATURE_SIZE, 0.0);
        if (value >= WorldMap.MIN_DIRT) { // DIRT
            noiseTile = tileTypes[0];
        } else if (value <= WorldMap.MAX_WATER) { // WATER
            noiseTile = tileTypes[1];
        } else { // RANDOM TYPE OF GRASS
            int rnd = rng.nextInt(3);
            switch (rnd) {
                case 0:
                    noiseTile = tileTypes[2];
                    break;
                case 1:
                    noiseTile = tileTypes[3];
                    break;
                default:
                    noiseTile = tileTypes[4];
                    break;
            }
        }
//        System.out.println(noiseTile.getSource() + " x: " + x + ", y: " + y);
        return noiseTile;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.util.Random;

/**
 *
 * @author ThomasLemqvist
 */
public class WorldMap {
    //indeholder chuncks
    
    private Tile[] tileTypes = new Tile[] {
        new Tile("dirt.png", 1f),
        new Tile("grass.jpg", .4f)
    };
    public Tile[][] worldMap;
    private final Random random;
    
    public WorldMap () {
        random = new Random();
    }
    
    public WorldMap(long seed) {
        random = new Random(seed);
    }

    public void generateMap(int size) {
        worldMap = new Tile[size][size];
        for (int i=0; i<worldMap.length; i++) {
            Tile[] tiles = worldMap[i];
            for (int n=0; n<worldMap.length; n++) {
                worldMap[i][n] = tileTypes[random.nextInt(tileTypes.length)];
            }
        }
    }
    
    public Tile[][] getMap() {
        return worldMap;
    }
}

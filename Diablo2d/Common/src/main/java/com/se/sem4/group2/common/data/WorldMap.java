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

import java.util.Random;

/**
 *
 * @author ThomasLemqvist
 */
public class WorldMap {
    //indeholder chuncks
    
    private Tile[] tileTypes = new Tile[] {
        new Tile("com/se/sem4/group2/map/dirt.png", 1f),
        new Tile("com/se/sem4/group2/map/grass.jpg", .4f)
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

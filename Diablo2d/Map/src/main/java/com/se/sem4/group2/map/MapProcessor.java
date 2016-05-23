/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.map;

import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.services.IMapProcessingService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lhrbo
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IMapProcessingService.class)
public class MapProcessor implements IMapProcessingService {

    float lastX = 0;
    float lastY = 0;
    boolean first = true;
    MapHandler instance = MapHandler.getInstance();

    @Override
    public void process(float x, float y, WorldMap worldMap) {
        if (first) {
            lastX = x;
            lastY = y;
            first = false;
        }

        int defaultTileSize = WorldMap.DEFAULT_TILE_SIZE;
        float dist = calculateDistance(x, y);
        if (dist > defaultTileSize) {
            //System.out.println("Processing");
            int xMax = worldMap.getxMax();
            int xMin = worldMap.getxMin();
            int yMax = worldMap.getyMax();
            int yMin = worldMap.getyMin();
            if (x > lastX + defaultTileSize) {
                int tmpX = xMax + 1; //refactor
                for (int tmpY = yMin; tmpY < yMax + 1; tmpY++) {
                    instance.addTile(tmpX, tmpY);
                }
                lastX += defaultTileSize;
            } else if (x < lastX - defaultTileSize) {
                int tmpX = xMin - 1; //refactor
                for (int tmpY = yMin; tmpY < yMax + 1; tmpY++) {
                    instance.addTile(tmpX, tmpY);
                }
                lastX -= defaultTileSize;
            }
            if (y > lastY + defaultTileSize) {
                int tmpY = yMax + 1;
                for (int tmpX : worldMap.getMap().keySet()) {
                    instance.addTile(tmpX, tmpY);
                }
                lastY += defaultTileSize;
            } else if (y < lastY - defaultTileSize) {
                int tmpY = yMin - 1;
                for (int tmpX : worldMap.getMap().keySet()) {
                    instance.addTile(tmpX, tmpY);
                }
                lastY -= defaultTileSize;
            }
        }
    }

    private float calculateDistance(float x, float y) {
        return (float) Math.sqrt(Math.pow((x - lastX), 2) + Math.pow((y - lastY), 2));
    }
}

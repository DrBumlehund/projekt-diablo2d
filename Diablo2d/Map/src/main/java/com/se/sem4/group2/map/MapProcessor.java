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

    @Override
    public void process(float x, float y, WorldMap worldMap) {
        if (first) {
            lastX = x;
            lastY = y;
            first = false;
        }

        int def = worldMap.DEFAULT_SIZE;
        float dist = calculateDistance(x, y);
        if (dist > def) {
            System.out.println("Processing");
            int xMax = worldMap.getxMax();
            int xMin = worldMap.getxMin();
            int yMax = worldMap.getyMax();
            int yMin = worldMap.getyMin();
            if (x > lastX + def) {
                int tmpX = xMax + 1; //refactor
                for (int tmpY = yMin; tmpY < yMax + 1; tmpY++) {
                    worldMap.addTile(tmpX, tmpY);
                }
                lastX += def;
            } else if (x < lastX - def) {
                int tmpX = xMin - 1; //refactor
                for (int tmpY = yMin; tmpY < yMax + 1; tmpY++) {
                    worldMap.addTile(tmpX, tmpY);
                }
                lastX -= def;
            }
            if (y > lastY + def) {
                int tmpY = yMax + 1;
                for (int tmpX : worldMap.getMap().keySet()) {
                    worldMap.addTile(tmpX, tmpY);
                }
                lastY += def;
            } else if (y < lastY - def) {
                int tmpY = yMin - 1;
                for (int tmpX : worldMap.getMap().keySet()) {
                    worldMap.addTile(tmpX, tmpY);
                }
                lastY -= def;
            }
        }
    }

    private float calculateDistance(float x, float y) {
        return (float) Math.sqrt(Math.pow((x - lastX), 2) + Math.pow((y - lastY), 2));
    }
}

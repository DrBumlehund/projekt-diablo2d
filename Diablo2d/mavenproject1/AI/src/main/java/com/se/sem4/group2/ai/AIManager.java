/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.ai;

import com.se.sem4.group2.common.data.Entity;
import static com.se.sem4.group2.common.data.EntityType.NPC;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.Tile;
import com.se.sem4.group2.common.data.WorldMap;
import java.awt.Point;
import java.util.List;
import java.util.Map;

/**
 *
 * @author simon
 */
public class AIManager implements Runnable {

    private static AIManager instance;
    
    private DStarLite pf;
    private WorldMap worldMap;
    private Point boundsX = new Point(0,0);
    private Point boundsY = new Point(0,0);
    private static final int TILE_SIZE = 64;
    // Pathfinding will apply to a DIVISOR precison of TILE_SIZE
    private static final int DIVISOR = 8; 
    private Map<String, Entity> world;

        
    public static AIManager getInstance() {
        if (instance == null) {
            instance = new AIManager();
        }
        return instance;
    }
    
    private AIManager () {
        pf = new DStarLite();
    }

    public Map<String, Entity> getWorld() {
        return world;
    }

    public void setWorld(Map<String, Entity> world) {
        this.world = world;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }
    
    public void process() {        
        Point goal = null;
        for (Entity player : world.values()) {
            if (player.getType() == PLAYER) {
                Point playerPos = new Point((int) player.getX(), (int) player.getY());
                goal = playerPos;
            }
        }

        updateCellHash(worldMap);
        
        for (Entity npc : world.values()) {
            if (npc.getType() == NPC) {
                if (goal == null) {
                    return;
                }
                pf.updateStart((int) npc.getX()/DIVISOR, (int) npc.getY()/DIVISOR);
                pf.updateGoal((int) goal.x/DIVISOR, (int) goal.y/DIVISOR);
//                pf.init((int) npc.getX(), (int) npc.getX(), (int) goal.x, (int) goal.y);

                pf.replan();
                final List<Point> pathPoints = pf.getPathPoint();
                if (!pathPoints.isEmpty())
                    pathPoints.remove(0);
                for (Point point : pathPoints) {
                    point.x *= DIVISOR;
                    point.y *= DIVISOR;
                }
                npc.setPath(pathPoints);
            }
        }
    }

    private void updateCellHash(WorldMap worldMap) {
        for (int x=worldMap.getxMin(); x<worldMap.getxMax(); x++) {
            if (x >= boundsX.x && x < boundsX.y)
                continue; // We already loaded this data
            for (int y=worldMap.getyMin(); y<worldMap.getyMax(); y++) {
                if (y >= boundsY.x && y < boundsY.y)
                    continue; // We already loaded this data
                Tile tile = worldMap.getTile(x, y);
                if (tile == null) {
                    System.out.println("Didn't find a tile when updating cell hash!");
                    continue;
                }

                final int scale = TILE_SIZE/DIVISOR;
                final float speedModifier = tile.getSpeedModifier();
                for (int innerX=0; innerX<scale; innerX++) {
                    for (int innerY=0; innerY<scale; innerY++) {
                        pf.updateCell(x * scale + innerX, y * scale + innerY, speedModifier);
                    }
                }
                //System.out.println("Update cell " + x + ", " + y + " with modifier: " + speedModifier);
            }
        }

        boundsX = new Point(Math.min(boundsX.x, worldMap.getxMin()), Math.max(boundsX.y, worldMap.getxMax()));
        boundsY = new Point(Math.min(boundsY.x, worldMap.getyMin()), Math.max(boundsY.y, worldMap.getyMax()));
    }

    @Override
    public void run() {
        process();
    }
}

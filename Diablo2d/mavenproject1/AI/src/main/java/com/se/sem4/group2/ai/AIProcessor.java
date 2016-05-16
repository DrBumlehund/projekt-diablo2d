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
import com.se.sem4.group2.common.services.IAIProcessingService;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lhrbo
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IAIProcessingService.class)
public class AIProcessor implements IAIProcessingService {

    private DStarLite pf = new DStarLite();
    private Point goal;
    private Point boundsX = new Point(0,0);
    private Point boundsY = new Point(0,0);
    private int divisor = 64;

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, WorldMap worldMap) {        
        for (Entity player : world.values()) {
            if (player.getType() == PLAYER) {
                Point playerPos = new Point((int) player.getX(), (int) player.getY());
                if (goal != playerPos) {
                    goal = playerPos;
                }
            }
        }

        updateCellHash(worldMap);
        
        for (Entity npc : world.values()) {
            if (npc.getType() == NPC) {
                // TODO: divide by map divisor
//                pf.updateStart((int) npc.getX(), (int) npc.getY());
//                pf.updateGoal((int) goal.x, (int) goal.y);
//                pf.init((int) npc.getX(), (int) npc.getX(), (int) goal.x, (int) goal.y);

                //pf.replan();
                final List<Point> pathPoint = pf.getPathPoint();
                pathPoint.clear();
                pathPoint.add(new Point(goal.x/64, goal.y/64));
                npc.setPath(pathPoint);
            }
        }
    }

    private void updateCellHash(WorldMap worldMap) {
        for (int x=worldMap.getxMin(); x<worldMap.getxMax(); x++) {
            if (x >= boundsX.x && x <= boundsX.y)
                continue; // We already loaded this data
            for (int y=worldMap.getyMin(); y<worldMap.getyMax(); y++) {
                if (y >= boundsY.x && y <= boundsY.y)
                    continue; // We already loaded this data
                Tile tile = worldMap.getTile(x, y);
                if (tile == null) {
                    System.out.println("Didn't find a tile when updating cell hash!");
                    continue;
                }
                
                final float speedModifier = tile.getSpeedModifier();
                pf.updateCell(x,y, speedModifier);
//                for (int i=0; i<64/divisor; i++) {
//                    final float speedModifier = tile.getSpeedModifier();
//                    pf.updateCell(x + i, y + i, speedModifier);
//                }
            }
        }
        
        boundsX = new Point(Math.min(boundsX.x, worldMap.getxMin()), Math.max(boundsX.y, worldMap.getxMax()));
        boundsY = new Point(Math.min(boundsY.x, worldMap.getyMin()), Math.max(boundsY.y, worldMap.getyMax()));
    }
    
    
}

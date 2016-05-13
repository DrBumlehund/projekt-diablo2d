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

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, WorldMap worldMap) {
        for (int x=worldMap.getxMin(); x<worldMap.getxMax(); x++) {
            if (x >= boundsX.x && x <= boundsX.y)
                continue; // We already loaded this data
            for (int y=worldMap.getxMin(); y<worldMap.getxMax(); y++) {
                if (y >= boundsY.x && y <= boundsY.y)
                    continue; // We already loaded this data
                Tile tile = worldMap.getTile(x, y);
                if (tile == null)
                    continue;
                for (int i=1; i<64; i++) {
                    pf.updateCell(x*64 + i, y*64 + i, tile.getSpeedModifier());
                }
            }
        }
        
        boundsX = new Point(Math.min(boundsX.x, worldMap.getxMin()), Math.max(boundsX.y, worldMap.getxMax()));
        boundsY = new Point(Math.min(boundsY.x, worldMap.getyMin()), Math.max(boundsY.y, worldMap.getyMax()));
        
        for (Entity player : world.values()) {
            if (player.getType() == PLAYER) {
                Point playerPos = new Point((int) player.getX(), (int) player.getY());
                if (goal != playerPos) {
                    goal = playerPos;
                }
            }
        }

        for (Entity npc : world.values()) {
            if (npc.getType() == NPC) {
                pf.updateStart((int) npc.getX(), (int) npc.getY());
                pf.updateGoal((int) goal.x, (int) goal.y);
                pf.replan();
                npc.setPath(pf.getPathPoint());
            }
        }

//                List<Point> path = entity.getPath();
        //path.clear();
//                Point v = new Point((int)player.getX(), (int)player.getY());
//                Point e = new Point((int)entity.getX(), (int)entity.getY());
//                
//                Point direction = new Point(v.x - e.x, v.y - e.y);
//                double length = Math.sqrt((double)(direction.x * direction.x) + (double)(direction.y * direction.y));
//                direction.x = (int)(direction.x / length);
//                direction.y = (int)(direction.y / length);
//                
//                int stoppingDistance = 5;
//                Point r = new Point(v.x - (stoppingDistance * direction.x), v.y - (stoppingDistance * direction.y));
//                path.add(v);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.ai;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.services.IGamePluginService;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lhrbo
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class AIPlugin implements IGamePluginService {

    Thread pathfindingThread = null;
    private MetaData metaData;
    private Map<String, Entity> world;
    private WorldMap worldMap;
    // Delay in miliseconds between each pathfinding cycle
    private static final int PATHFINDING_INTERVAL = 200; 
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledPathfinding;
    
    @Override
    public void start(MetaData metaData, Map<String, Entity> world) {
        // TODO: start a new thread and slowly process the entities in "world".
        // TODO: add worldmap as a parameter on this start method
        this.metaData = metaData;
        this.worldMap = metaData.getWorldMap();
        this.world = world;
        
        AIManager aiManager = AIManager.getInstance();
        aiManager.setWorld(world);
        aiManager.setWorldMap(worldMap);
        scheduledPathfinding = scheduler.scheduleAtFixedRate(aiManager, 0, PATHFINDING_INTERVAL, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop(MetaData metaData) {
        if (scheduledPathfinding != null) {
            scheduledPathfinding.cancel(true);
        }
    }
}

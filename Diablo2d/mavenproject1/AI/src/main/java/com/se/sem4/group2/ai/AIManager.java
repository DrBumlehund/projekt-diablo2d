/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.ai;

import com.se.sem4.group2.common.data.WorldMap;

/**
 *
 * @author simon
 */
public class AIManager {

    private static AIManager instance;
    
    private DStarLite pf;
    private WorldMap worldMap;

        
    public static AIManager getInstance() {
        if (instance == null) {
            instance = new AIManager();
        }
        return instance;
    }
    
    private AIManager () {
        pf = new DStarLite();
    }
    
    public void updatePathfinding () {
        pf.updateCell(0, 0, 0);
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }
}

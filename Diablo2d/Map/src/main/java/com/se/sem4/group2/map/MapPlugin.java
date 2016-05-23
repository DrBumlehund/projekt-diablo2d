/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.map;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.services.IGamePluginService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Simon
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class MapPlugin implements IGamePluginService {

    private final MapHandler instance = MapHandler.getInstance();
//
//    @Override
//    public WorldMap start(MetaData metaData) {
//        instance = MapHandler.getInstance();
//        instance.initialize(metaData);
//        instance.setWorldMap(worldMap);
//        worldMap = instance.createMap();
//        return worldMap;
//    }

    @Override
    public void stop(MetaData metaData) {
        metaData.getWorldMap().clearMap();
    }

    @Override
    public void start(MetaData metaData, Map<String, Entity> world) {
        instance.initialize(metaData);
        metaData.setWorldMap(instance.createMap());
    }
}

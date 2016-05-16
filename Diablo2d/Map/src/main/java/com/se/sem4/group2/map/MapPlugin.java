/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.map;

import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.services.IMapPluginService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Simon
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IMapPluginService.class)
public class MapPlugin implements IMapPluginService {

    private WorldMap worldMap;
    private MapHandler instance;

    @Override
    public WorldMap start(MetaData metaData) {
        instance = MapHandler.getInstance();
        instance.setMetaData(metaData);
        instance.setWorldMap(worldMap);
        worldMap = instance.createMap();
        return worldMap;
    }

    @Override
    public void stop(MetaData metaData) {
        instance.unloadMap();
    }
}

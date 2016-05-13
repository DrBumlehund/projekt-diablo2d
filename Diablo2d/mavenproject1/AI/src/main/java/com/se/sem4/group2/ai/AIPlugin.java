/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.ai;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IGamePluginService;
import com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lhrbo
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class AIPlugin implements IGamePluginService {

    @Override
    public void start(MetaData metaData, Map<String, Entity> world, IAssetTextureService assetManager) {
        // TODO: start a new thread and slowly process the entities in "world".
        // TODO: add worldmap as a parameter on this start method
    }

    @Override
    public void stop(MetaData metaData) {
    }
    
}

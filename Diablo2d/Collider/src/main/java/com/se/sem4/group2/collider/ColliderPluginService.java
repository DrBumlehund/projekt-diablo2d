/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IGamePluginService;
import java.awt.geom.Ellipse2D;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Simon
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class ColliderPluginService implements IGamePluginService {

    private Map<String, Entity> world;

    @Override
    public void start(MetaData metaData, Map<String, Entity> world) {
        this.world = world;
        ColliderHandler.getInstance().addCollidersToEntities(world);
    }

    @Override
    public void stop(MetaData metaData) {
        if (world != null) {
            for (Entity e : world.values()) {
                ColliderHandler.getInstance().RemoveCollider(e.getId());
            }
        }
    }

}

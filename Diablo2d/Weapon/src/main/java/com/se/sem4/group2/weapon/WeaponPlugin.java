/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.weapon;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IGamePluginService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author thomaslemqvist
 */
@ServiceProvider(service = IGamePluginService.class)
public class WeaponPlugin implements IGamePluginService {

    private Map<String, Entity> world;

    @Override
    public void start(MetaData metaData, Map<String, Entity> world) {
        this.world = world;
    }

    @Override
    public void stop(MetaData metaData) {
        for (Entity ent : world.values()) {
            if (ent.getType() == EntityType.SPELL) {
                world.remove(ent.getId());
            }
        }
    }

}

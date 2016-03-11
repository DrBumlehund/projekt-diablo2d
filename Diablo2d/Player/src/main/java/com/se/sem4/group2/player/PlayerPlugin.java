/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.player;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IGamePluginService;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author ThomasLemqvist
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class PlayerPlugin implements IGamePluginService {

    private Map<String, Entity> world;
    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(MetaData metaData, Map<String, Entity> world) {
        this.world = world;
        player = createPlayer(metaData);
        world.put(player.getId(), player);
    }

    private Entity createPlayer(MetaData metaData) {
        Entity newPlayer = new Entity();

        newPlayer.setType(PLAYER);
        newPlayer.setPos(metaData.getDisplayWidth() / 2, metaData.getDisplayHeight() / 2);
        newPlayer.setRadians((float) Math.PI / 2);
        newPlayer.setSpeed(100);


        newPlayer.setShapeX(new float[2]);
        newPlayer.setShapeY(new float[2]);
        newPlayer.setRadius(10f);
        
        return newPlayer;
    }

    @Override
    public void stop(MetaData metaData) {
        world.remove(player.getId());
    }

}

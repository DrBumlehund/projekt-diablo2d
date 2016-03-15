/* 
 * Copyright (C) 2016 Casper Beese Nielsen, Jakob Tøttrup, Jesper Bager Rasmussen, Oliver Vestergaard, Simon Hjortshøj Larsen & Thomas August Lemqvist
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
        newPlayer.setMaxSpeed(100);
        newPlayer.setAcceleration(600);
        newPlayer.setDeacceleration(400);
        
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

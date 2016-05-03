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

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IGamePluginService;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import com.se.sem4.group2.common.data.SpellType;
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;
import com.se.sem4.group2.common.services.IColliderService;

/**
 *
 * @author ThomasLemqvist
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class PlayerPlugin implements IGamePluginService {

    private Map<String, Entity> world;
    private Entity player;
    //private IAssetTextureService assetManager;

    public PlayerPlugin() {
    }

    @Override
    public void start(MetaData metaData, Map<String, Entity> world, IAssetTextureService assetManager) {
        this.world = world;
        player = createPlayer(metaData, assetManager);
        world.put(player.getId(), player);
    }

    private Entity createPlayer(MetaData metaData, IAssetTextureService assetManager) {
        Entity newPlayer = new Entity();

        newPlayer.setType(PLAYER);
        newPlayer.setPos(metaData.getDisplayWidth() / 2, metaData.getDisplayHeight() / 2);
        newPlayer.setRadians(0f);
        newPlayer.setMaxSpeed(200f);
        newPlayer.setAcceleration(50f);
        newPlayer.setDeacceleration(1.3f);
        newPlayer.setName("Player");
        newPlayer.setShapeX(new float[2]);
        newPlayer.setShapeY(new float[2]);
        newPlayer.setRadius(16f);
        
        newPlayer.setMaxDamage(-1);
        newPlayer.setMinDamage(-1);
        newPlayer.setMaxHealth(100);
        
        newPlayer.setActiveSpell(SpellType.FIREBALL);
        
        Ellipse2D shape = new java.awt.geom.Ellipse2D.Float(0, 0, newPlayer.getRadius() * 2, newPlayer.getRadius() * 2);
        Collider collider = new Collider(shape, newPlayer);
        getColliderService().start(player, collider);

        //Set Sprite, Weapon, Color
        newPlayer.setSpritePath("com/se/sem4/group2/player/Wizard.png");
        assetManager.create(metaData);
        assetManager.load(newPlayer.getSpritePath(), "Texture");
        // assetManager.render(newPlayer.getSpritePath(), metaData.getDisplayWidth() / 2, metaData.getDisplayHeight() / 2, newPlayer.getRadians());

        return newPlayer;
    }

    @Override
    public void stop(MetaData metaData) {
        world.remove(player.getId());
    }

    private IColliderService getColliderService() {
        return SPILocator.locateFirst(IColliderService.class);
    }

}

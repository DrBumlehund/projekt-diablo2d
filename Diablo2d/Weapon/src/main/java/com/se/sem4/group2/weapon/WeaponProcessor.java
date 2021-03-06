/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.weapon;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import com.se.sem4.group2.common.data.GameKeys;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jesper
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class WeaponProcessor implements IEntityProcessingService {

    private long timeStamp = 0;
    private Entity player;

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity) {

        // TODO: make a better system, to allow both enemies and players to do fire spells?.
        if (metaData.getKeys().isDown(GameKeys.SPACE) && System.currentTimeMillis() - timeStamp > 200) {
            timeStamp = System.currentTimeMillis();
            for (Entity ent : world.values()) {
                if (ent.getType() == PLAYER) {
                    WeaponHandler.getInstance().fireSpell(world, ent, metaData);
                }
            }
        }

        if (entity.getType() == EntityType.SPELL) {
            // Removes the bullet if certain conditions is met.
            if (entity.getLifeTime() < entity.getLifeTimer() || entity.isColided() || entity.isDead()) {
                world.remove(entity.getId());
            }

            entity.setLifeTimer(entity.getLifeTimer() + metaData.getDelta());
        }
    }
}

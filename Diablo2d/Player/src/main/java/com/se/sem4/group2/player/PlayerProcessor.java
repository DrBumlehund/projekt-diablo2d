/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.player;

import com.se.sem4.group2.common.data.Entity;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

/**
 *
 * @author ThomasLemqvist
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class PlayerProcessor implements IEntityProcessingService {

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity) {
        float x = entity.getX();
        float y = entity.getY();
        float dt = metaData.getDelta();
        float dx = entity.getDx();
        float dy = entity.getDy();
        float speed = entity.getSpeed();
        float radians = entity.getRadians();

        if (entity.getType().equals(PLAYER)) {
            //TODO: IMPLEMENTER KEYS
            //movement
//            if (metaData.getKeys().isDown(UP)) {
                dx += cos(radians) * speed * dt;
                dy += sin(radians) * speed * dt;
//            } else if (metaData.getKeys().isDown(LEFT)) {
                dx += cos(radians + (float) (Math.PI / 2)) * speed * dt;
                dy += sin(radians + (float) (Math.PI / 2)) * speed * dt;
//            } else if (metaData.getKeys().isDown(RIGHT)) {
                dx += cos(radians - (float) (Math.PI / 2)) * speed * dt;
                dy += sin(radians - (float) (Math.PI / 2)) * speed * dt;
//            } else if (metaData.getKeys().isDown(DOWN)) {
                dx += cos(radians + (float) Math.PI) * speed * dt;
                dy += sin(radians + (float) Math.PI) * speed * dt;
//            }

            //TODO: bliv enige om gameplay og fix wrap metode...
            //set position
            x += dx * dt;
            if (x > metaData.getDisplayWidth()) {
                x = 0;
            } else if (x < 0) {
                x = metaData.getDisplayWidth();
            }

            y += dy * dt;
            if (y > metaData.getDisplayHeight()) {
                y = 0;
            } else if (y < 0) {
                y = metaData.getDisplayHeight();
            }

            
            // Update entity
            entity.setPos(x, y);
            entity.setDx(dx);
            entity.setDy(dy);
            entity.setRadians(radians);
            
        }
    }

}

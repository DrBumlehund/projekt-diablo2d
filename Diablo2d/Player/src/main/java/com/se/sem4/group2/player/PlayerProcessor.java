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
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import static com.se.sem4.group2.common.data.GameKeys.*;
import com.se.sem4.group2.common.services.IAssetServices.IAssetAudioService;
import com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService;
import java.awt.Point;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author ThomasLemqvist
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class PlayerProcessor implements IEntityProcessingService {
    
    private IAssetTextureService assetManager;

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity, IAssetTextureService assetManager, IAssetAudioService soundManager) {
        float x = entity.getX();
        float y = entity.getY();
        float dt = metaData.getDelta();
        float dx = entity.getDx();
        float dy = entity.getDy();
        float maxSpeed = entity.getMaxSpeed();
        float acceleration = entity.getAcceleration();
        float deacceleration = entity.getDeacceleration();
        float radians = entity.getRadians();
        Point mousePos = metaData.getMousePos();
        this.assetManager = assetManager;
        

        if (entity instanceof Entity) {
            if (entity.getType().equals(PLAYER)) {

                //angle
                double theta = Math.atan2(metaData.getDisplayHeight() / 2 - mousePos.y, metaData.getDisplayWidth() / 2 - mousePos.x);
                theta += Math.PI;
                radians = (float) theta;

                //movement
                dx = 0;
                dy = 0;
                if (metaData.getKeys().isDown(RIGHT)) {
                    dx += maxSpeed * dt;
                }
                if (metaData.getKeys().isDown(UP)) {
                    dy += maxSpeed * dt;
                }
                if (metaData.getKeys().isDown(DOWN)) {
                    dy -= maxSpeed * dt;
                }
                if (metaData.getKeys().isDown(LEFT)) {
                    dx -= maxSpeed * dt;
                }

                //deacceleration
                float vec = (float) Math.sqrt(dx * dx + dy * dy);

                // normalize velocity
                if (vec > 0) {
                    dx *= Math.abs(dx / vec);
                    dy *= Math.abs(dy / vec);
                }

                
                //set position
                x += dx;
                y += dy;

                assetManager.render(entity.getSpritePath(), entity, metaData);
                
                // Update entity
                entity.setPos(x, y);
                entity.setDx(dx);
                entity.setDy(dy);
                entity.setRadians(radians);
                
                updateShape(entity);
            }
        }

    }

    private void updateShape(Entity entity) {
        
        
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        float x = entity.getX();
        float y = entity.getY();
        float radians = entity.getRadians();

        shapex[0] = (float) (x);
        shapey[0] = (float) (y);

        shapex[1] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.player;

import com.se.sem4.group2.common.data.Entity;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import static com.se.sem4.group2.common.data.GameKeys.*;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

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
        float maxSpeed = entity.getMaxSpeed();
        float acceleration = entity.getAcceleration();
        float deacceleration = entity.getDeacceleration();
        float radians = entity.getRadians();

        if (entity.getType().equals(PLAYER)) {
            //TODO: IMPLEMENTER KEYS
            // Angle
//            System.out.println("1 = " + metaData.getMouseX() + ", " + metaData.getMouseY());
//            radians = ((x * metaData.getMouseX()) + (y
//                    * metaData.getMouseY())) / ((float) Math.sqrt((Math.pow(x, 2)
//                            + Math.pow(y, 2)) * (Math.pow(metaData.getMouseX(), 2)
//                            + Math.pow(metaData.getMouseY(), 2))));
            float tmpX, tmpY;
            if (x < metaData.getMouseX()) {
                tmpX = metaData.getMouseX() - x;

            } else {
                tmpX = x - metaData.getMouseX();

            }
            if (y < metaData.getMouseY()) {
                tmpY = metaData.getMouseY() - y;

            } else {
                tmpY = y - metaData.getMouseY();

            }
            radians = (float) (Math.acos(tmpX / (Math.sqrt(Math.pow(tmpX, 2) + Math.pow(tmpY, 2)))));

            if (x > metaData.getMouseX() && y > metaData.getMouseY()) {
                radians += (float) Math.PI * 0.5;
            } else if (x > metaData.getMouseX() && y < metaData.getMouseY()) {
                radians += (float) Math.PI;
            } else if (x < metaData.getMouseX() && y > metaData.getMouseY()) {
                radians -= (float) Math.PI * 0.5;
            }

            
            
            //System.out.println("2 = " + x + ", " + y);
//movement
            if (metaData.getKeys().isDown(RIGHT)) {
                dx += /*cos(radians) */ acceleration * dt;
                //dy += sin(radians) * acceleration * dt;
            }
            if (metaData.getKeys().isDown(UP)) {
                //dx += cos(radians + (float) (Math.PI / 2)) * acceleration * dt;
                dy += /*sin(radians + (float) (Math.PI / 2)) */ acceleration * dt;
            }
            if (metaData.getKeys().isDown(DOWN)) {
                //dx += cos(radians - (float) (Math.PI / 2)) * acceleration * dt;
                dy -= /*sin(radians - (float) (Math.PI / 2)) */ acceleration * dt;
            }
            if (metaData.getKeys().isDown(LEFT)) {
                dx -= /*cos(radians + (float) Math.PI) */ acceleration * dt;
                //dy += sin(radians + (float) Math.PI) * acceleration * dt;
            }

            float vec = (float) Math.sqrt(dx * dx + dy * dy);
            if (vec > 0) {
                dx -= (dx / vec) * deacceleration * dt;
                dy -= (dy / vec) * deacceleration * dt;
            }
            if (vec > maxSpeed) {
                dx = (dx / vec) * maxSpeed;
                dy = (dy / vec) * maxSpeed;
            }

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
            updateShape(entity);

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

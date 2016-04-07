/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.npc;

import com.se.sem4.group2.common.data.Entity;
import static com.se.sem4.group2.common.data.EntityType.NPC;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.awt.Point;
import java.util.Map;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Thomas
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class NpcProcessor implements IEntityProcessingService {

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
        Point mousePos = metaData.getMousePos();

        for (Entity e : world.values()) {
            if (e.getType() == PLAYER) {
                if(calcDist(e, entity) < 200)
                mousePos = new Point((int) e.getX(), (int) e.getY());
            }
        }

        if (entity instanceof Entity) {
            if (entity.getType().equals(NPC)) {

                //angle
                float tmpX = mousePos.x - x;
                float tmpY = mousePos.y - y;
                radians = (float) -(Math.atan2(tmpX, tmpY) - 0.5 * Math.PI);
                if (radians < 0) {
                    radians += (float) (2 * Math.PI);
                }
                Random r = new Random();
                int k = r.nextInt(10000);
                //movement
                if (k < 50) {
                    dx += acceleration * dt;
                }
                if (k > 50 && k < 500) {
                    dy += acceleration * dt;
                }
                if (k > 500 && k < 9550) {
                    dy -= acceleration * dt;
                }
                if (k > 9550) {
                    dx -= acceleration * dt;
                }

                //deacceleration
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

    private float calcDist(Entity me, Entity other){
        return (float) Math.sqrt(Math.pow((other.getX()-me.getX()), 2) + Math.pow((other.getY() - me.getY()), 2));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Simon
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class ColliderProcessor implements IEntityProcessingService {

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity) {
        // Adds Colliders to new entities.
        ColliderHandler.getInstance().removeExcessColliders(world);
        
        ColliderHandler.getInstance().addCollidersToEntities(world);
        
        
        
        Map<String, Collider> colliders = ColliderHandler.getInstance().GetColliders();
        for (Map.Entry<String, Collider> collider : colliders.entrySet()) {
            Entity ent;
            if (collider.getValue().getTransform() instanceof Entity) {
                ent = (Entity) collider.getValue().getTransform();
            } else {
                continue;
            }
            if (Math.abs(ent.getDx()) < 0.001f && Math.abs(ent.getDy()) < 0.001f) {
                continue;
            }
            for (Map.Entry<String, Collider> otherCollider : colliders.entrySet()) {
                if (!collider.getValue().getTransform().getId().equals(otherCollider.getValue().getTransform().getId())) {
                    if (collider.getValue().checkCollision(otherCollider.getValue())) {

                        ent.setPos(ent.getX() - ent.getDx(), ent.getY() - ent.getDy());

                        collider.getValue().OnCollision(otherCollider.getValue());
                        //System.out.println("Collison between " + collider.getTransform() + " and " + otherCollider.getTransform());
                    }
                }
            }
        }
    }

}

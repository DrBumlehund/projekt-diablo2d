/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import com.se.sem4.group2.common.data.MetaData;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author casperbeese
 */
public class ColliderProcessorTest {

    private Entity playerEnt = new Entity();
    private Entity npcEnt = new Entity();
    private MetaData metaData = new MetaData();
    private Map<String, Entity> world = new ConcurrentHashMap<>();

    public ColliderProcessorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        playerEnt.setType(EntityType.PLAYER);
        npcEnt.setType(EntityType.NPC);
        npcEnt.setX(50);
        npcEnt.setY(50);
        playerEnt.setDx(1);
        playerEnt.setDy(1);
        npcEnt.setDx(1);
        npcEnt.setDy(1);
        playerEnt.setRadius(10);
        npcEnt.setRadius(10);
        playerEnt.setMaxHealth(100);
        npcEnt.setMaxDamage(50);
        world.put(playerEnt.getId(), playerEnt);
        world.put(npcEnt.getId(), npcEnt);
        ColliderHandler.getInstance().addCollidersToEntities(world);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of checkCollision method, of class ColliderProcessor.
     */
    @Test
    public void testCollision() {
        System.out.println("Ellipse collision");
        ColliderProcessor instance = new ColliderProcessor();
        playerEnt.setX(50);
        playerEnt.setY(50);
        float beforeHealth = playerEnt.getHealth();
        System.out.println("beforehealth:" + beforeHealth);
        instance.process(metaData, world, playerEnt);
        float afterHealth = playerEnt.getHealth();
        System.out.println("afterHealth:" + afterHealth + "\n");
        assertTrue(beforeHealth != afterHealth);
    }

    @Test
    public void testNotColliding() {
        System.out.println("Ellipse not colliding");
        ColliderProcessor instance = new ColliderProcessor();
        playerEnt.setX(100);
        playerEnt.setY(100);
        float beforeHealth = playerEnt.getHealth();
        System.out.println("beforehealth:" + beforeHealth);
        instance.process(metaData, world, playerEnt);
        float afterHealth = playerEnt.getHealth();
        System.out.println("afterHealth:" + afterHealth + "\n");
        assertTrue(beforeHealth == afterHealth);
    }

}

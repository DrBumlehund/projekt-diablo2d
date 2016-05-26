/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.player;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import com.se.sem4.group2.common.data.GameKeys;
import static com.se.sem4.group2.common.data.GameKeys.NUM_1;
import static com.se.sem4.group2.common.data.GameKeys.NUM_2;
import static com.se.sem4.group2.common.data.GameKeys.UP;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.SpellType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author casperbeese
 */
public class PlayerProcessorTest {
    
    MetaData metaData = new MetaData();
    Map<String, Entity> world = new ConcurrentHashMap<>();
    Entity entity = new Entity();
    GameKeys gameKeys = new GameKeys();
    
    public PlayerProcessorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        world.put(entity.getId(), entity);
        metaData.setDelta(5);
        metaData.setMousePos(100, 100);
        entity.setX(50);
        entity.setY(50);
        entity.setMaxSpeed(50);
        entity.setMaxHealth(100);
        entity.setType(EntityType.PLAYER);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class PlayerProcessor.
     */
    @Test
    public void testPosition() {
        System.out.println("position/movement test");
        
        PlayerProcessor instance = new PlayerProcessor();
        
        float startX = entity.getX();
        float startY = entity.getY();
        
        gameKeys.setKey(UP, true);
        
        instance.process(metaData, world, entity);
        
        float afterX = entity.getX();
        float afterY = entity.getY();
        
        assertFalse(startX == afterX && startY == afterY);
    }
    
    @Test
    public void testIfAlive() {
        System.out.println("If alive test");
        
        PlayerProcessor instance = new PlayerProcessor();
        
        if (!entity.isDead()) {
            entity.takeDamage(entity.getHealth() / 2);
            assertTrue(entity.isDead() == false);
        }
        
    }
    
    @Test
    public void testDying() {
        System.out.println("Dying test");
        
        PlayerProcessor instance = new PlayerProcessor();
        
        if (!entity.isDead()) {
            entity.takeDamage(101);
            instance.process(metaData, world, entity);
            assertTrue(entity.isDead());
        } else {
            fail();
        }
    }
    
    @Test
    public void testRotation() {
        System.out.println("Rotation test");
        PlayerProcessor instance = new PlayerProcessor();
        
        float radians1 = entity.getRadians();
        instance.process(metaData, world, entity);
        float radians2 = entity.getRadians();
        assertFalse(radians1 == radians2);
        
    }
    
    @Test
    public void testChangeActivespell() {
        System.out.println("Change spell test");
        
        PlayerProcessor instance = new PlayerProcessor();
        gameKeys.setKey(NUM_1, true);
        instance.process(metaData, world, entity);
        gameKeys.setKey(NUM_1, false);
        assertTrue("Active Spell not a fireball", entity.getActiveSpell() == SpellType.FIREBALL);
        gameKeys.setKey(NUM_2, true);
        instance.process(metaData, world, entity);
        assertTrue("Active Spell is icebolt", entity.getActiveSpell() == SpellType.ICEBOLT);
        
    }
    
}

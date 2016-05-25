/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.player;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
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
public class PlayerPluginTest {

    MetaData metaData = new MetaData();
    Map<String, Entity> world = new ConcurrentHashMap<>();
    

    public PlayerPluginTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        metaData.setDelta(5);
        metaData.setMousePos(100, 100);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class PlayerPlugin.
     */
    @Test
    public void testStart() {
        System.out.println("start");

        PlayerPlugin instance = new PlayerPlugin();
        instance.start(metaData, world);
        assertTrue(world != null);
        assertFalse(world.isEmpty());
    }

    /**
     * Test of stop method, of class PlayerPlugin.
     */
    @Test
    public void testStop() {
        System.out.println("stop");
        PlayerPlugin instance = new PlayerPlugin();
        instance.start(metaData, world);
        assertTrue(!world.isEmpty());
        instance.stop(metaData);
        assertTrue(world.isEmpty());
        
    }

}

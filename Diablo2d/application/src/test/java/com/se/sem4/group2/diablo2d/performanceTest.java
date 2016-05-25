/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.diablo2d;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import com.se.sem4.group2.common.services.IGamePluginService;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openide.util.Lookup;

/**
 *
 * @author casperbeese
 */
public class performanceTest {

    private final Lookup lookup = Lookup.getDefault();
    private Collection<? extends IGamePluginService> gamePluginServices;
    private Collection<? extends IEntityProcessingService> entityProcessingServices;
    private Lookup.Result<IGamePluginService> result;
    private MetaData metaData = new MetaData();
    private Map<String, Entity> world = new ConcurrentHashMap<>();
    private Entity entity = new Entity();
    private ThreadMXBean bean = ManagementFactory.getThreadMXBean();

    public performanceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        gamePluginServices = lookup.lookupAll(IGamePluginService.class);
        for (IGamePluginService gamePluginService : gamePluginServices) {
            gamePluginService.start(metaData, world);
        }
        for (int i = 0; i < 10; i++) {
            Entity entity1 = new Entity();
            entity1.setType(EntityType.NPC);
            entity1.setRadius(10);
            entity1.setX((float) Math.random() * 500);
            entity1.setY((float) Math.random() * 500);
            entity1.setMaxSpeed(100);
            world.put(entity1.getId(), entity1);
        }
        for (int i = 0; i < 10; i++) {
            Entity entity1 = new Entity();
            entity1.setType(EntityType.PLAYER);
            entity1.setRadius(10);
            entity1.setX((float) (Math.random() * 500) + 1);
            entity1.setY((float) (Math.random() * 500) + 1);
            entity1.setMaxSpeed(100);
            world.put(entity1.getId(), entity1);
        }
        for (int i = 0; i < 10; i++) {
            Entity entity1 = new Entity();
            entity1.setType(EntityType.SPELL);
            entity1.setRadius(10);
            entity1.setX((float) (Math.random() * 500) + 1);
            entity1.setY((float) (Math.random() * 500) + 1);
            entity1.setMaxSpeed(10);
            world.put(entity1.getId(), entity1);
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestProcessors() {

        entityProcessingServices = lookup.lookupAll(IEntityProcessingService.class);
        for (IEntityProcessingService entityProcessingService : entityProcessingServices) {

            for (Entity ent : world.values()) {
                long startTime = bean.getCurrentThreadCpuTime();
                for (int i = 0; i < 1000; i++) {
                    entityProcessingService.process(metaData, world, ent);
                }
                long stopTime = bean.getCurrentThreadCpuTime();
                System.out.println(ent.getType() + " in " + entityProcessingService.getClass() + " took: " + ((stopTime - startTime) / 1000) + " nano seconds");
                System.out.println(ent.getType() + " in " + entityProcessingService.getClass() + " took: " + (1000000000 / ((stopTime - startTime) / 1000)) + " frames/s");

            }
        }

    }

}

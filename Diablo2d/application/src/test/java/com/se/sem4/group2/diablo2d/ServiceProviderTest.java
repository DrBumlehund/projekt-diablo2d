package com.se.sem4.group2.diablo2d;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import com.se.sem4.group2.common.services.IGamePluginService;
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
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 *
 * @author casperbeese
 */
public class ServiceProviderTest {

    private final Lookup lookup = Lookup.getDefault();
    private Collection<? extends IGamePluginService> gamePluginServices;
    private Collection<? extends IEntityProcessingService> entityProcessingServices;
    private Lookup.Result<IGamePluginService> result;
    private MetaData metaData = new MetaData();
    private Map<String, Entity> world = new ConcurrentHashMap<>();
    private ThreadMXBean bean = ManagementFactory.getThreadMXBean();

    public ServiceProviderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Black-Box validation of the IGamePluingService providers.
     */
    @Test
    public void pluginServiceProviderTest() {

        gamePluginServices = lookup.lookupAll(IGamePluginService.class);

        assertTrue("Test failed, because gamePluginServices is empty", !gamePluginServices.isEmpty());
        System.out.println("gamePluginServices size:" + gamePluginServices.size());
        for (IGamePluginService gp : gamePluginServices) {
            assertNotNull(gp);
            System.out.println("Success got IGamePluginService");
            
        }
    }

    /**
     * Black-Box validation of the IEntityProcessingService providers.
     */
    @Test
    public void processingServiceProviderTest() {

        entityProcessingServices = lookup.lookupAll(IEntityProcessingService.class);

        assertTrue("Test failed, because entityProcessingServices is empty",!entityProcessingServices.isEmpty());

        for (IEntityProcessingService entityProcessingService : entityProcessingServices) {
            assertNotNull(entityProcessingService);
            System.out.println("Success got IEntityProcessingService");   
        }
    }

    @Test
    public void testStart() {
        gamePluginServices = lookup.lookupAll(IGamePluginService.class);
        for (IGamePluginService gamePluginService : gamePluginServices) {

            long startTime = bean.getCurrentThreadCpuTime();
            gamePluginService.start(metaData, world);
            long stopTime = bean.getCurrentThreadCpuTime();
            System.out.println(gamePluginService + " took: " + (stopTime - startTime) + " nano seconds");
        }
        assertFalse(world.isEmpty());
    }

    @Test
    public void testStop() {
        gamePluginServices = lookup.lookupAll(IGamePluginService.class);
        for (IGamePluginService gamePluginService : gamePluginServices) {
            long startTime = bean.getCurrentThreadCpuTime();
            gamePluginService.start(metaData, world);
            long stopTime = bean.getCurrentThreadCpuTime();
            System.out.println(gamePluginService + " took: " + (stopTime - startTime) + " nano seconds");
        }
        for (IGamePluginService gamePluginService : gamePluginServices) {

            long startTime = bean.getCurrentThreadCpuTime();
            gamePluginService.stop(metaData);
            long stopTime = bean.getCurrentThreadCpuTime();
            System.out.println(gamePluginService + " took: " + (stopTime - startTime) + " nano seconds");

        }
        assertTrue(world.isEmpty());
    }

}

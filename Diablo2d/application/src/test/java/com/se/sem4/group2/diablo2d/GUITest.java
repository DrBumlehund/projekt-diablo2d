package com.se.sem4.group2.diablo2d;

import java.io.IOException;
import static java.nio.file.Files.copy;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import static org.sikuli.script.Key.SPACE;
import org.sikuli.script.Screen;

public class GUITest {

    static String APP;
    static String UPDATES_FILE;
    static String UPDATES_FILE_WITH_MOVEMENT;
    static String UPDATES_FILE_WITHOUT_MOVEMENTANDMAP;
    static String UPDATES_FILE_WITH_MAP;
    static String GUIELEMENTS;
    static Screen screen;
    static App lostWizard;

    @BeforeClass
    public static void setUpClass() throws IOException, InterruptedException {
        APP = "/Users/thomaslemqvist/github/projekt-diablo2d/Diablo2d/application/target/diablo2d/bin/diablo2d";
        UPDATES_FILE = "/Users/thomaslemqvist/Documents/Netbeans/projekt/netbeans_site/updates.xml";
        UPDATES_FILE_WITH_MOVEMENT = "/Users/thomaslemqvist/Documents/Netbeans/projekt/netbeans_site/updatesWithMovement/updates.xml";
        UPDATES_FILE_WITHOUT_MOVEMENTANDMAP = "/Users/thomaslemqvist/Documents/Netbeans/projekt/netbeans_site/updatesWithoutMovementAndMap/updates.xml";
        UPDATES_FILE_WITH_MAP = "/Users/thomaslemqvist/Documents/Netbeans/projekt/netbeans_site/updatesWithMap/updates.xml";
        GUIELEMENTS = "/Users/thomaslemqvist/github/projekt-diablo2d/Diablo2d/application/src/test/resouces/LostWizGUI/";
        ImagePath.setBundlePath(GUIELEMENTS);
        screen = new Screen();
    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        System.out.println("Test finished, closing app");
        lostWizard.close();
    }

    @Test
    public void GUITest() throws FindFailed, InterruptedException, IOException {
        System.out.println("Unloading movement and map...");
        Path updatesFile = Paths.get(UPDATES_FILE);
        copy(Paths.get(UPDATES_FILE_WITHOUT_MOVEMENTANDMAP), updatesFile, REPLACE_EXISTING);
        
        System.out.println("Opening application...");
        lostWizard = App.focus(APP);
        Thread.sleep(5000);
        
        System.out.println("finding player");

        try {
            Assert.assertTrue("Finding player", screen.wait("Player.png", 15) != null);
        } catch (FindFailed failed) {
            fail("The player was not found!");
        }
        screen.keyDown(SPACE);
        screen.keyUp(SPACE);
        try {

            Assert.assertTrue("Finding firebolt", screen.wait("Firebolt.png", 5) != null);
        } catch (FindFailed failed) {
            fail("The firebolt was not found!");
        }
        screen.type("2");

        screen.keyDown(SPACE);
        screen.keyUp(SPACE);
        try {
            Assert.assertTrue("Finding frostbolt", screen.wait("Frostbolt.png", 5) != null);

        } catch (FindFailed fail) {
            fail("The frostbolt was not found!");
        }
        screen.type("3");
        screen.keyDown(SPACE);
        screen.keyUp(SPACE);
 
        try {
            Assert.assertTrue("Finding lightningbolt", screen.wait("Lightningbolt.png", 5) != null);

        } catch (FindFailed fail) {
            fail("The lightningbolt was not found!");
        }

        copy(Paths.get(UPDATES_FILE_WITH_MOVEMENT), updatesFile, REPLACE_EXISTING);
        System.out.println("Loading movement...");

        try {
            Assert.assertTrue("Finding NPC", screen.wait("NPC.png", 15) != null);
        } catch (FindFailed fail) {
            fail("The NPC was not found!");
        }
        System.out.println(screen.wait("NPC.png", 15));

        copy(Paths.get(UPDATES_FILE_WITH_MAP), updatesFile, REPLACE_EXISTING);
        System.out.println("Loading map...");
        try {
            Assert.assertTrue("Finding NPC", screen.wait("grass.png", 10) != null || screen.wait("water.png", 10) != null || screen.wait("soil.png", 10) != null);
        } catch (FindFailed fail) {
            fail("The map was not found!");
        }
    }

}

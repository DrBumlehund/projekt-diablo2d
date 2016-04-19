/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.map;

import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.Tile;
import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.services.IMapPluginService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import org.openide.util.lookup.ServiceProvider;
import com.se.sem4.group2.common.services.IAssetServices.IAssetService;
import com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService;
import java.util.ServiceLoader;

/**
 *
 * @author Simon
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IMapPluginService.class)
public class MapPlugin implements IMapPluginService {

    private MetaData metaData;
    private WorldMap worldMap;
    private IAssetTextureService assetManager;
    private File file = new File("");
    private String pathToJars = (file.getAbsolutePath() + "/diablo2d/modules");
    private File modulesFolder = new File(pathToJars);

    @Override
    public WorldMap start(MetaData metaData, IAssetTextureService assetManager) {
        this.metaData = metaData;
        this.assetManager = assetManager;
        try {
            this.worldMap = createMap();
        } catch (IOException ex) {
            Logger.getLogger(MapPlugin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MapPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.worldMap;

    }

        public void generateMap() {
        System.out.println("GENERATING");
        for (int x = worldMap.getxMin(); x < worldMap.getxMax() + 1; x++) {
            for (int y = worldMap.getyMin(); y < worldMap.getyMax() + 1; y++) {
                addTile(x, y);
            }
        }
    }

    Random r = new Random();

    public Tile generateSingleTile(int x, int y) {
        Tile noiseTile;
        Tile[] tileTypes = worldMap.getTileTypes();
        double value = worldMap.getNoise().eval(x / worldMap.FEATURE_SIZE, y / worldMap.FEATURE_SIZE, 0.0);
        if (value >= worldMap.MIN_DIRT) { // DIRT
            noiseTile = tileTypes[0];
        } else if (value <= worldMap.MAX_WATER) { // WATER
            noiseTile = tileTypes[1];
        } else { // GRASS
            int rnd = r.nextInt(3);
            if (rnd == 0) {
                noiseTile = tileTypes[2];
            } else if (rnd == 1) {
                noiseTile = tileTypes[3];
            } else {
                noiseTile = tileTypes[4];
            }
        }
        //System.out.println(noiseTile.getSource() + " x: " + x + ", y: " + y);
        return noiseTile;
    }
    
        public void addTile(int x, int y) {
            int xMin = worldMap.getxMin();
        int xMax = worldMap.getxMax();
        int yMax = worldMap.getyMax();
        int yMin = worldMap.getyMin();
        if (x < xMin) {
            xMin = x;
            worldMap.getWorldMap().remove(xMax);
            xMax--;
        } else if (x > xMax) {
            xMax = x;
            worldMap.getWorldMap().remove(xMin);
            xMin++;
        }
        if (y < yMin) {
            yMin = y;
            for (int i : worldMap.getWorldMap().keySet()) {
                worldMap.getWorldMap().get(i).remove(yMax);
            }
            yMax--;
        } else if (y > yMax) {
            yMax = y;
            for (int i : worldMap.getWorldMap().keySet()) {
                worldMap.getWorldMap().get(i).remove(yMin);
            }
            yMin++;
        }
        Tile tile = generateSingleTile(x, y);
        if (worldMap.getWorldMap().get(x) != null) {
            worldMap.getWorldMap().get(x).put(y, tile);
        } else {
            worldMap.getWorldMap().put(x, new HashMap<Integer, Tile>());
            worldMap.getWorldMap().get(x).put(y, tile);
        }
    }
    
    @Override
    public void stop(MetaData metaData) {
        unloadMap();
    }

    private WorldMap createMap() throws IOException, URISyntaxException {
        WorldMap map = new WorldMap(new Random().nextLong());
        map.generateMap();
        loadImages(map.getMap());
        
        return map;
    }

    private void loadImages(HashMap<Integer, HashMap<Integer, Tile>> map) throws IOException, URISyntaxException {
        for (Integer x : map.keySet()) {
            for (Integer y : map.get(x).keySet()) {
                Tile tile = map.get(x).get(y);
                assetManager.load(tile.getSource(), "Texture");
                
//                InputStream is = MapPlugin.class.getResourceAsStream(tile.getSource());
//                byte[] imageBytes = getResources(modulesFolder, tile.getSource());
//                tile.injectSource(imageBytes);
            }
        }
    }

    private byte[] getBytesFromResource(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
        } catch (IOException ex) {
            Logger.getLogger(MapPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return buffer.toByteArray();
    }

    public byte[] getResources(File modulesFolder, String path) throws IOException, URISyntaxException {
        for (File jarFile : modulesFolder.listFiles()) {
            if (jarFile.getName().contains(".jar")) {  // Run with JAR file
                JarFile jar = new JarFile(jarFile);

                Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
                while (entries.hasMoreElements()) {

                    final String name = entries.nextElement().getName();

                    ZipEntry zipEntry = jar.getEntry(name);
                    if (name.equals(path)) { //filter according to the path
                        System.out.println(name);

                        return getBytesFromResource(jar.getInputStream(zipEntry));
                    }
                }
                jar.close();
            }
        }

        return null;
    }

    private void unloadMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

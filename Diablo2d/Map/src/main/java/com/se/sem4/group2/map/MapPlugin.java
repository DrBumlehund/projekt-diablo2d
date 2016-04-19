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

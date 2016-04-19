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
    private String pathToJars = (file.getAbsolutePath() + "/target/diablo2d/diablo2d/modules");
    private File modulesFolder = new File(pathToJars);

    @Override
    public WorldMap start(MetaData metaData, IAssetTextureService assetManager) {
        this.metaData = metaData;
        this.assetManager = assetManager;
        try {
            this.worldMap = createMap();
        } catch (IOException | URISyntaxException ex) {
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
            }
        }
    }

    private void unloadMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

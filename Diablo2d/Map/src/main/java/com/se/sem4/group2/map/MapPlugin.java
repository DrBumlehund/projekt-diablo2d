/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.map;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IGamePluginService;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import com.se.sem4.group2.common.data.Tile;
import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.services.IMapPluginService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Simon
 */

@ServiceProvider(service = com.se.sem4.group2.common.services.IMapPluginService.class)
public class MapPlugin implements IMapPluginService {

    private MetaData metaData;
    private WorldMap worldMap;
    
    @Override
    public WorldMap start(MetaData metaData) {
        this.metaData = metaData;
        this.worldMap = createMap();
        return this.worldMap;
    }

    @Override
    public void stop(MetaData metaData) {
        unloadMap();
    }

    private WorldMap createMap() {
        WorldMap map = new WorldMap();
        map.generateMap(12);
        injectImages(map.getMap());
        
        return map;
    }
    
    private void injectImages (Tile[][] map) {
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                InputStream is = MapPlugin.class.getResourceAsStream(tile.getSource());
                byte[] imageBytes = getBytesFromResource(is);
                tile.injectSource(imageBytes);
            }
        }
    }
    
    private byte[] getBytesFromResource (InputStream is) {
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

    private void unloadMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
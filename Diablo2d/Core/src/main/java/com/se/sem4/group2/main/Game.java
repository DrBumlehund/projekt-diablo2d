/* 
 * Copyright (C) 2016 Casper Beese Nielsen, Jakob Tøttrup, Jesper Bager Rasmussen, Oliver Vestergaard, Simon Hjortshøj Larsen & Thomas August Lemqvist
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.se.sem4.group2.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.Tile;
import com.se.sem4.group2.common.data.Transform;
import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IColliderService;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import com.se.sem4.group2.common.services.IGamePluginService;
import com.se.sem4.group2.common.services.IMapPluginService;
import com.se.sem4.group2.managers.GameInputProcessor;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.plaf.metal.MetalIconFactory;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;

    private ShapeRenderer sr;

    private IGamePluginService playerPlugin;
    private IMapPluginService mapPlugin;
    private IEntityProcessingService playerProcessor;

    private final MetaData metaData = new MetaData();
//    private final GameData md = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private Map<String, Entity> world = new ConcurrentHashMap<>();
    private WorldMap worldMap;
    private SpriteBatch batch;
    private List<IGamePluginService> gamePlugins;
    private final Lookup lookup = Lookup.getDefault();

    @Override
    public void create() {

        metaData.setDisplayWidth(Gdx.graphics.getWidth());
        metaData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(metaData.getDisplayWidth(), metaData.getDisplayHeight());
        cam.translate(metaData.getDisplayWidth() / 2, metaData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(metaData)
        );
        
        
        Lookup.Result<IGamePluginService> result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        gamePlugins = new ArrayList<>(result.allInstances());
        result.allItems();

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(metaData, world);
        }
        
        for (IMapPluginService iMapPlugin : getMapPluginServices()) {
            worldMap = iMapPlugin.start(metaData);
        }
        
        
        for (IColliderService colliderSErvices : getColliderServices()) {
            Transform transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
            colliderSErvices.start(transform, new Collider(new Polygon(), transform));
            transform = new Transform();
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        metaData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        metaData.getKeys().update();
    }

    private void update() {
        // Process entities
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            for (Entity e : world.values()) {
                entityProcessorService.process(metaData, world, e);
            }
        }
        
        long start = System.nanoTime();    
        // Physics update
        for (Entity entity : world.values()) {
            // TODO: dx and dy are probably never zero. Might wanna floor them down a couple decimal places
            //if (entity.getDx() != 0 && entity.getDy() != 0) {
                final Collection<? extends IColliderService> colliderServices = getColliderServices();
                for (IColliderService colliderService : colliderServices) {
                    Collider collider = colliderService.getCollider();
                    for (IColliderService colliderService2 : colliderServices) {
                        if (colliderService2.checkCollision(collider)) {
                            entity.setX(entity.getX() - entity.getDx());
                            entity.setY(entity.getY() - entity.getDy());
                            
                        }
                    }
                }
            //}
        }
        
        long elapsedTime = System.nanoTime() - start;
        System.out.println("Time to update colliders: " + elapsedTime);
    }

    private void draw() {
        for (int x=0; x<worldMap.getMap().length; x++) {
            for (int y=0; y<worldMap.getMap().length; y++) {
                Tile tile = worldMap.getMap()[x][y];
                Texture texture = null;
                if (textureResources.containsKey(tile.toString() + tile.getSource())) {
                    texture = textureResources.get(tile.toString() + tile.getSource());
                } else {
                    Pixmap pixmap = new Pixmap(tile.getImage(), 0, tile.getImage().length);
                    texture = new Texture(pixmap);
                    textureResources.put(tile.toString() + tile.getSource(), texture);
                }
                batch.begin();
                batch.draw(texture, x*texture.getWidth(), y*texture.getHeight());
                batch.end();
            }
        }
        
        for (Entity entity : world.values()) {
            //Gdx.gl.glClearColor(0, 0, 0, 1);
            //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            float[] shapeX = entity.getShapeX();
            float[] shapeY = entity.getShapeY();
            
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.WHITE);
            sr.circle(entity.getX(), entity.getY(), entity.getRadius());
            
            sr.end();
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(Color.BLACK);
            sr.line(shapeX[0], shapeY[0], shapeX[1], shapeY[1]);
            sr.end();
        }
    }
    
    Map<String, Texture> textureResources = new HashMap<>();

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

    private Collection<? extends IMapPluginService> getMapPluginServices() {
        return SPILocator.locateAll(IMapPluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }

    private Collection<? extends IColliderService> getColliderServices() {
        return SPILocator.locateAll(IColliderService.class);
    }
    
    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {
            for (IGamePluginService updatedGamePlugin : lookup.lookupAll(IGamePluginService.class)) {
                if (!gamePlugins.contains(updatedGamePlugin)) {
                    updatedGamePlugin.start(metaData, world);
                    gamePlugins.add(updatedGamePlugin);
               }
            }
        }
    };
}

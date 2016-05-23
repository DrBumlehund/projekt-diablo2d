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
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.Tile;
import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import com.se.sem4.group2.common.services.IGamePluginService;
import com.se.sem4.group2.common.services.IMapProcessingService;
import com.se.sem4.group2.managers.GameInputProcessor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import static com.se.sem4.group2.common.data.EntityType.SPELL;
import com.se.sem4.group2.common.data.GameEvent;
import com.se.sem4.group2.managers.JarFileResolver;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private OrthographicCamera cam;
    private ShapeRenderer sr;
    private SpriteBatch batch;
    private final Lookup lookup = Lookup.getDefault();
    private final MetaData metaData = new MetaData();
    private Map<String, Entity> world = new ConcurrentHashMap<>();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    Lookup.Result<IGamePluginService> result;
    Random rng = new Random();

    // Assetmanager stuff:
    private final JarFileResolver jfr = new JarFileResolver();
    private final AssetManager am = new AssetManager(jfr);

    @Override
    public void create() {
        metaData.setDisplayWidth(Gdx.graphics.getWidth());
        metaData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(metaData.getDisplayWidth(), metaData.getDisplayHeight());
        cam.translate(metaData.getDisplayWidth() / 2, metaData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new GameInputProcessor(metaData));
        
        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(cam.combined);
        sr.setProjectionMatrix(cam.combined);

        metaData.setDelta(Gdx.graphics.getDeltaTime());
        metaData.getKeys().update();
        
        update();
        draw();
        playSounds();
    }

    private void update() {
        // Process entities
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
//            System.out.println(entityProcessorService.getClass());
            for (Entity entity : world.values()) {
                entityProcessorService.process(metaData, world, entity);
            }
        }

        for (IMapProcessingService mapProcesser : getMapProcessingServices()) {
//            System.out.println(mapProcesser.getClass());
            mapProcesser.process(cam.position.x, cam.position.y, metaData.getWorldMap());
        }

    }

    private void draw() {
        //batch.setProjectionMatrix(cam.combined);
        drawMap();
        drawEntities();
    }

    private void drawMap() {
        if (metaData.getWorldMap() != null) {
            WorldMap worldMap = metaData.getWorldMap();

            int xMax = worldMap.getxMax();
            int xMin = worldMap.getxMin();
            int yMax = worldMap.getyMax();
            int yMin = worldMap.getyMin();

            for (int x = xMin; x < xMax + 1; x++) {
                for (int y = yMin; y < yMax + 1; y++) {
                    Tile tile = worldMap.getTile(x, y);
                    if (tile == null) {
                        return;
                    }
                    loadTexture(tile.getSource());

                    Texture texture = am.get(tile.getSource(), Texture.class);
                    if (texture == null) {
                        System.out.println("Error: Texture was null while attempting to draw map: " + tile.getSource());
                        loadTexture(tile.getSource());
                        continue;
                    }
                    batch.begin();
                    batch.draw(texture, x * texture.getWidth(), y * texture.getHeight());
                    batch.end();

                }
            }

            if (worldMap.getMap().isEmpty()) {
                System.out.println("MAP EMPTY!");
                metaData.setWorldMap(null);
            }
        }
    }

    private void drawEntities() {
        for (Entity entity : world.values()) {

            if (entity.getType() == EntityType.PLAYER) {
                cam.position.set(new Vector3((float) entity.getX(), (float) entity.getY(), 1f));
            }
            cam.update();

            loadTexture(entity.getTexturePath());

            batch.setProjectionMatrix(cam.combined);
            batch.begin();
            //System.out.println(path);
            Texture tex = am.get(entity.getTexturePath(), Texture.class);
            Sprite sprite = new Sprite(tex);
            sprite.setSize((int) entity.getRadius() * 2, (int) entity.getRadius() * 2);

            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.rotate((float) (entity.getRadians() * (180 / Math.PI)));

            sprite.setCenter(entity.getX(), entity.getY());

            sprite.draw(batch);

            batch.end();

            // Health bars.
            if (entity.getType() != SPELL) {
                sr.begin(ShapeRenderer.ShapeType.Filled);
                float width = 25f/* entity.getMaxHealth() * 0.07f*/;
                float height = 2f;
                float x = entity.getX() - width / 2;
                float y = entity.getY() + (entity.getRadius() + 10f);
                // healthbar border.
                sr.setColor(Color.BLACK);
                sr.rect(x - 1, y - 1, width + 2, height + 2);
                // health bar background
                sr.setColor(Color.RED);
                sr.rect(x, y, width, height);
                // Health overlay.
                sr.setColor(Color.GREEN);
                sr.rect(x, y, entity.getHealthPercentage(width), height);
                sr.end();
            }

        }
    }

    private void playSounds() {
        List<GameEvent> soundEvents = metaData.getGameEvents();
        if (!soundEvents.isEmpty()) {
            for (Entity entity : world.values()) {
                if (!entity.getSoundPaths().isEmpty()) {
                    for (GameEvent ge : soundEvents) {
                        int index = rng.nextInt(entity.getSoundPaths().size());
                        String path = entity.getSoundPaths().get(index);
                        loadSound(path);

                        Sound sound = am.get(path, Sound.class);
                        sound.play();

                        soundEvents.remove(ge);
                    }
                }
            }
        }
    }

    private void loadTexture(String path) {
        if (!am.isLoaded(path)) {
            am.load(path, Texture.class);
            am.finishLoading();
        }
    }

    private void loadSound(String path) {
        if (!am.isLoaded(path)) {
            am.load(path, Sound.class);
            am.finishLoading();
        }
    }

    private void loadMusic(String path) {
        if (!am.isLoaded(path)) {
            am.load(path, Music.class);
            am.finishLoading();
        }
    }

    @Override
    public void resize(int width, int height) {
        metaData.setDisplayWidth(width);
        metaData.setDisplayHeight(height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        // Disposes all assets.
        am.dispose();
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IMapProcessingService> getMapProcessingServices() {
        return lookup.lookupAll(IMapProcessingService.class);
    }
    
    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(metaData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(metaData);
                    gamePlugins.remove(gs);
                }
            }
        }
    };
}

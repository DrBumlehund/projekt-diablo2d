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
import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import static com.se.sem4.group2.common.data.EntityType.NPC;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import static com.se.sem4.group2.common.data.EntityType.PROJECTILE;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.Tile;
import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IAIProcessingService;
import com.se.sem4.group2.common.services.IColliderProcessingService;
import com.se.sem4.group2.common.services.IColliderService;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import com.se.sem4.group2.common.services.IGamePluginService;
import com.se.sem4.group2.common.services.IMapPluginService;
import com.se.sem4.group2.common.services.IMapProcessingService;
import com.se.sem4.group2.managers.AudioProcessor;
import com.se.sem4.group2.managers.GameControllerInputProcessor;
import com.se.sem4.group2.managers.GameInputProcessor;
import com.se.sem4.group2.managers.TextureProcessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.Font.font;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

public class Game implements ApplicationListener {

    private OrthographicCamera cam;

    private ShapeRenderer sr;

    private IGamePluginService playerPlugin;
    private IMapPluginService mapPlugin;
    private IEntityProcessingService playerProcessor;

    private final MetaData metaData = new MetaData();
    private final List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private Map<String, Entity> world = new ConcurrentHashMap<>();
    private WorldMap worldMap;
    private SpriteBatch batch;
    private List<IGamePluginService> gamePlugins;
    private final Lookup lookup = Lookup.getDefault();
    private final AudioProcessor aP = new AudioProcessor();
//    private Music centipede;
    private final TextureProcessor tP = new TextureProcessor();

    @Override
    public void create() {

        aP.load("com/se/sem4/group2/core/centipede.mp3", "Music");
        aP.load("com/se/sem4/group2/core/tristram.mp3", "Music");

        //aP.playMusic("com/se/sem4/group2/core/tristram.mp3");

        metaData.setDisplayWidth(Gdx.graphics.getWidth());
        metaData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(metaData.getDisplayWidth(), metaData.getDisplayHeight());
        cam.update();

        sr = new ShapeRenderer();
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(metaData)
        );
//        //TODO: FIX CONTROLLER LISTENER ... 
//        Controllers.addListener(
//                new GameControllerInputProcessor(metaData)
//        );

        Lookup.Result<IGamePluginService> result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        gamePlugins = new ArrayList<>(result.allInstances());
        result.allItems();

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(metaData, world, tP);
        }

        for (IMapPluginService iMapPlugin : getMapPluginServices()) {
            worldMap = iMapPlugin.start(metaData, tP);
        }

    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        metaData.setDelta(Gdx.graphics.getDeltaTime());
        sr.setProjectionMatrix(cam.combined);
        batch.setProjectionMatrix(cam.combined);

        update();

        draw();

        metaData.getKeys().update();
    }

    private void update() {

        // Process entities
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            for (Entity e : world.values()) {
                entityProcessorService.process(metaData, world, e, tP, aP);
                if (e.getType() == EntityType.PLAYER) {
                    cam.position.set(new Vector3((float) e.getX(), (float) e.getY(), 1f));
                    cam.update();
                }
            }
        }

        for (IMapProcessingService mapProcesser : getMapProcessingServices()) {
            mapProcesser.process(cam.position.x, cam.position.y, worldMap);
        }

        for (IColliderProcessingService colliderProcessingService : getColliderProcessingServices()) {
            colliderProcessingService.process();
        }

        for (IAIProcessingService service : getAIProcessingServices()) {
            for (Entity e : world.values()) {
                service.process(metaData, world, worldMap);
            }
        }

    }

    private void draw() {

        if (worldMap != null) {
            int xMax = worldMap.getxMax();
            int xMin = worldMap.getxMin();
            int yMax = worldMap.getyMax();
            int yMin = worldMap.getyMin();

            for (int x = xMin; x < xMax + 1; x++) {
                for (int y = yMin; y < yMax + 1; y++) {
                    Tile tile = worldMap.getTile(x, y);
                    Texture texture = tP.textures.get(tile.getSource());
                    // TODO: No idea why texture is sometimes null
                    if (texture == null) {
                        System.out.println("Error: Texture was null while attempting to draw map: " + tile.getSource());
                        tP.load(tile.getSource(), "Texture");
                        continue;
                    }
                    batch.begin();
                    batch.draw(texture, x * texture.getWidth(), y * texture.getHeight());
                    batch.end();

                }
            }
        }

        for (Entity entity : world.values()) {

            float[] shapeX = entity.getShapeX();
            float[] shapeY = entity.getShapeY();

            sr.begin(ShapeRenderer.ShapeType.Filled);
            if (entity.getType() == PLAYER) {
                //tP.render(entity.getSpritePath(), entity.getDx() + (metaData.getDisplayWidth() / 2), entity.getDy() + (metaData.getDisplayHeight() / 2), entity.getRadians());
                tP.render(entity.getSpritePath(), entity, metaData);
                //sr.setColor(Color.WHITE);
            } else if (entity.getType() == NPC) {
                sr.setColor(Color.RED);
                sr.circle(entity.getX(), entity.getY(), entity.getRadius());
            } else {
               // sr.setColor(Color.BLACK);
                tP.render(entity.getSpritePath(), entity, metaData);
               // sr.circle(entity.getX(), entity.getY(), entity.getRadius());
            }

            sr.end();

            if (entity.getType() == NPC) {
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.setColor(Color.BLACK);
                sr.line(shapeX[0], shapeY[0], shapeX[1], shapeY[1]);
                sr.end();

                // Health bars.
                sr.begin(ShapeRenderer.ShapeType.Filled);
                float width = /*25f*/ entity.getMaxHealth() * 0.07f;
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
//        centipede.dispose();
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

    private Collection<? extends IMapProcessingService> getMapProcessingServices() {
        return SPILocator.locateAll(IMapProcessingService.class);
    }

    private Collection<? extends IColliderProcessingService> getColliderProcessingServices() {
        return SPILocator.locateAll(IColliderProcessingService.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {
            for (IGamePluginService updatedGamePlugin : lookup.lookupAll(IGamePluginService.class)) {
                if (!gamePlugins.contains(updatedGamePlugin)) {
                    updatedGamePlugin.start(metaData, world, tP);
                    gamePlugins.add(updatedGamePlugin);
                }
            }
        }
    };

    private Iterable<IAIProcessingService> getAIProcessingServices() {
        return SPILocator.locateAll(IAIProcessingService.class);
    }
}

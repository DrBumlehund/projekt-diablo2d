/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.Tile;
import com.se.sem4.group2.common.data.WorldMap;
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import com.se.sem4.group2.common.services.IGamePluginService;
import com.se.sem4.group2.common.services.IMapPluginService;
import com.se.sem4.group2.managers.GameInputProcessor;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.plaf.metal.MetalIconFactory;

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

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(metaData, world);
        }
        
        for (IMapPluginService iMapPlugin : getMapPluginServices()) {
            worldMap = iMapPlugin.start(metaData);
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
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            for (Entity e : world.values()) {
                entityProcessorService.process(metaData, world, e);
            }
        }
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import com.se.sem4.group2.common.services.IGamePluginService;
import com.se.sem4.group2.managers.GameInputProcessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private SpriteBatch batch;

    private final MetaData md = new MetaData();
//    private final GameData md = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private Map<String, Entity> world = new ConcurrentHashMap<>();

    @Override
    public void create() {

        md.setDisplayWidth(Gdx.graphics.getWidth());
        md.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(md.getDisplayWidth(), md.getDisplayHeight());
        cam.translate(md.getDisplayWidth() / 2, md.getDisplayHeight() / 2);
        cam.update();

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(md)
        );

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(md, world);
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        md.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        md.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            for (Entity e : world.values()) {
                entityProcessorService.process(md, world, e);
            }
        }
    }

    private void draw() {
        for (Entity entity : world.values()) {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            entity.getSprite().draw(batch);
            batch.end();

        }
    }

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

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }
}

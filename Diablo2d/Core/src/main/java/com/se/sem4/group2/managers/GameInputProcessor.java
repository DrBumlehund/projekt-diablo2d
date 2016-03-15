/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.se.sem4.group2.common.data.GameKeys;
import com.se.sem4.group2.common.data.MetaData;

/**
 *
 * @author jakobtottrup
 */
public class GameInputProcessor extends InputAdapter {

    private final MetaData metaData;

    public GameInputProcessor(MetaData metaData) {
        this.metaData = metaData;
    }

    // ============= MOVE SYSTEM ================ // 
    // UNIMPLEMENTED//
    @Override
    public boolean mouseMoved(int x, int y) {
        metaData.setMousePos(x, (metaData.getDisplayHeight() - y));
        return true;
    }

    public boolean keyDown(int k) {
        if (k == Input.Keys.UP || k == Input.Keys.W) {
            metaData.getKeys().setKey(GameKeys.UP, true);
        }
        if (k == Input.Keys.LEFT || k == Input.Keys.A) {
            metaData.getKeys().setKey(GameKeys.LEFT, true);
        }
        if (k == Input.Keys.DOWN || k == Input.Keys.S) {
            metaData.getKeys().setKey(GameKeys.DOWN, true);
        }
        if (k == Input.Keys.RIGHT || k == Input.Keys.D) {
            metaData.getKeys().setKey(GameKeys.RIGHT, true);
        }
        if (k == Input.Keys.ENTER) {
            metaData.getKeys().setKey(GameKeys.ENTER, true);
        }
        if (k == Input.Keys.ESCAPE) {
            metaData.getKeys().setKey(GameKeys.ESCAPE, true);
        }
        if (k == Input.Keys.SPACE) {
            metaData.getKeys().setKey(GameKeys.SPACE, true);
        }
        if (k == Input.Keys.SHIFT_LEFT || k == Input.Keys.SHIFT_RIGHT) {
            metaData.getKeys().setKey(GameKeys.SHIFT, true);
        }
        return true;
    }

    public boolean keyUp(int k) {
        if (k == Input.Keys.UP || k == Input.Keys.W) {
            metaData.getKeys().setKey(GameKeys.UP, false);
        }
        if (k == Input.Keys.LEFT || k == Input.Keys.A) {
            metaData.getKeys().setKey(GameKeys.LEFT, false);
        }
        if (k == Input.Keys.DOWN || k == Input.Keys.S) {
            metaData.getKeys().setKey(GameKeys.DOWN, false);
        }
        if (k == Input.Keys.RIGHT || k == Input.Keys.D) {
            metaData.getKeys().setKey(GameKeys.RIGHT, false);
        }
        if (k == Input.Keys.ENTER) {
            metaData.getKeys().setKey(GameKeys.ENTER, false);
        }
        if (k == Input.Keys.ESCAPE) {
            metaData.getKeys().setKey(GameKeys.ESCAPE, false);
        }
        if (k == Input.Keys.SPACE) {
            metaData.getKeys().setKey(GameKeys.SPACE, false);
        }
        if (k == Input.Keys.SHIFT_LEFT || k == Input.Keys.SHIFT_RIGHT) {
            metaData.getKeys().setKey(GameKeys.SHIFT, false);
        }
        return true;
    }

}

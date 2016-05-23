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

    @Override
    public boolean mouseMoved(int x, int y) {
        metaData.setMousePos(x, (metaData.getDisplayHeight() - y));
        return true;
    }

    @Override
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
        if (k == Input.Keys.NUM_1) {
            metaData.getKeys().setKey(GameKeys.NUM_1, true);
        }
        if (k == Input.Keys.NUM_2) {
            metaData.getKeys().setKey(GameKeys.NUM_2, true);
        }
        if (k == Input.Keys.NUM_3) {
            metaData.getKeys().setKey(GameKeys.NUM_3, true);
        }
        return true;
    }

    @Override
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
        if (k == Input.Keys.NUM_1) {
            metaData.getKeys().setKey(GameKeys.NUM_1, false);
        }
        if (k == Input.Keys.NUM_2) {
            metaData.getKeys().setKey(GameKeys.NUM_2, false);
        }
        if (k == Input.Keys.NUM_3) {
            metaData.getKeys().setKey(GameKeys.NUM_3, false);
        }
        return true;
    }

}

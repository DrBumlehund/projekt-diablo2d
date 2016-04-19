/*
 * Copyright (C) 2016 Thomas
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

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.se.sem4.group2.common.data.GameKeys;
import com.se.sem4.group2.common.data.MetaData;
import java.util.ArrayList;

/**
 *
 * @author Thomas
 */
public class GameControllerInputProcessor extends ControllerAdapter {

    private final MetaData metaData;

    public GameControllerInputProcessor(MetaData metaData) {
        this.metaData = metaData;
    }

    /*      ========== GAMEPAD FUNTIONALITY ==========        */
    private ArrayList<Controller> controllers = new ArrayList<>();

    @Override
    public void connected(Controller cntrlr) {
        System.out.println("Controller \"" + cntrlr.getName() + "\" has contected.");
        controllers.add(cntrlr);
    }

    @Override
    public void disconnected(Controller cntrlr) {
        System.out.println("Controller \"" + cntrlr.getName() + "\" has discontected.");
        controllers.remove(cntrlr);
    }

    @Override
    public boolean buttonDown(Controller cntrlr, int i) {
        if (i == XBox360Pad.BUTTON_A) {
            metaData.getKeys().setKey(GameKeys.SPACE, true);
        }
        return true;
    }

    @Override
    public boolean buttonUp(Controller cntrlr, int i) {
        if (i == XBox360Pad.BUTTON_A) {
            metaData.getKeys().setKey(GameKeys.SPACE, false);
        }
        return true;
    }

    @Override
    public boolean axisMoved(Controller cntrlr, int i, float f) {
        if (i == XBox360Pad.AXIS_LEFT_X) {
            if (f < 0) {
                metaData.getKeys().setKey(GameKeys.LEFT, true);
            } else {
                metaData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (f > 0) {
                metaData.getKeys().setKey(GameKeys.RIGHT, true);
            } else {
                metaData.getKeys().setKey(GameKeys.RIGHT, false);
            }
        }
        if (i == XBox360Pad.AXIS_LEFT_Y) {
            if (f < 0) {
                metaData.getKeys().setKey(GameKeys.UP, true);
            } else {
                metaData.getKeys().setKey(GameKeys.UP, false);
            }
            if (f > 0) {
                metaData.getKeys().setKey(GameKeys.DOWN, true);
            } else {
                metaData.getKeys().setKey(GameKeys.DOWN, false);
            }
        }
//        if (i == XBox360Pad.AXIS_RIGHT_X) {
//            if (f < 0) {
//                
//            } else {
//            }
//            if (f > 0) {
//            } else {
//            }
//        }
//        if (i == XBox360Pad.AXIS_RIGHT_Y) {
//            if (f < 0) {
//            } else {
//            }
//            if (f > 0) {
//            } else {
//            }
//        }
        if (i == XBox360Pad.AXIS_RIGHT_TRIGGER) {
            if (f > 0) {
                metaData.getKeys().setKey(GameKeys.SPACE, true);
            } else {
                metaData.getKeys().setKey(GameKeys.SPACE, false);
            }
        }
        if (i == XBox360Pad.AXIS_LEFT_TRIGGER) {
            if (f > 0) {
                metaData.getKeys().setKey(GameKeys.SPACE, true);
            } else {
                metaData.getKeys().setKey(GameKeys.SPACE, false);
            }
        }

        return true;
    }
}

/**
 * Various static XBOX 360 game pad information, Source:
 * http://www.gamefromscratch.com/post/2014/09/22/LibGDX-Tutorial-Part-14-Gamepad-support.aspx
 *
 * @author Thomas
 */
class XBox360Pad {

    public static final int BUTTON_X = 2;
    public static final int BUTTON_Y = 3;
    public static final int BUTTON_A = 0;
    public static final int BUTTON_B = 1;
    public static final int BUTTON_BACK = 6;
    public static final int BUTTON_START = 7;
    public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
    public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
    public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
    public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;
    public static final int BUTTON_LB = 4;
    public static final int BUTTON_L3 = 8;
    public static final int BUTTON_RB = 5;
    public static final int BUTTON_R3 = 9;
    public static final int AXIS_LEFT_X = 1; //-1 is left | +1 is right
    public static final int AXIS_LEFT_Y = 0; //-1 is up | +1 is down
    public static final int AXIS_LEFT_TRIGGER = 4; //value 0 to 1f
    public static final int AXIS_RIGHT_X = 3; //-1 is left | +1 is right
    public static final int AXIS_RIGHT_Y = 2; //-1 is up | +1 is down
    public static final int AXIS_RIGHT_TRIGGER = 4; //value 0 to -1f
}

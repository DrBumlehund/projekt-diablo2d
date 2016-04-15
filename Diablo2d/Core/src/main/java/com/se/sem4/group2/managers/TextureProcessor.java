

/*
 * Copyright (C) 2016 casperbeese
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

import org.openide.util.lookup.ServiceProvider;
import com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService;

/**
 *
 * @author casperbeese
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService.class)
public class TextureProcessor extends AssetProcessor implements IAssetTextureService{

    @Override
    public void draw(String path) {
    }

    
}

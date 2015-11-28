/*
 * Copyright 2011-2015 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * $Id$
 */

package com.shenbian.admin.util.pf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * AvailableThemes
 *
 * @author Oleg Varaksin / last modified by $Author$
 * @version $Revision$
 */
public class AvailableThemes {

    private static AvailableThemes instance = null;

    private final HashMap<String, Theme> themesAsMap;
    private final List<Theme> themes;

    private AvailableThemes() {
        final List<String> themeNames = new ArrayList<String>();

        themeNames.add("ui-lightness");
        themeNames.add("blitzer");
        themeNames.add("bootstrap");
        themeNames.add("cupertino");
        themeNames.add("delta");
        themeNames.add("excite-bike");
        themeNames.add("hot-sneaks");
        themeNames.add("pepper-grinder");
        themeNames.add("redmond");
        themeNames.add("start");

        themesAsMap = new HashMap<String, Theme>();
        themes = new ArrayList<Theme>();

        for (final String themeName : themeNames) {
            final Theme theme = new Theme();
            theme.setName(themeName);
            theme.setImage("/resources/images/themeswitcher/" + themeName
                    + ".png");

            themes.add(theme);
            themesAsMap.put(theme.getName(), theme);
        }
    }

    public static AvailableThemes getInstance() {
        if (instance == null) {
            instance = new AvailableThemes();
        }

        return instance;
    }

    public final List<Theme> getThemes() {
        return themes;
    }

    public Theme getThemeForName(final String name) {
        return themesAsMap.get(name);
    }
}

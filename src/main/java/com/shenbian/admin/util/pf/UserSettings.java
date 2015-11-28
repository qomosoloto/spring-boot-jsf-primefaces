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


import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 * User settings.
 *
 * @author Oleg Varaksin / last modified by $Author$
 * @version $Revision$
 */
@Component(value = "userSettings")
@ManagedBean
@SessionScoped
public class UserSettings implements Serializable {

    private static final long serialVersionUID = 20111020L;

    private List<Theme> availableThemes;
    private Theme currentTheme;

    public UserSettings() {
        currentTheme = AvailableThemes.getInstance().getThemeForName("delta");
        availableThemes = AvailableThemes.getInstance().getThemes();
    }

    public final List<Theme> getAvailableThemes() {
        return availableThemes;
    }

    public final void setAvailableThemes(List<Theme> availableThemes) {
        this.availableThemes = availableThemes;
    }

    public final Theme getCurrentTheme() {
        return currentTheme;
    }

    public final void setCurrentTheme(Theme currentTheme) {
        this.currentTheme = currentTheme;
    }
}

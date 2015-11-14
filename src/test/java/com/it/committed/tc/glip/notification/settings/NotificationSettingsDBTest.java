package com.it.committed.tc.glip.notification.settings;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Let's check that we are able to read deafult.properties from the JAR
 *
 * Created by den.konakov@it-committed.com on 2015-11-12.
 */
public class NotificationSettingsDBTest {
    @Test
    public void loadingDefaults() {
        NotificationSettingsDB settingsDB = new NotificationSettingsDB();
        settingsDB.init();

        assertTrue(settingsDB.getDefaultSettings() != null);
        assertTrue(settingsDB.getDefaultSettings().getApiUrl() != null);
    }
}

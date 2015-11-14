package com.it.committed.tc.glip.notification.settings;

import com.google.code.objectprops.ObjectPropertiesStore;
import com.it.committed.tc.glip.log.LoggerWrapper;
import com.it.committed.tc.glip.view.AdminPageExtension;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Properties;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * Central storage which is responsible for the writing/reading settings into/from
 * properties.
 * <p>
 * Created by den.konakov@it-committed.com on 2015-11-11.
 */
public class NotificationSettingsDB {

    public static final String DEFAULT_PROPS = "com/it/committed/tc/glip/notification/default.properties";
    public static final String STORAGE_PROPS = "settings.properties";
    public static final String STORAGE_PATH = "notification.settings";

    private static final LoggerWrapper Log = LoggerWrapper.create(Loggers.SERVER, NotificationSettingsDB.class.getName());

    private ObjectPropertiesStore storage;
    private NotificationSettings defaultSettings;
    private String fullStorageFilePath;

    @Autowired
    private PluginDescriptor descriptor;

    public void init() {
        Log.info("Initializing the default properties ...");
        Log.info("Using the PluginDescriptor '%s'", (descriptor != null ? descriptor : "NULL"));

        Properties defaultProperties = new Properties();
        try {
            defaultProperties.load(getClass().getClassLoader().getResourceAsStream(DEFAULT_PROPS));
        } catch (IOException e) {
            Log.error("We were not able to read default properties storage.", e);
        }
        ObjectPropertiesStore defaultStore = new ObjectPropertiesStore(defaultProperties);
        defaultSettings = (NotificationSettings) defaultStore.readObject(STORAGE_PATH, NotificationSettings.class);

        Log.info("Probing for the storage properties using path '%s' ...", getFullStorageFilePath());
        File storageFile = new File(getFullStorageFilePath());
        if (!storageFile.exists()) {
            Log.info("The storage file does not exist. Probing for folder ...");
            File storageFolder = new File(getFullStorageFolderPath());
            if (!storageFolder.exists()) {
                Log.info("Folder '%s' does not exist. Creating ...", storageFolder.getAbsolutePath());
                storageFolder.mkdir();
            }

            try {
                storageFile.createNewFile();
            } catch (IOException e) {
                Log.error("We were not able to create a new storage file!", e);
            }

            Log.info("Storage file has been created. Proceed further ...");
        }

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(storageFile));
        } catch (IOException e) {
            Log.error("We were not able to read properties storage.", e);
        }
        storage = new ObjectPropertiesStore(properties);
    }

    public void saveSettings(NotificationSettings settings) {
        NotificationSettings stored = (NotificationSettings) storage.readObject(NotificationSettings.class);
        if (stored != null) {
            stored.copyFrom(settings);
            storage.writeObject(STORAGE_PATH, stored);
        } else {
            storage.writeObject(STORAGE_PATH, settings);
        }

        OutputStream out = null;
        try {
            out = new FileOutputStream(getFullStorageFilePath());
            storage.getDatabase().store(out, "Settings for the Glip Notificator");
        } catch (FileNotFoundException e) {
            Log.error("We were not able to create OutputStream", e);
        } catch (IOException e) {
            Log.error("We were not able to write to OutputStream", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.error("We were not able to close OutputStream", e);
                }
            }
        }
    }

    private String getFullStorageFolderPath() {
        return descriptor.getPluginRoot().getAbsolutePath().substring(0,
                descriptor.getPluginRoot().getAbsolutePath().indexOf(".unpacked")) + AdminPageExtension.PLUGIN_NAME;
    }

    private String getFullStorageFilePath() {
        if (fullStorageFilePath == null) {
            fullStorageFilePath = getFullStorageFolderPath() + "/" + STORAGE_PROPS;
        }

        return fullStorageFilePath;
    }

    public NotificationSettings readSettings() {
        return readSettings(getDefaultSettings());
    }

    public NotificationSettings readSettings(NotificationSettings defaultSettings) {
        NotificationSettings stored = (NotificationSettings) storage.readObject(STORAGE_PATH, NotificationSettings.class);
        if (stored != null) {
            return stored;
        }

        return defaultSettings;
    }

    public NotificationSettings getDefaultSettings() {
        return defaultSettings;
    }
}

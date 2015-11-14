package com.it.committed.tc.glip.view;

import com.google.common.collect.Lists;
import com.it.committed.tc.glip.log.LoggerWrapper;
import com.it.committed.tc.glip.notification.settings.NotificationSettings;
import com.it.committed.tc.glip.notification.settings.NotificationSettingsDB;
import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * Created by den.konakov@it-committed.com on 2015-10-26.
 */
public class AdminPageExtension extends AdminPage {

    public static final String PLUGIN_NAME = "GlipNotificator";

    private static final LoggerWrapper Log = LoggerWrapper.create(Loggers.SERVER, AdminPageExtension.class.getName());
    private static final String AFTER_PAGE_ID = "jabber";
    private static final String BEFORE_PAGE_ID = "clouds";
    private static final String PAGE = "adminSettings.jsp";

    @Autowired
    NotificationSettingsDB storageDB;

    protected AdminPageExtension(@NotNull PagePlaces pagePlaces) {
        super(pagePlaces, PLUGIN_NAME, PAGE, PLUGIN_NAME);

        setPosition(PositionConstraint.between(Lists.newArrayList(AFTER_PAGE_ID), Lists.newArrayList(BEFORE_PAGE_ID)));
        register();
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
        super.fillModel(model, request);

        NotificationSettings settings = storageDB.readSettings(null);
        NotificationSettings defaults = storageDB.getDefaultSettings();

        Log.debug("Filling the admin page using controller '%s' and settings '%s'", storageDB, (settings != null ? settings : "NULL"));
        model.put(NotificationSettings.ENABLED_MODEL_KEY, settings != null ? settings.isEnabled() : defaults.isEnabled());
        model.put(NotificationSettings.APIURL_MODEL_KEY, settings != null ? settings.getApiUrl() : "");

        // Filling the templates
        model.put(NotificationSettings.BUILD_STARTED_MODEL_KEY, settings != null ? settings.getBuildStarted() : "");
        model.put(NotificationSettings.DEFAULT_MODEL_PREFIX + NotificationSettings.BUILD_STARTED_MODEL_KEY, defaults.getBuildStarted());

        model.put(NotificationSettings.BUILD_SUCESS_MODEL_KEY, settings != null ? settings.getBuildSuccess() : "");
        model.put(NotificationSettings.DEFAULT_MODEL_PREFIX + NotificationSettings.BUILD_SUCESS_MODEL_KEY, defaults.getBuildSuccess());

        model.put(NotificationSettings.BUILD_FAILS_MODEL_KEY, settings != null ? settings.getBuildFails() : "");
        model.put(NotificationSettings.DEFAULT_MODEL_PREFIX + NotificationSettings.BUILD_FAILS_MODEL_KEY, defaults.getBuildFails());

        model.put(NotificationSettings.BUILD_FAILSTRT_MODEL_KEY, settings != null ? settings.getBuildFailstrt() : "");
        model.put(NotificationSettings.DEFAULT_MODEL_PREFIX + NotificationSettings.BUILD_FAILSTRT_MODEL_KEY, defaults.getBuildFailstrt());

        model.put(NotificationSettings.BUILD_FIRST_ERROR_MODEL_KEY, settings != null ? settings.getBuildFirsterror() : "");
        model.put(NotificationSettings.DEFAULT_MODEL_PREFIX + NotificationSettings.BUILD_FIRST_ERROR_MODEL_KEY, defaults.getBuildFirsterror());

        model.put(NotificationSettings.BUILD_HANGING_MODEL_KEY, settings != null ? settings.getBuildHanging() : "");
        model.put(NotificationSettings.DEFAULT_MODEL_PREFIX + NotificationSettings.BUILD_HANGING_MODEL_KEY, defaults.getBuildHanging());

        model.put(NotificationSettings.SERVER_STARTUP_MODEL_KEY, settings != null ? settings.getServerStartup() : "");
        model.put(NotificationSettings.DEFAULT_MODEL_PREFIX + NotificationSettings.SERVER_STARTUP_MODEL_KEY, defaults.getServerStartup());

        model.put(NotificationSettings.SERVER_SHUTDOWN_MODEL_KEY, settings != null ? settings.getServerShutdown() : "");
        model.put(NotificationSettings.DEFAULT_MODEL_PREFIX + NotificationSettings.SERVER_SHUTDOWN_MODEL_KEY, defaults.getServerShutdown());
    }

    @NotNull
    @Override
    public String getGroup() {
        return SERVER_RELATED_GROUP;
    }
}

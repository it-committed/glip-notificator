package com.it.committed.tc.glip.view;

import com.it.committed.tc.glip.log.LoggerWrapper;
import com.it.committed.tc.glip.notification.settings.NotificationSettings;
import com.it.committed.tc.glip.notification.settings.NotificationSettingsDB;
import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * Controller which handles the Admin page and stores the settings.
 * <p>
 * Created by den.konakov@it-committed.com on 2015-10-28.
 */
public class AdminPageController extends BaseController {

    private static final LoggerWrapper Log = LoggerWrapper.create(Loggers.SERVER, AdminPageController.class.getName());

    private static final String ACTION_PARAMETER = "action";
    private static final String CONTROLLER_PATH = "/configureGlipNotificator.html";

    @Autowired
    NotificationSettingsDB storageDB;

    public AdminPageController(@NotNull WebControllerManager manager) {
        manager.registerController(CONTROLLER_PATH, this);
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (request.getParameter(ACTION_PARAMETER) != null) {
            Log.debug("Saving the enabled/disabled state");
            enableOrDisablePlugin(request);
        } else { // Usual save
            Log.debug("Saving the whole object");
            retrieveAndSaveSettings(request);
        }

        return null;
    }

    private void retrieveAndSaveSettings(HttpServletRequest request) {
        NotificationSettings current = storageDB.readSettings();
        NotificationSettings settings = new NotificationSettings();
        settings.setEnabled(current.isEnabled());

        // Fill everything except the !isEnabled!
        settings.setApiUrl(readParameter(request, NotificationSettings.APIURL_MODEL_KEY, current.getApiUrl()));
        settings.setBuildStarted(readParameter(request, NotificationSettings.BUILD_STARTED_MODEL_KEY, current.getBuildStarted()));
        settings.setBuildSuccess(readParameter(request, NotificationSettings.BUILD_SUCESS_MODEL_KEY, current.getBuildSuccess()));
        settings.setBuildFails(readParameter(request, NotificationSettings.BUILD_FAILS_MODEL_KEY, current.getBuildFails()));
        settings.setBuildFailstrt(readParameter(request, NotificationSettings.BUILD_FAILSTRT_MODEL_KEY, current.getBuildFailstrt()));
        settings.setBuildFirsterror(readParameter(request, NotificationSettings.BUILD_FIRST_ERROR_MODEL_KEY, current.getBuildFirsterror()));
        settings.setBuildHanging(readParameter(request, NotificationSettings.BUILD_HANGING_MODEL_KEY, current.getBuildHanging()));
        settings.setServerStartup(readParameter(request, NotificationSettings.SERVER_STARTUP_MODEL_KEY, current.getServerStartup()));
        settings.setServerShutdown(readParameter(request, NotificationSettings.SERVER_SHUTDOWN_MODEL_KEY, current.getServerShutdown()));

        // Save it!
        storageDB.saveSettings(settings);
    }

    private String readParameter(HttpServletRequest request, String modelKey, String defaultValue) {
        String result;

        if (request.getParameter(modelKeyToParam(modelKey)) != null) {
            result = request.getParameter(modelKeyToParam(modelKey));
        } else {
            result = defaultValue;
        }

        Log.debug("Reading from request using the model key '%s' and defaultValue '%s', request key was '%s' and the result '%s'",
                modelKey, (defaultValue != null ? defaultValue : "NULL"), modelKeyToParam(modelKey), result);
        return result;
    }

    private String modelKeyToParam(String modelKey) {
        if (modelKey.contains("_")) {
            return modelKey.substring(modelKey.indexOf('_') + 1);
        }

        return null; // Let it crash?
    }

    private void enableOrDisablePlugin(HttpServletRequest request) {
        NotificationSettings settings = new NotificationSettings();
        settings.setEnabled("enable".equalsIgnoreCase(request.getParameter(ACTION_PARAMETER)));
        storageDB.saveSettings(settings);
    }
}

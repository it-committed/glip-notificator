package com.it.committed.tc.glip.notification;

import com.it.committed.tc.glip.log.LoggerWrapper;
import com.it.committed.tc.glip.notification.template.NotificationHttpSender;
import com.it.committed.tc.glip.notification.template.NotificationTemplate;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * Suppose to catch the events about server startup/shutdown. Doesn't work for now.
 * <p>
 * //FIXME: make it work
 * <p>
 * Created by den.konakov@it-committed.com on 2015-10-28.
 */
public class ServerExtension extends BuildServerAdapter {

    private static final LoggerWrapper Log = LoggerWrapper.create(Loggers.SERVER, ServerExtension.class.getName());

    @Autowired
    NotificationTemplate templateFactory;

    public ServerExtension() {
    }

    @Override
    public void serverStartup() {
        Log.info("Server is starting up!");
        sendNotification(NotificationTemplate.TEMPLATE_TYPES.SERVER_STARTED);
    }

    @Override
    public void serverShutdown() {
        Log.info("Server is shutting down!");
        sendNotification(NotificationTemplate.TEMPLATE_TYPES.SERVER_SHUTDOWN);
    }

    private void sendNotification(NotificationTemplate.TEMPLATE_TYPES templateType) {
        Log.info("Sending notification about '%s' using the settings '%s'", templateType, templateFactory.getCurrentSettings());
        if (templateFactory.getCurrentSettings().isEnabled()) {
            try {
                NotificationHttpSender.sendPost(templateFactory.getCurrentSettings().getApiUrl(),
                        templateFactory.createTemplate(null, templateType).toString());
            } catch (Exception e) {
                Log.error("There was some error while we were sending the Notification", e);
            }
        }
    }
}

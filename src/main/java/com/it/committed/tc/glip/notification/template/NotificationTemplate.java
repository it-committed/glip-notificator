package com.it.committed.tc.glip.notification.template;

import com.it.committed.tc.glip.log.LoggerWrapper;
import com.it.committed.tc.glip.notification.settings.NotificationSettings;
import com.it.committed.tc.glip.notification.settings.NotificationSettingsDB;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.SRunningBuild;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
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
 * Just a container for the Template types and template parsing, creating, etc..
 * <p>
 * Created by den.konakov@it-committed.com on 2015-10-18.
 */
public class NotificationTemplate {
    private static final LoggerWrapper Log = LoggerWrapper.create(Loggers.SERVER, NotificationTemplate.class.getName());

    NotificationSettingsDB storageDB;

    @Autowired
    public NotificationTemplate(NotificationSettingsDB storageDB) {
        this.storageDB = storageDB;
    }

    public String createTemplate(SRunningBuild build, TEMPLATE_TYPES templateType) {
        String result = null;
        try {
            String templateStr = null;
            switch (templateType) {
                case BUILD_STARTED:
                    templateStr = getCurrentSettings().getBuildStarted();
                    break;

                case BUILD_SUCCESSFUL:
                    templateStr = getCurrentSettings().getBuildSuccess();
                    break;

                case BUILD_FAILED:
                    templateStr = getCurrentSettings().getBuildFails();
                    break;

                case BUILD_FAILED_TO_START:
                    templateStr = getCurrentSettings().getBuildFailstrt();
                    break;

                case BUILD_FAILING:
                    templateStr = getCurrentSettings().getBuildFirsterror();
                    break;

                case BUILD_HANGING:
                    templateStr = getCurrentSettings().getBuildHanging();
                    break;

                case SERVER_STARTED:
                    templateStr = getCurrentSettings().getServerStartup();
                    break;

                case SERVER_SHUTDOWN:
                    templateStr = getCurrentSettings().getServerShutdown();
                    break;
            }

            if (templateStr != null && !templateStr.isEmpty()) {
                Template template = new Template(templateType.getTemplateName(),
                        new StringReader(templateStr), new Configuration());
                return processTemplate(template, build != null ? buildObjecToModel(build) : null);
            }
        } catch (IOException e) {
            Log.error("We were unable to parse template from the string!", e);
        }

        return result;
    }

    public NotificationSettings getCurrentSettings() {
        return storageDB.readSettings();
    }

    private Map<String, String> buildObjecToModel(SRunningBuild build) {
        Map<String, String> result = new HashMap<>();

        result.put(TemplateModel.MODEL_VARS.BRANCH.getVarName(), build.getBranch().getDisplayName());
        result.put(TemplateModel.MODEL_VARS.BUILD_ID.getVarName(), build.getBuildNumber());
        result.put(TemplateModel.MODEL_VARS.BUILD_NUM.getVarName(), build.getBuildNumber());
        result.put(TemplateModel.MODEL_VARS.COMPLETED.getVarName(), String.valueOf(build.getCompletedPercent()));
        result.put(TemplateModel.MODEL_VARS.DURATION.getVarName(), String.valueOf(build.getDuration()));
        result.put(TemplateModel.MODEL_VARS.CONFIGURATION.getVarName(), build.getFullName());
        result.put(TemplateModel.MODEL_VARS.TRIGGERED.getVarName(), build.getTriggeredBy().getAsString());

        return result;
    }

    private String processTemplate(Template template, Map<?, ?> model) {
        StringWriter writer = new StringWriter();
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            Log.error("There was an Template error. Please check!", e);
        } catch (IOException e) {
            Log.error("We were not able to write to String.", e);
        }

        writer.flush();
        return writer.getBuffer().toString();
    }

    public enum TEMPLATE_TYPES {
        BUILD_STARTED("Build Started"), BUILD_HANGING("Build Hanging"), BUILD_FAILING("Build Failing - first error"),
        BUILD_SUCCESSFUL("Build Successful"), BUILD_FAILED("Build Failed"), BUILD_FAILED_TO_START("Build Failed To Start"),
        SERVER_STARTED("Server Started"), SERVER_SHUTDOWN("Server was Shutdown");

        private String templateName;

        TEMPLATE_TYPES(String templateName) {
            this.templateName = templateName;
        }

        public String getTemplateName() {
            return templateName;
        }
    }
}

package com.it.committed.tc.glip.notification;

import com.google.common.collect.Lists;
import com.it.committed.tc.glip.log.LoggerWrapper;
import com.it.committed.tc.glip.notification.template.NotificationHttpSender;
import com.it.committed.tc.glip.notification.template.NotificationTemplate;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.notification.NotificatorRegistry;
import jetbrains.buildServer.responsibility.ResponsibilityEntry;
import jetbrains.buildServer.responsibility.TestNameResponsibilityEntry;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.STest;
import jetbrains.buildServer.serverSide.mute.MuteInfo;
import jetbrains.buildServer.serverSide.problems.BuildProblemInfo;
import jetbrains.buildServer.tests.TestName;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.vcs.VcsRoot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * The main Notificator class which takes care of UserUI/Properties and actual notification send
 * To the Glip URL
 * <p>
 * Created by den.konakov@it-committed.com on 2015-10-08.
 */
public class Notificator implements jetbrains.buildServer.notification.Notificator {

    private static final LoggerWrapper Log = LoggerWrapper.create(Loggers.SERVER, Notificator.class.getName());
    private static final String TYPE = "GlipNotificator";

    @Autowired
    NotificationTemplate templateFactory;

    public Notificator(NotificatorRegistry notificatorRegistry) throws IOException {
        notificatorRegistry.register(this, Lists.newArrayList());
    }

    @Override
    public void notifyBuildStarted(SRunningBuild build, Set<SUser> users) {
        sendNotification(build, NotificationTemplate.TEMPLATE_TYPES.BUILD_STARTED);
    }

    @Override
    public void notifyBuildSuccessful(SRunningBuild build, Set<SUser> users) {
        sendNotification(build, NotificationTemplate.TEMPLATE_TYPES.BUILD_SUCCESSFUL);
    }

    @Override
    public void notifyBuildFailed(SRunningBuild build, Set<SUser> users) {
        sendNotification(build, NotificationTemplate.TEMPLATE_TYPES.BUILD_FAILED);
    }

    @Override
    public void notifyBuildFailedToStart(SRunningBuild build, Set<SUser> users) {
        sendNotification(build, NotificationTemplate.TEMPLATE_TYPES.BUILD_FAILED_TO_START);
    }

    @Override
    public void notifyBuildFailing(SRunningBuild build, Set<SUser> users) {
        sendNotification(build, NotificationTemplate.TEMPLATE_TYPES.BUILD_FAILING);
    }

    @Override
    public void notifyBuildProbablyHanging(SRunningBuild build, Set<SUser> users) {
        sendNotification(build, NotificationTemplate.TEMPLATE_TYPES.BUILD_HANGING);
    }

    // TODO: do it later
    @Override
    public void notifyLabelingFailed(@NotNull Build build, @NotNull VcsRoot root, @NotNull Throwable exception, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyResponsibleChanged(@NotNull SBuildType buildType, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyResponsibleAssigned(@NotNull SBuildType buildType, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyResponsibleChanged(@Nullable TestNameResponsibilityEntry oldValue, @NotNull TestNameResponsibilityEntry newValue, @NotNull SProject project, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyResponsibleAssigned(@Nullable TestNameResponsibilityEntry oldValue, @NotNull TestNameResponsibilityEntry newValue, @NotNull SProject project, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyResponsibleChanged(@NotNull Collection<TestName> testNames, @NotNull ResponsibilityEntry entry, @NotNull SProject project, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyResponsibleAssigned(@NotNull Collection<TestName> testNames, @NotNull ResponsibilityEntry entry, @NotNull SProject project, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyBuildProblemResponsibleAssigned(@NotNull Collection<BuildProblemInfo> buildProblems, @NotNull ResponsibilityEntry entry, @NotNull SProject project, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyBuildProblemResponsibleChanged(@NotNull Collection<BuildProblemInfo> buildProblems, @NotNull ResponsibilityEntry entry, @NotNull SProject project, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyTestsMuted(@NotNull Collection<STest> tests, @NotNull MuteInfo muteInfo, @NotNull Set<SUser> users) {
    }

    @Override
    public void notifyTestsUnmuted(@NotNull Collection<STest> tests, @NotNull MuteInfo muteInfo, @Nullable SUser user, @NotNull Set<SUser> users) {
    }

    @Override
    public void notifyBuildProblemsMuted(@NotNull Collection<BuildProblemInfo> buildProblems, @NotNull MuteInfo muteInfo, @NotNull Set<SUser> users) {

    }

    @Override
    public void notifyBuildProblemsUnmuted(@NotNull Collection<BuildProblemInfo> buildProblems, @NotNull MuteInfo muteInfo, @Nullable SUser user, @NotNull Set<SUser> users) {

    }

    private void sendNotification(SRunningBuild build, NotificationTemplate.TEMPLATE_TYPES templateType) {
        Log.info("Sending notification about '%s' using the settings '%s'", templateType, templateFactory.getCurrentSettings());
        if (templateFactory.getCurrentSettings().isEnabled()) {
            try {
                NotificationHttpSender.sendPost(templateFactory.getCurrentSettings().getApiUrl(),
                        templateFactory.createTemplate(build, templateType).toString());
            } catch (Exception e) {
                Log.error("There was some error while we were sending the Notification", e);
            }
        }
    }

    @NotNull
    @Override
    public String getNotificatorType() {
        return TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return TYPE;
    }

}

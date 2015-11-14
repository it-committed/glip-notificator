package com.it.committed.tc.glip.notification.template;

import com.it.committed.tc.glip.notification.settings.NotificationSettingsDB;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.SRunningBuild;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Should use default.properties with NotificationHttpSender to test
 * full cycle of  Notification creation and sending
 * <p>
 * Created by den.konakov@it-committed.com on 2015-11-12.
 */
public class TemplateFunctionalTest {

    private NotificationTemplate template;
    private SRunningBuild runningBuild;

    @Before
    public void setUp() {
        NotificationSettingsDB realOnes = new NotificationSettingsDB();
        realOnes.init();

        NotificationSettingsDB spied = PowerMockito.spy(realOnes);
        PowerMockito.when(spied.readSettings()).thenReturn(realOnes.getDefaultSettings());

        template = new NotificationTemplate(spied);

        Branch branch = PowerMockito.mock(Branch.class);
        PowerMockito.when(branch.getDisplayName()).thenReturn("testBranch");

        runningBuild = PowerMockito.mock(SRunningBuild.class);
        PowerMockito.when(runningBuild.getBranch()).thenReturn(branch);
        PowerMockito.when(runningBuild.getBuildId()).thenReturn(111L);
        PowerMockito.when(runningBuild.getBuildNumber()).thenReturn("333");
    }

    @After
    public void tearDown() {
        template = null;
        runningBuild = null;
    }

    private String prepareTemplate(String string) {
        return string.replaceAll("'", "\""); // This should be done by JavaScprint on the View side
    }

    @Test
    public void buildStarted() {
        String tmplt = template.createTemplate(runningBuild, NotificationTemplate.TEMPLATE_TYPES.BUILD_STARTED);
        assertTrue("Template not be null", tmplt != null);

        try {
            assertEquals("Answer from Glip should be proper!", 200,
                    NotificationHttpSender.sendPost(template.getCurrentSettings().getApiUrl(), prepareTemplate(tmplt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buildSuccess() {
        String tmplt = template.createTemplate(runningBuild, NotificationTemplate.TEMPLATE_TYPES.BUILD_SUCCESSFUL);
        assertTrue("Template not be null", tmplt != null);

        try {
            assertEquals("Answer from Glip should be proper!", 200,
                    NotificationHttpSender.sendPost(template.getCurrentSettings().getApiUrl(), prepareTemplate(tmplt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buildFailed() {
        String tmplt = template.createTemplate(runningBuild, NotificationTemplate.TEMPLATE_TYPES.BUILD_FAILED);
        assertTrue("Template not be null", tmplt != null);

        try {
            assertEquals("Answer from Glip should be proper!", 200,
                    NotificationHttpSender.sendPost(template.getCurrentSettings().getApiUrl(), prepareTemplate(tmplt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buildShutdown() { // Should not send anything since now it is not used and empty !!!
        String tmplt = template.createTemplate(runningBuild, NotificationTemplate.TEMPLATE_TYPES.SERVER_SHUTDOWN);
        assertTrue("Template not be null", tmplt == null);
    }
}

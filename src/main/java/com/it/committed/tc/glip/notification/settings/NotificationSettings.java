package com.it.committed.tc.glip.notification.settings;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * Just a POJO which represents the saved settings for build Notification
 * <p>
 * Created by den.konakov@it-committed.com on 2015-10-30.
 */
public class NotificationSettings {

    public static final String DEFAULT_MODEL_PREFIX = "default_";

    public static final String ENABLED_MODEL_KEY = "mod_enabled";
    public static final String APIURL_MODEL_KEY = "mod_apiurl";
    public static final String TC_BASE_URL_MODEL_KEY = "mod_tcbaseurl";

    public static final String BUILD_STARTED_MODEL_KEY = "mod_buildstarted";
    public static final String BUILD_SUCESS_MODEL_KEY = "mod_buildsuccess";
    public static final String BUILD_FAILS_MODEL_KEY = "mod_buildfails";
    public static final String BUILD_FAILSTRT_MODEL_KEY = "mod_buildfailstrt";
    public static final String BUILD_FIRST_ERROR_MODEL_KEY = "mod_buildfirsterror";
    public static final String BUILD_HANGING_MODEL_KEY = "mod_buildhanging";
    public static final String SERVER_STARTUP_MODEL_KEY = "mod_serverstartup";
    public static final String SERVER_SHUTDOWN_MODEL_KEY = "mod_servershutdown";

    private Boolean enabled;
    private String apiUrl;
    private String tcBaseUrl;
    private String buildStarted;
    private String buildSuccess;
    private String buildFails;
    private String buildFailstrt;
    private String buildFirsterror;
    private String buildHanging;
    private String serverStartup;
    private String serverShutdown;

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getTcBaseUrl() {
        return tcBaseUrl;
    }

    public void setTcBaseUrl(String tcBaseUrl) {
        this.tcBaseUrl = tcBaseUrl;
    }

    public String getBuildStarted() {
        return buildStarted;
    }

    public void setBuildStarted(String buildStarted) {
        this.buildStarted = buildStarted;
    }

    public String getBuildSuccess() {
        return buildSuccess;
    }

    public void setBuildSuccess(String buildSuccess) {
        this.buildSuccess = buildSuccess;
    }

    public String getBuildFails() {
        return buildFails;
    }

    public void setBuildFails(String buildFails) {
        this.buildFails = buildFails;
    }

    public String getBuildFailstrt() {
        return buildFailstrt;
    }

    public void setBuildFailstrt(String buildFailstrt) {
        this.buildFailstrt = buildFailstrt;
    }

    public String getBuildFirsterror() {
        return buildFirsterror;
    }

    public void setBuildFirsterror(String buildFirsterror) {
        this.buildFirsterror = buildFirsterror;
    }

    public String getBuildHanging() {
        return buildHanging;
    }

    public void setBuildHanging(String buildHanging) {
        this.buildHanging = buildHanging;
    }

    public String getServerStartup() {
        return serverStartup;
    }

    public void setServerStartup(String serverStartup) {
        this.serverStartup = serverStartup;
    }

    public String getServerShutdown() {
        return serverShutdown;
    }

    public void setServerShutdown(String serverShutdown) {
        this.serverShutdown = serverShutdown;
    }

    public void copyFrom(NotificationSettings settings) {
        setEnabled(settings.isEnabled());
        setApiUrl(settings.getApiUrl());
        setBuildStarted(settings.getBuildStarted());
        setBuildSuccess(settings.getBuildSuccess());
        setBuildFails(settings.getBuildFails());
        setBuildFailstrt(settings.getBuildFailstrt());
        setBuildFirsterror(settings.getBuildFirsterror());
        setBuildHanging(settings.getBuildHanging());
        setServerStartup(settings.getServerStartup());
        setServerShutdown(settings.getServerShutdown());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[Settings: {enabled=").append(getFieldValueSafely(enabled));
        sb.append(", apiUrl=").append(getApiUrl());
        sb.append(", tcBaseUrl=").append(getTcBaseUrl());
        sb.append(", buildStarted=").append(getBuildStarted() != null ? "SET" : "UNSET");
        sb.append(", buildSuccess=").append(getBuildSuccess() != null ? "SET" : "UNSET");
        sb.append(", buildFails=").append(getBuildFails() != null ? "SET" : "UNSET");
        sb.append(", buildFailstrt=").append(getBuildFailstrt() != null ? "SET" : "UNSET");
        sb.append(", buildFirsterror=").append(getBuildFirsterror() != null ? "SET" : "UNSET");
        sb.append(", buildHanging=").append(getBuildHanging() != null ? "SET" : "UNSET");
        sb.append(", serverStartup=").append(getServerStartup() != null ? "SET" : "UNSET");
        sb.append(", serverShutdown=").append(getServerShutdown() != null ? "SET" : "UNSET");
        sb.append("}]");
        return sb.toString();
    }

    private String getFieldValueSafely(Object value) {
        return value == null ? "NULL" : value.toString();
    }
}

package com.it.committed.tc.glip.notification.template;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * The only purpose of this class - contain the MODEL_VARS enum.
 * For the documentation sake - to be a little bit easier to read.
 * <p>
 * Created by den.konakov@it-committed.com on 2015-11-12.
 */
public class TemplateModel {
    public enum MODEL_VARS {
        BRANCH("branch"), // Branch display name
        BUILD_ID("build_id"), // Build id
        BUILD_NUM("build_num"), // Build number
        COMPLETED("completed"), // Completed %
        DURATION("duration"), // Current duration of the build
        CONFIGURATION("configuration"), // has form "project_name :: buildconfig_name"
        TC_BASE_URL("tc_base_url"), // TeamCity base url from settings
        BUILD_URL("build_url"), // Url to the TeamCity build
        TRIGGERED("triggered"); // TriggredBy information

        private String variableName;

        MODEL_VARS(String variableName) {
            this.variableName = variableName;
        }

        public String getVarName() {
            return variableName;
        }
    }
}

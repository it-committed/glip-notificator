package com.it.committed.tc.glip.notification.template;

import com.it.committed.tc.glip.log.LoggerWrapper;
import jetbrains.buildServer.log.Loggers;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * Just simple HTTP sender using POST.
 * <p>
 * Created by den.konakov@it-committed.com on 2015-11-12.
 */
public class NotificationHttpSender {

    private static final LoggerWrapper Log = LoggerWrapper.create(Loggers.SERVER, NotificationHttpSender.class.getName());

    // HTTP POST request
    public static int sendPost(String url, String template) throws Exception {
        if (template == null) {
            return 0;
        }

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Content-Type", "application/json");

        con.setRequestProperty("Content-Length", Integer.toString(template.getBytes().length));
        con.setRequestProperty("Content-Language", "en-US");

        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(template);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        Log.info("Sending 'POST' request to URL : " + url);
        Log.info("Post length : " + template.getBytes().length);
        Log.info("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return responseCode;
    }
}

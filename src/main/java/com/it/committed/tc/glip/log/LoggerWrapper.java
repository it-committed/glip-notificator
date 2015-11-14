package com.it.committed.tc.glip.log;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * Since I was not able to make this work:
 * <p>
 * Logger.getInstance(Class<?>.getCanonicalName());
 * <p>
 * I created this simple wrapper for convenience.
 * <p>
 * Created by den.konakov@it-committed.com on 2015-11-06.
 */
public class LoggerWrapper {

    private final String HEAD = "[%s] - ";

    private Logger logger;
    private String className;

    private LoggerWrapper(Logger toWrap, String forClassName) {
        this.logger = toWrap;
        this.className = forClassName;
    }

    public static LoggerWrapper create(Logger toWrap, String forClassName) {
        return new LoggerWrapper(toWrap, forClassName);
    }

    public void info(String message, Object... params) {
        logger.info(String.format(HEAD + message, getStrings(params).toArray()));
    }

    @NotNull
    private List<String> getStrings(Object[] params) {
        List<String> strings = new LinkedList<>();
        strings.add(className);
        for (Object param : params) {
            strings.add(param.toString());
        }
        return strings;
    }

    public void error(String message, Throwable e) {
        logger.error(String.format(HEAD + message, className), e);
    }

    public void debug(String message, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format(HEAD + message, getStrings(params).toArray()));
        }
    }
}

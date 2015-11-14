<%@ include file="/include.jsp" %>

<c:url value="/configureGlipNotificator.html" var="actionUrl"/>

<bs:linkCSS dynamic="${true}">
    ${teamcityPluginResourcesPath}css/glipNotificator.css
</bs:linkCSS>

<form action="${actionUrl}" id="GlipSettingsForm" method="POST">
    <div class="editNotificatorSettingsPage">
        <c:choose>
            <c:when test="${mod_enabled}">
                <div style="margin-left: 0.6em;">
                    The notifier is <strong>enabled</strong>. All Glip notifications are posted via WebHooks.
                    &nbsp;&nbsp;<a class="btn btn_mini" href="#" id="disable-btn">Disable</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="pauseNote" style="margin-bottom: 1em;">
                    The notifier is <strong>disabled</strong>. All Glipnotifications are suspended.
                    &nbsp;&nbsp;<a class="btn btn_mini" href="#" id="enable-btn">Enable</a>
                </div>
            </c:otherwise>
        </c:choose>

        <bs:messages key="configurationSaved"/>
        <br>
        <table class="runnerFormTable">
            <%--<tr class="groupingTitle">--%>
            <td colspan="2">Connection Configuration&nbsp;</td>
            </tr>
            <tr>
                <th>
                    <label for="apiUrl">Glip WebHook URL: </label>
                </th>
                <td>
                    <forms:textField name="apiurl" id="apiurl" value="${mod_apiurl}" style="width: 300px;"/>
                    <span style="color: #888; font-size: 90%;"> URL provided by Glip WebHook integration.</span>
                </td>
            </tr>
            <tr>
                <td colspan="2"><forms:submit id="testConnection" type="button" label="Test connection"/></td>
            </tr>
            <tr class="groupingTitle">
                <td colspan="2">JSON Payload Configuration&nbsp;<a
                        href="???"
                        class="helpIcon" style="vertical-align: middle;" target="_blank"><bs:helpIcon/></a></td>
            </tr>
            <tr>
                <th>
                    <label for="buildstarted">Build starts: </label>
                </th>
                <td>
                    <textarea id="buildstarted" name="buildstarted"
                              style="width: 92%;height: 150px;">${mod_buildstarted}</textarea>
                    <a style="vertical-align: top;" href="#" id="default_buildstarted_link">Default</a>
                    <input type="hidden" id="default_buildstarted" value="${default_mod_buildstarted}"/><br>
                    <forms:submit id="test_buildstarted" type="button" label="Test"/><br>
                </td>
            </tr>
            <tr>
                <th><label for="buildsuccess">Build is successful: </label></th>
                <td>
                    <textarea id="buildsuccess" name="buildsuccess"
                              style="width: 92%;height: 150px;">${mod_buildsuccess}</textarea>
                    <a style="vertical-align: top;" href="#" id="default_buildsuccess_link">Default</a>
                    <input type="hidden" id="default_buildsuccess" value="${default_mod_buildsuccess}"/><br>
                    <forms:submit id="test_buildsuccess" type="button" label="Test"/><br>
                </td>
            </tr>
            <tr>
                <th><label for="buildfails">Build fails: </label></th>
                <td>
                    <textarea id="buildfails" name="buildfails"
                              style="width: 92%;height: 150px;">${mod_buildfails}</textarea>
                    <a style="vertical-align: top;" href="#" id="default_buildfails_link">Default</a>
                    <input type="hidden" id="default_buildfails" value="${default_mod_buildfails}"/><br>
                    <forms:submit id="test_buildfails" type="button" label="Test"/><br>
                </td>
            </tr>
            <tr>
                <th><label for="buildfailstrt">Build fails to start: </label></th>
                <td>
                    <textarea id="buildfailstrt" name="buildfailstrt"
                              style="width: 92%;height: 150px;">${mod_buildfailstrt}</textarea>
                    <a style="vertical-align: top;" href="#" id="default_buildfailstrt_link">Default</a>
                    <input type="hidden" id="default_buildfailstrt" value="${default_mod_buildfailstrt}"/><br>
                    <forms:submit id="test_buildfailstrt" type="button" label="Test"/><br>
                </td>
            </tr>
            <tr>
                <th><label for="buildfirsterror">Build first error occurs: </label></th>
                <td>
                    <textarea id="buildfirsterror" name="buildfirsterror"
                              style="width: 92%;height: 150px;">${mod_buildfirsterror}</textarea>
                    <a style="vertical-align: top;" href="#" id="default_buildfirsterror_link">Default</a>
                    <input type="hidden" id="default_buildfirsterror" value="${default_mod_buildfirsterror}"/><br>
                    <forms:submit id="test_buildfirsterror" type="button" label="Test"/><br>
                </td>
            </tr>
            <tr>
                <th><label for="buildhanging">Build is probably hanging: </label></th>
                <td>
                    <textarea id="buildhanging" name="buildhanging"
                              style="width: 92%;height: 150px;">${mod_buildhanging}</textarea>
                    <a style="vertical-align: top;" href="#" id="default_buildhanging_link">Default</a>
                    <input type="hidden" id="default_buildhanging" value="${default_mod_buildhanging}"/><br>
                    <forms:submit id="test_buildhanging" type="button" label="Test"/><br>
                </td>
            </tr>
            <!-- <tr>
                <th><label for="serverstartup">Server startup: </label></th>
                <td>
                    <textarea id="serverstartup" name="serverstartup"
                              style="width: 92%;height: 150px;">${mod_serverstartup}</textarea>
                    <a style="vertical-align: top;" href="#" id="default_serverstartup_link">Default</a>
                    <input type="hidden" id="default_serverstartup" value="${default_mod_serverstartup}"/><br>
                </td>
            </tr>
            <tr>
                <th><label for="servershutdown">Server shutdown: </label></th>
                <td>
                    <textarea id="servershutdown" name="servershutdown"
                              style="width: 92%;height: 150px;">${mod_servershutdown}</textarea>
                    <a style="vertical-align: top;" href="#" id="default_servershutdown_link">Default</a>
                    <input type="hidden" id="default_servershutdown" value="${default_mod_servershutdown}"/><br>
                </td>
            </tr> -->
        </table>
        <div class="saveButtonsBlock">
            <forms:submit id="save-btn" label="Save"/>
            <forms:saving/>
        </div>
    </div>
</form>

<script type="text/javascript">
    jQuery(document).ready(function () {
    });

    (function ($) {
        var createSaveUrl = function () {
            return "?apiurl=" + encodeURIComponent($("#apiurl").val())
                    + "&buildstarted=" + encodeURIComponent($("#buildstarted").val())
                    + "&buildsuccess=" + encodeURIComponent($("#buildsuccess").val())
                    + "&buildfails=" + encodeURIComponent($("#buildfails").val())
                    + "&buildfailstrt=" + encodeURIComponent($("#buildfailstrt").val())
                    + "&buildfirsterror=" + encodeURIComponent($("#buildfirsterror").val())
                    + "&buildhanging=" + encodeURIComponent($("#buildhanging").val())
                    + "&serverstartup=" + encodeURIComponent($("#serverstartup").val())
                    + "&servershutdown=" + encodeURIComponent($("#servershutdown").val());
        };

        var save = function () {
            $.post("${actionUrl}" + createSaveUrl(),
                    function () {
                        BS.reload(true);
                    });

            return false;
        };

        var sendAction = function (enable) {
            $.post("${actionUrl}?action=" + (enable ? 'enable' : 'disable'),
                    function () {
                        BS.reload(true);
                    });
            return false;
        };

        var sendTest = function (testJson) {
            if ($("#apiurl").val()) {
                jQuery.ajax(
                        {
                            url: $("#apiurl").val(),
                            data: testJson,
                            type: "POST"
                        }).done(function () {
                            alert("Connection successful! Please see posted message in the room!");
                        }).fail(function () {
                            alert("Connection failed!")
                        });
            } else {
                alert("Please fill the URL field!")
            }
        }

        $("#save-btn").click(function () {
            return save();
        });

        $("#enable-btn").click(function () {
            return sendAction(true);
        });

        $("#disable-btn")
                .click(
                function () {
                    if (!confirm("Glip notifications will not be sent until enabled. Disable the notifier?"))
                        return false;
                    return sendAction(false);
                });

        $("#testConnection").click(function () {
            sendTest({
                "icon": "http://icons.iconarchive.com/icons/iconshock/real-vista-food/128/beer-icon.png",
                "activity": "Beer consumed - Test from TC plugin!",
                "title": "Jeff is having a Maple Bacon Coffee Porter - Test from TC plugin!",
                "body": "* Location: [The Funky Buddha Lounge](http://www.thefunkybuddha.com)\n*Beer Advocate Rating: [99](http://tinyurl.com/psf4uzq)"
            });
        });

        $('#default_buildstarted_link').click(function () {
            $("#buildstarted").val($("#default_buildstarted").val().replace(/'/g, '"'));
            return false;
        });

        $('#test_buildstarted').click(function () {
            sendTest(JSON.parse($("#buildstarted").val()));
        });

        $('#default_buildsuccess_link').click(function () {
            $("#buildsuccess").val($("#default_buildsuccess").val().replace(/'/g, '"'));
            return false;
        });

        $('#test_buildsuccess').click(function () {
            sendTest(JSON.parse($("#buildsuccess").val()));
        });

        $('#default_buildfails_link').click(function () {
            $("#buildfails").val($("#default_buildfails").val().replace(/'/g, '"'));
            return false;
        });

        $('#test_buildfails').click(function () {
            sendTest(JSON.parse($("#buildfails").val()));
        });

        $('#default_buildfailstrt_link').click(function () {
            $("#buildfailstrt").val($("#default_buildfailstrt").val().replace(/'/g, '"'));
            return false;
        });

        $('#test_buildfailstrt').click(function () {
            sendTest(JSON.parse($("#buildfailstrt").val()));
        });

        $('#default_buildfirsterror_link').click(function () {
            $("#buildfirsterror").val($("#default_buildfirsterror").val().replace(/'/g, '"'));
            return false;
        });

        $('#test_buildfirsterror').click(function () {
            sendTest(JSON.parse($("#buildfirsterror").val()));
        });

        $('#default_buildhanging_link').click(function () {
            $("#buildhanging").val($("#default_buildhanging").val().replace(/'/g, '"'));
            return false;
        });

        $('#test_buildhanging').click(function () {
            sendTest(JSON.parse($("#buildhanging").val()));
        });

        $('#default_serverstartup_link').click(function () {
            $("#serverstartup").val($("#default_serverstartup").val().replace(/'/g, '"'));
            return false;
        });

        $('#test_serverstartup').click(function () {
            sendTest(JSON.parse($("#serverstartup").val()));
        });

        $('#default_servershutdown_link').click(function () {
            $("#servershutdown").val($("#default_servershutdown").val().replace(/'/g, '"'));
            return false;
        });

        $('#test_servershutdown').click(function () {
            sendTest(JSON.parse($("#servershutdown").val()));
        });

    })(jQuery);

</script>
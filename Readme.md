# Glip Notifier for TeamCity
Simple and easy way to add build notifications from TeamCity to your [Glip](https://glip.com) WebHook integration.

So, you should know, that even this plugin build following the TeamCity Notifier Template, it has only one "state" for all user.
It basically means, that notification will be done only once per each "event", not per every TC user.

Having said that, you should still remember to create an Notifier rule for this plugin to be able to work.
Creation of the "root" rule is recommended. Creation more than one notification rule is not tested and not
recommended.

You can read more about plugin and Notifier configuration at the Wiki [page](https://github.com/it-committed/glip-notificator/wiki/Managing-plugin-settings).

## Installation
Download the latest pre-build plugin (release) from [here](https://github.com/it-committed/glip-notificator/releases/)

And Follow the TeamCity installation guide for [plugins](https://confluence.jetbrains.com/display/TCD9/Installing+Additional+Plugins)

## Build plugin

If you want to build your own plugin, after clone:

* cd [project folder]
* ./gradlew clean buildPlugin
* find your plugin under [project folder]/build/distributions/GlipNotificator.zip

If you want to develop it using your local installation of the TeamCity, then after clone:

* vim build..gradle
* change two variables "ext.teamCityHomeFolder" and "ext.teamCityBuildServerFolder" point to your installation
* change code as you wish
* go to your [TEAM_CITY_HOME]/bin
* do ./teamcity-server.sh stop
* go to your [project folder]
* ./gradlew clean installPlugin
* go back to the [TEAM_CITY_HOME]/bin
* do ./teamcity-server.sh start
* test the plugin, using your local TeamCity

## License
This software is distributed without warranty of any kind, under the [MIT](https://opensource.org/licenses/MIT) license.

## Thanks to
This plugin is using only one third party library:

ObjectProps - Serializing Java objects into java.util.Properties
Copyright (C) 2009 by Michael Karneim, http://code.google.com/p/objectprops/
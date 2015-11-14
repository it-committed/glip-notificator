# Glip Notifier for TeamCity
Simple and easy way to add TeamCity notifications to your [Glip](https://glip.com) WebHook integration.

So, you should know that even this plugin build as a TeamCity Notificator, it has one "state" for all user.
If you want to switch it off, use the button on tha Admin page, but it will turn it off for _all_ user, since
there is only one WebHook room in your Glip.

If you want to switch off any notification you can just make it template "empty".

## Installation
Download the  pre-build plugin [here](link?)

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
This plugin is using only one OS library:

ObjectProps - Serializing Java objects into java.util.Properties
Copyright (C) 2009 by Michael Karneim, http://code.google.com/p/objectprops/
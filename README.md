# EveDotLan

This project scans the chat logs folder (`%userprofile%\Documents\Eve\Logs\Chatlogs`) for the newest Local chat log, 
and parses it for the `EVE System > Channel changed to Local` text. It then uses the SeleniumWebDriver 
to fire up an instance of Chrome, and highlights your current location on https://evemaps.dotlan.net/

### Requirements
* Must have Java 8 or newer installed.
* Must have Chrome installed.

### Running
Just double-click the jar file. When you close the browser, the app will also be closed.
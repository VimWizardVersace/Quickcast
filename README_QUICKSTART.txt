                            Quickcast Quickstart Guide
================================================================================

    In order to try out the latest changes in the front end, just follow these
simple steps:

1. Run '$ git clone -b <branch-name> https://github.com/Roastmaster/Quickcast'
    in order to get the latest front end code. Replace <branch-name> with one of
    the following:
        master - gets you all of the latest updates, including back end.
        frontend_latest - gets you the latest front end code (maybe unstable).
        frontend_stable - gets you the latest stable front end code.

2. Install Android Studio (https://developer.android.com/sdk/index.html) and the
    Android SDK. Just follow the instructions on ^that site if you are unsure.

3. Launch Android Studio and direct it to the project folder 'Quickcast/
    front_end.'

4. Once the project loads, click the green 'Run app' arrow in the toolbar, or
    press Shift+F10

5. Follow the prompts to open or creat a new Android Virtual Device if you don't
    have a physical device to test on.

6. Enjoy!

    If you don't have a device with you to test on, Android Studio can generate
unsigned APKs for manual installation. To do so, open the Gradle panel (it
should be on the right side of the window) and drill down to 'front_end>
front_end(root)>Tasks>build' and double click on 'assemble.' When the script
finishes, you should find three APKs located in 'front_end/app/build/outputs/
apk/,' but you probably only want 'app-release-unsigned.apk'
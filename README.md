This is a Kotlin Multiplatform project targeting Android, iOS.

Backlog Buster is a todo list app with a twist, it is focussed entirely on a user's backlog of unfinished games.
It allows users to add games they wish to play to a list, it will track when they were added and when they were finished.
The user has the ability to rate the games using a 6-point Likert scale.
Users can sort the backlog by when games were added, when they were finished, how long they took to finish or the rating they gave to the game. 
This project was conceived mostly for my own use, I had spent years tracking games I wanted to play inside the iOS reminders app and wanted
something that felt a bit more tailored to gaming specifically. The project also presented a good opportunity to learn Kotlin and Compose multiplatform.

![plot](screenshots/Main%20Screen%20Sort%20by%20Title%20-%20Completed%20Visible.png)
![plot](screenshots/Add%20Game%20Screen.png)
![plot](screenshots/Details%20Screen.png)

More screenshots available in the screenshots folder.

The libraries in technologies used in Backlog Buster are as follows. 
- Kotlin
- Compose Multiplatform
- Material 3
- Room: for the data persistence layer
- Koin: for dependency injection
- Voyager: for navigation, because Compose Navigation 3 is not yet supported by Koin on all target platforms.
- Datastore: To store basic user preferences, this is used to preserve the users filter and sort states on the main screen between app boots.

The App uses the MVVM architecture outlined by Google's documentation, this is the recommended architecture for Compose.
Voyager also recommends the use of the MVVM arch by providing Screens for navigation, these Screens are backed by ScreenModels
that act as the ViewModel for the Screen. They present information retrieved via Room, which is done through Data Access Objects or DAOs,
these DAOs are injected into the ScreenModels by Koin.

This version of the App will soon be deployed to both the iOS App Store and Google Play Store, pending final design of the app icon, which is in progress.
However, it can be built for Android and iOS as is by following the steps outlined below. 

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
    - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
      Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
      folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:

- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
<h1 align="center">Book Parking</h1>

<p align="center">  
Book Parking is a demo application based on MVVM architecture. The app allows users to booking parking slots, the app uses firebase for the backend.
</p>
</br>
<p align="center">
<img src="/screenshots/row.png"/>
</p>

## Download
Go to the [Releases](https://github.com/nero002/Book-Parking/releases) to download the latest APK.

<img src="/screenshots/appworking.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Firebase](https://firebase.google.com/) - for user authentication & realtime database for storing and retrieving data. 
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- JetPack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - compose - Modern Android UI toolkit.
- Architecture
  - MVVM Architecture (View - ViewBinding - ViewModel - Model)
  - Repository pattern
- [Glide](https://github.com/bumptech/glide) - loading images.
- [Lottie](https://github.com/airbnb/lottie-android) - for animations.
- [Folding cell](https://github.com/Ramotion/folding-cell) - expanding content cell with paper like animation.
</br>
</br>
</br>
</br>
</br>
</br>
</br>
</br>
</br>
</br>

## Architecture
Book Parking is based on MVVM architecture and repository pattern.
![architecture](/screenshots/architecture.png)

## Getting Started

1. [Add Firebase to your Android Project.](https://firebase.google.com/docs/android/setup)
2. Copy the google-services.json file you just downloaded into the app/ or mobile/ directory of your Android Studio project.
3. Select the Auth panel and then click the Sign In Method tab.
4. Click Google and turn on the Enable switch, then click Save
5. Import [this](/screenshots/book_parking_export.json) file into your firebase real time database.

## Demo Video 
You can watch the full demo on [YouTube](https://youtu.be/xXWviLTXARg)

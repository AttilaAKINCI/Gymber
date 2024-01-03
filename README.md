# Gymber
Gymber is a tinder like gym club finder for your daily sports routine 🙌

[APK Link (https://drive.google.com/file/d/1nB7PulUQSDBgPjQSAkYQkSs_y2FW3uCw/view?usp=sharing)](https://drive.google.com/file/d/1nB7PulUQSDBgPjQSAkYQkSs_y2FW3uCw/view?usp=sharing)


## How to run in your local
In order to run project in your local be aware below points ->
* Android Studio Iguana | 2023.2.1 Beta 1 | Build #AI-232.10227.8.2321.11231672, built on December 18, 2023
* checkout master branch
* add *SERVICE_ENDPOINT_BASE_URL=https://api.one.fit/* to your local.properties file.


## App Video
    
    Launch & Permission        Permission denial          Swipe Actions     Details & Map      Gym Match            

<img src="https://github.com/AttilaAKINCI/Gymber/assets/21987335/353f5110-d78c-49a5-9f60-98d0ec4c910f" width="160"/> <img 
src="https://github.com/AttilaAKINCI/Gymber/assets/21987335/6b2254a6-9bde-414a-93a1-eb9d391ca500" width="160"/>  <img 
src="https://github.com/AttilaAKINCI/Gymber/assets/21987335/c96fb0e7-22bc-44fe-962a-3df42ddb230c" width="160"/> <img
src="https://github.com/AttilaAKINCI/Gymber/assets/21987335/bfcdd190-588b-4076-80cd-e21992dc35a6" width="160"/>  <img
src="https://github.com/AttilaAKINCI/Gymber/assets/21987335/bafb34d7-5076-4637-8c04-b2e93fff4130" width="160"/> 


## 3rd party lib. usages & Tech Specs
* Kotlin
* [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
* [Kotlin DSL](https://developer.android.com/build/migrate-to-kotlin-dsl)
* Patterns
    - MVVM
    - Clean Architecture
    - Repository
* [JetPack Compose](https://developer.android.com/jetpack/compose?gclid=Cj0KCQiAjMKqBhCgARIsAPDgWlyVg8bZaasX_bdQfYrAXsuDQ6vD-2SmFcTv34Fb-jLQxgGqPD7UxKgaAso5EALw_wcB&gclsrc=aw.ds)
* [Edge to Edge UI design](https://developer.android.com/jetpack/compose/layouts/insets)
* Shimmer Loading
* Native/Custom Splash Screen
* Dark/Light UI Mode
* [App Permissions - Jetpack compose adjusted](https://developer.android.com/guide/topics/permissions/overview)
* [GPS location- Jetpack compose adjusted](https://developer.android.com/develop/sensors-and-location/location/retrieve-current)
* [Compose Destinations](https://github.com/raamcosta/compose-destinations) / [Documentation](https://composedestinations.rafaelcosta.xyz/)
* [Ktor Client](https://ktor.io/docs/client-supported-platforms.html)
* [Lottie Animations](https://github.com/airbnb/lottie-android)
* [Coil](https://github.com/coil-kt/coil)
    - Asynch image loading
* [Timber Client logging](https://github.com/JakeWharton/timber)
* [Dependency Injection (HILT)](https://developer.android.com/training/dependency-injection/hilt-android)
* [Turbine](https://github.com/cashapp/turbine)
* [MockK](https://mockk.io/)
* Unit testing
* Instrumentation & Compose UI Testing
* Junit5


#### UI Flow
1- Gymber app starts with avocado animation on Splash Screen, When animation end, user navigates to Dashboard Screen

2- Considering user's location permissions, permission request buttons are appeared

3- In dashboard screen gym places are shown accordingly. this page can be managed by 4 buttons at the bottom.(Reverse / Dislike / Detail / Like)

4- Like Button checks matching state if there is a match, matchScreen is shown.

5- Detail screen shows further details about gyms

6- Detail screen can navigate users to Google Maps to view gyms on google maps 


#### ScreenShots
Light Mode:

<img src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/1-light.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/2-light.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/3-light.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/4-light.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/5-light.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/6-light.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/7-light.png" width="110">

Dark Mode:

<img src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/1-dark.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/2-dark.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/3-dark.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/4-dark.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/5-dark.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/6-dark.png" width="110">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/7-dark.png" width="110">


# License

The code is licensed as:

```
Copyright 2021 Attila Akıncı

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


# GymberCompose
GymberCompose is a searcher app for sports clubs using Jetpack compose way

## Brief Description
GymberCompose is a similar repository to [Gymber](https://github.com/AttilaAKINCI/Gymber) that wraps UI development part with Jetpack Compose!! 

[APK Link (https://drive.google.com/file/d/1YVQSlhfMWlEnrI48xwL1XqOLeE0T2Z92/view?usp=sharing)](https://drive.google.com/file/d/1YVQSlhfMWlEnrI48xwL1XqOLeE0T2Z92/view?usp=sharing)

## App Video

         Like and Match              Detail Screen           

<img src="https://user-images.githubusercontent.com/21987335/147857253-e1a926e6-9c98-411f-bc79-7ec39077f855.gif" width="200"/> <img 
src="https://user-images.githubusercontent.com/21987335/147857260-aab0585a-e264-4f17-8298-5287650fe835.gif" width="200"/>   


## 3rd party lib. usages & Tech Specs
* Patterns
    - MVVM design pattern
    - Repository pattern for data management
* JetPack Libs
    - Compose
    - Compose UI testing
* Retrofit
* Kotlin & Coroutines
* Coil Image loading
* Lottie compose animation Lib.
* Moshi Json handler
* Timber Client logging
* Dependency Injection (HILT) 
* Single Activity multiple Composable approach
* Unit testing samples & HILT integrations for testing
* MockK library for unit testing
* Junit5
* Thruth (assertions)

#### UI Flow
1- Gymber app starts with avocado animaion on Splash Screen, When animation end, user navigates to Dashboard Screen

2- in dashboard screen gym places are shown accordingly. this page can be managed by 3 buttons at the bottom.

3- Like Button checks matching state if there is a match matchScreen is shown.

4- detail screen contains special details about gym

#### ScreenShots
<img src="https://github.com/AttilaAKINCI/Gymber-Compose/blob/master/images/1.png" width="250">   <img
src="https://github.com/AttilaAKINCI/Gymber-Compose/blob/master/images/2.png" width="250">   <img
src="https://github.com/AttilaAKINCI/Gymber-Compose/blob/master/images/3.png" width="250">   <img
src="https://github.com/AttilaAKINCI/Gymber-Compose/blob/master/images/4.png" width="250">   <img
src="https://github.com/AttilaAKINCI/Gymber-Compose/blob/master/images/5.png" width="250">   <img
src="https://github.com/AttilaAKINCI/Gymber-Compose/blob/master/images/6.png" width="250">   

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


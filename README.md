# Gymber
Gymber is a POC project that offers a search on sports clubs in certain area

## Brief Description
Gymber app consist of 2 different fragments and 1 root activity. Activity holds a container layout in order to manage fragments which will be controlled by navigation component.
According to network connectivity and app permissions, app navigates user with alert dialogs.

[APK Link (https://drive.google.com/file/d/1newfhBtYWSusOq8zbZZyUipB8SeoVFkJ/view?usp=sharing)](https://drive.google.com/file/d/1newfhBtYWSusOq8zbZZyUipB8SeoVFkJ/view?usp=sharing)

## App Video

         Normal Run               Match Condition             App Permissions

<img src="https://user-images.githubusercontent.com/21987335/147551063-3b3ea1c6-ed61-4198-9f05-4ca5929f2a8c.gif" width="200"/> <img 
src="https://user-images.githubusercontent.com/21987335/147551113-d01939f5-4749-495a-8fe9-c13f9caea0ea.gif" width="200"/>  <img 
src="https://user-images.githubusercontent.com/21987335/147551149-1f3f2129-3b46-452b-ac74-d6dba158bad2.gif" width="200"/>  


## 3rd party lib. usages & Tech Specs
* Patterns
    - MVVM design pattern
    - Repository pattern for data management
* JetPack Libs
    - Navigation Component
* App Permissions
* GPS Location
* Retrofit
* Kotlin & Coroutines
* Coil Image loading
* Lottie animation Lib.
* Facebook Shimmer
* Fragment Transition Animations
* Moshi Json handler
* Timber Client logging
* Dependency Injection (HILT) 
* DataBinding, ViewBinding
* RecyclerView with List Adapter and DiffUtil
* Single Activity multiple Fragments approach
* Unit testing samples & HILT integrations for testing
* MockK library for unit testing
* Turbine
* Junit5
* Thruth (assertions)

#### UI Flow
1- Gymber app starts with avocado animaion on Splash Screen, When animation end, user navigates to Dashboard Screen

2- App calculates distance between user and gym places so app asks location permission on dashboard start

3- If permission is not granted, app creates a dialog that says "this permission is obligated so you can go to settings in order to change your desicion".

4- After permission granted, gym list is loaded after a shimmer(loading) animation

5- Dashboard cards works like tinder swipe, you can swipe them to right for potential matches, swipe to left to throw them away or click them to checkout some details of the gyms.

6- For match case, Match screen comes with alpha animation and contains confetti animation. 

7- In detail page, selected gym related informations are listed. 


#### ScreenShots
<img src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/1.png" width="200">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/2.png" width="200">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/3.png" width="200">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/4.png" width="200">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/5.png" width="200">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/6.png" width="200">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/7.png" width="200">   <img
src="https://github.com/AttilaAKINCI/Gymber/blob/master/images/8.png" width="200"> 

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


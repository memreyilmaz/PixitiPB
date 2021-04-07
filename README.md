<h1 align="center">Pixiti</h1>

<p align="center">  
Pixiti is a demo application based on modern Android application tech-stacks and MVVM architecture.<br>App fetching data from the network via repository pattern.<br>
</p>
</br>

<p align="center">
<img src="/Gallery.png" width="30%"/>
<img src="/Detail.png" width="30%"/>
<img src="/Info.png" width="30%"/>
</p>


<img src="/preview.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt (alpha) for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Paging - The Paging Library helps you load and display small chunks of data at a time.
  - Navigation - Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app. 
- Architecture
  - MVVM Architecture (View - Viewinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
- [Dexter](https://github.com/Karumi/Dexter) - Dexter is an Android library that simplifies the process of requesting permissions at runtime.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- [FlexBox](https://github.com/google/flexbox-layout) - FlexboxLayout is a library project which brings the similar capabilities of CSS Flexible Box Layout Module to Android.
- Custom Views
  - [PhotoView](https://github.com/chrisbanes/PhotoView) - An easily usable implementation of a zooming Android ImageView.
  - [CircleImageView](https://github.com/hdodenhof/CircleImageView) - A fast circular ImageView perfect for profile images.


## Architecture
Pixiti is based on MVVM architecture and a repository pattern.

## Pixabay API

<img src="/pixabaylogo.png" align="right" width="21%"/>

Pixiti using the [PixabayAPI](https://https://pixabay.com/api/docs/) for constructing RESTful API.<br>
Pixabay API provides a RESTful API interface for searching and retrieving free images and videos released under the Pixabay License.

## Icons on this app are from [Flaticon](https://www.flaticon.com/)

<div>Icons made by <a href="https://www.flaticon.com/authors/gregor-cresnar" title="Gregor Cresnar">Gregor Cresnar</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div> <div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>


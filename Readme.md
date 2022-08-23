<h1 align="center">Sing King Technical Task - Junior Mobile Developer</h1></br>
<p align="center">  
A simple page based on modern Android tech-stacks and MVVM architecture. Fetching data from the api and integrating persisted data in the database via repository pattern to search character based on query.
</p>
</br>
<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

## Screenshots

<p align="center">
<img src="https://i.postimg.cc/vTBtxYC6/1661241298958.jpg" width="270"/>
<img src="https://i.postimg.cc/52Ms9CfX/1661241309781.jpg" width="270"/>
</br>
<img src="https://i.postimg.cc/pL2K1Y2J/Task.gif" width="270"/>
</p>

## Tech stack & Open-source libraries

- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based
  + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
  + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous.
- Hilt for dependency injection.
- AndroidX
    - Lifecycle - dispose observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
    - Cache - Room database.
    - App Startup - Provides a straightforward, performant way to initialize components at
      application startup.
- Architecture
    - MVVM Architecture (Declarative View - ViewModel - Model)
    - Repository pattern
- Material Design & Animations
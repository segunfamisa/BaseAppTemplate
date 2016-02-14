## Android App Base template
When you're starting to write  an app, there are certain classes, methods and lines of code you
implement over and over. So, I thought I should have base template usable across all my apps.
I hope to write an Android Studio plugin to generate this automatically later in the future.

This template contains some of my favourite packages, config, classes and  methods.
In my research, I found alot of boiler plate templates, some of which I adapted some techniques
and structures from. This is still under development, and many ideas were sourced from various places.

Structurally, this tries to follow [Ribot Lab's boiler plate](https://github.com/ribot/android-boilerplate),
as well as [Fernando Cejas's Clean Architecture](https://github.com/android10/Android-CleanArchitecture)

I am learning to use dependency injection with [Fernando's](http://fernandocejas.com/2015/04/11/tasting-dagger-2-on-android/)
dagger tutorial as well as some other online tutorials.

##Features

1. Dependency Injection using Dagger: This section is still not fully developed;
I'm just wrapping my head around DI with dagger
2. `Navigator` class to handle Navigation.
3. `MediaPickerFragment` (with Android Marshmallow Permission).
4. `BaseActivity` with the following util methods:
  1. `addFragment()`
  2. `replaceFragment()`
  3. `close()`
  4. `hideKeyboard()`
5. `BaseFragment` with the following util methods:
  1. `closeFragment()`
  2. `hideKeyboard()`
6. Util classes & methods
  1. `AppUtils` with the following util methods:
        1. `getAppVersion()`
        2. `isTablet()`
        3. `isServiceRunning()`
        4. `hideKeyboard()`
  2. `BitmapUtils` with the following util methods:
        1. `decodeBitmapFromStream()`
        2. `calculateInSampleSize()`
  3. `DateUtils` with the following util methods:
        1. `getDay()`
        2. `getMonthShort()`
        3. `getMonth()`
  4. `DeviceUuidFactory` to retrieve the device id of the device.
  5. `DialogUtils` with the following util methods:
        1. `createSimpleOkDialog()` - different overloaded methods with various parameters
        2. `createProgressDialog()`
  6. ListUtils
  7. `Logger` - for logging. Different from `android.util.Log` by a flag `DEBUG_ON` which you can use to toggle whether or not you want to show the log
  8. `NetworkUtils` with the following util methods:
        1. `isNetworkConnected()`
  9. `PreferenceUtils` - very much like a Preference manager for your SharedPreferences
  10. `StringUtils` - String utils with the following util methods:
        1. `isEmpty()` - that returns true if the String is null, empty or "null"
        2. `nullify()` - to return the value of a string or null if the string is empty
  11. `UriUtils` - utils to convert a URI to a file path. This is useful when you're picking files from the device.
  File picking is fragmented and occurs differently on different versions of Android.
  This class helps to convert such into file paths.

##Usage
This part is still work in progress :)

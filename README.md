# AndroidHtmlParser
Simple Use Case For Implements HtmlCompat.fromHtml to Show Html Tag With Inline Image Link With Kotlin Coroutines and LifecycleScope in one TextView

<p align="left">
  <img src="https://user-images.githubusercontent.com/55354691/138512438-f37264f5-c72f-48cc-82de-ad519748fe52.jpg" width="250">
</p>


# How to Use

### Step 1. add this dependencies to your build.gradle

```
  implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1'
  implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
  implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'
```

### Step 2. Copy HtmlParser.kt into your project


### Step 3. in Activity or Fragment use Builder Pattern To Build a htmlParser class
HtmlParser depends on CoroutineScope Dispatcher so use lifecycleScope to control  image request flow

```
HtmlParser()
   .setCoroutineScope(lifecycleScope)
   .maxImageWidth(screenWidth)
   .setPlaceHolder(R.drawable.placeHolder)
   .with(htmlText)
   .into(textView)
   .parse()
```

### Note : For Sample Code Checkout MainActivity.kt

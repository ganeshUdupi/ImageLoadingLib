ImageZen

ImageZen is a lightweight and easy-to-use image loading library for Android. It allows you to load images from URLs asynchronously and display them in your Compose UI.
Features

    Asynchronous image loading from URLs.
    Simple integration with Jetpack Compose.
    Error handling for image loading failures.

Installation

Add the following dependencies to your build.gradle file:
dependencies {
    implementation 'androidx.compose.ui:ui:1.0.0'
    implementation 'androidx.compose.runtime:runtime:1.0.0'
    implementation 'androidx.compose.foundation:foundation:1.0.0'
}
Usage
Loading Images

To load an image from a URL, use the ImageZen.loadImageFromUrl function.
ImageZen.loadImageFromUrl(
    url = "https://example.com/image.jpg",
    onSuccess = { bitmap ->
        // Handle the loaded bitmap
    },
    onError = { error ->
        // Handle the error
    }
)

ImageZen.loadImageFromUrl(
    url = "https://example.com/image.jpg",
    onSuccess = { bitmap ->
        // Handle the loaded bitmap
    },
    onError = { error ->
        // Handle the error
    }
)
ImageZen

ImageZen is a lightweight and easy-to-use image loading library for Android. It allows you to load images from URLs asynchronously and display them in your Compose UI.
Features

    Asynchronous image loading from URLs.
    Simple integration with Jetpack Compose.
    Error handling for image loading failures.

Installation

Add the following dependencies to your build.gradle file:

gradle

dependencies {
    implementation 'androidx.compose.ui:ui:1.0.0'
    implementation 'androidx.compose.runtime:runtime:1.0.0'
    implementation 'androidx.compose.foundation:foundation:1.0.0'
}

Usage
Loading Images

To load an image from a URL, use the ImageZen.loadImageFromUrl function.

kotlin

ImageZen.loadImageFromUrl(
    url = "https://example.com/image.jpg",
    onSuccess = { bitmap ->
        // Handle the loaded bitmap
    },
    onError = { error ->
        // Handle the error
    }
)

Composable Function

Use the imageComponent composable function to display an image from a URL.

kotlin

@Composable
fun imageComponent(url: String) {
    var isLoading by remember {
        mutableStateOf(true)
    }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    if (bitmap == null && isLoading) {
        ImageZen.loadImageFromUrl(url, onSuccess = { loadedBitmap ->
            bitmap = loadedBitmap
            isLoading = false
        }, onError = {
            isLoading = false
        })
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    } else if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

Example

Here's a complete example of how to use ImageZen in your Compose UI:

kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object ImageZen {
    private val handler = Handler(Looper.getMainLooper())
    
    fun loadImageFromUrl(url: String, onSuccess: (Bitmap) -> Unit, onError: (String) -> Unit) {
        thread(start = true) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    handler.post {
                        onSuccess(bitmap)
                    }
                } else {
                    onError("Error in connection")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError("Error ${e.localizedMessage}")
            }
        }
    }
}

@Composable
fun imageComponent(url: String) {
    var isLoading by remember {
        mutableStateOf(true)
    }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    if (bitmap == null && isLoading) {
        ImageZen.loadImageFromUrl(url, onSuccess = { loadedBitmap ->
            bitmap = loadedBitmap
            isLoading = false
        }, onError = {
            isLoading = false
        })
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    } else if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    imageComponent(url = "https://example.com/image.jpg")
}

Contributing

Contributions are welcome! Please open an issue or submit a pull request on GitHub.
License

This project is licensed under the MIT License.

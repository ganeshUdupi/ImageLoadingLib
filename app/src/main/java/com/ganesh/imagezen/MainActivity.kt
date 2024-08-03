package com.ganesh.imagezen

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.ganesh.imagezen.ui.theme.ImageZen
import com.ganesh.imagezen.ui.theme.ImageZenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageZenTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ImageComponent("https://fastly.picsum.photos/id/645/200/300.jpg?hmac=fiKW3Nj8r0CWJQY3S-kkeT8PAfvKhA8igd9GIRk41Yw")
                }

            }
        }
    }

}

@Composable
fun ImageComponent(url: String) {
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

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        if (isLoading){
            CircularProgressIndicator()
        }else{
             bitmap?.also {loadedBitmap->
                 Image(bitmap =loadedBitmap.asImageBitmap() ,
                     contentDescription ="",
                     Modifier.fillMaxWidth(),
                     contentScale = ContentScale.Fit)

             }
        }
    }
}

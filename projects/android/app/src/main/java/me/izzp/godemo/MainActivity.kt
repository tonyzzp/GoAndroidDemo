package me.izzp.godemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.izzp.godemo.go.gcodec.Gcodec
import me.izzp.godemo.go.ghttp.Ghttp
import me.izzp.godemo.go.gojni.Gojni
import me.izzp.godemo.ui.theme.GoDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var result by remember { mutableStateOf("") }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextButton(text = "md5_string") {
                            result = Gcodec.mD5String("ruok")
                        }
                        TextButton(text = "md5_bytes") {
                            result = Gcodec.mD5Bytes("ruok".toByteArray())
                        }
                        TextButton(text = "md5_file") {
                            result = Gcodec.mD5File(packageCodePath)
                        }
                        HttpButton(onResult = {
                            result = it
                        })
                        ResultDialog(result = result) {
                            result = ""
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextButton(
    text: String,
    enable: Boolean = true,
    onClick: () -> Unit,
) {
    Button(onClick = onClick, enabled = enable) {
        Text(text = text)
    }
}

@Composable
fun HttpButton(onResult: (String) -> Unit) {
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Button(
        enabled = !isLoading,
        onClick = {
            isLoading = true
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    Thread.sleep(1000)
                    Ghttp.get("http://www.baidu.com")
                    onResult("http request ok")
                } catch (e: Exception) {
                    e.printStackTrace()
                    onResult(e.toString())
                }
                isLoading = false
            }
        },
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
            )
        }
        Text(text = "http")
    }
}

@Composable
fun ResultDialog(result: String, onDismiss: () -> Unit) {
    if (result.isEmpty()) {
        return
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            OutlinedButton(
                onClick = onDismiss,
                border = null,
            ) {
                Text(text = "OK")
            }
        },
        text = {
            Text(
                text = result, modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            )
        }
    )
}
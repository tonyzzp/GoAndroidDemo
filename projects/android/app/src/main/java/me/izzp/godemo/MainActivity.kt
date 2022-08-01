package me.izzp.godemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.izzp.godemo.go.gcodec.Gcodec
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
                    }
                    if (result.isNotEmpty()) {
                        AlertDialog(
                            onDismissRequest = {
                                println("onDismissRequest")
                                result = ""
                            },
                            confirmButton = {
                                OutlinedButton(
                                    onClick = { result = "" },
                                    border = null,
                                ) {
                                    Text(text = "OK")
                                }
                            },
                            text = {
                                Text(text = "result:$result")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}
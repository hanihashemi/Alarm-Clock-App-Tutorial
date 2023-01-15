package io.github.hanihashemi.alarmclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.github.hanihashemi.alarmclock.ui.AnalogClockComponent
import io.github.hanihashemi.alarmclock.ui.shadow
import io.github.hanihashemi.alarmclock.ui.theme.AlarmClockTheme
import io.github.hanihashemi.alarmclock.ui.theme.NavigationBarColor
import io.github.hanihashemi.alarmclock.ui.theme.NavigationBarShadowColor
import kotlinx.coroutines.delay
import java.util.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var hour by remember { mutableStateOf("0") }
            var minute by remember { mutableStateOf("0") }
            var second by remember { mutableStateOf("0") }
            var amOrPm by remember { mutableStateOf("0") }

            LaunchedEffect(Unit) {
                while (true) {
                    val cal = Calendar.getInstance()
                    hour = cal.get(Calendar.HOUR).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    minute = cal.get(Calendar.MINUTE).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    second = cal.get(Calendar.SECOND).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    amOrPm = cal.get(Calendar.AM_PM).run {
                        if (this == Calendar.AM) "AM" else "PM"
                    }

                    delay(1000)
                }
            }

            AlarmClockTheme {
                Scaffold(bottomBar = { NavigationBarComponent() }) {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            HeaderComponent()

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .fillMaxHeight(fraction = 0.8f),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    AnalogClockComponent(
                                        hour = hour.toInt(),
                                        minute = minute.toInt(),
                                        second = second.toInt()
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                    DigitalClockComponent(
                                        hour = hour,
                                        minute = minute,
                                        amOrPm = amOrPm,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderComponent() {
    Box(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(text = "Clock", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun NavigationBarComponent() {
    NavigationBar(
        modifier = Modifier
            .shadow(
                color = NavigationBarShadowColor,
                offsetX = 0.dp,
                offsetY = (-5).dp,
                blurRadius = 50.dp
            )
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        containerColor = NavigationBarColor
    ) {
        NavigationBarItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_alarm_24),
                contentDescription = null
            )
        }, selected = false, onClick = { })
        NavigationBarItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_hourglass_bottom_24),
                contentDescription = null
            )
        }, selected = false, onClick = { })
        NavigationBarItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_access_time_24),
                contentDescription = null
            )
        }, selected = true, onClick = { })
        NavigationBarItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_timer_24),
                contentDescription = null
            )
        }, selected = false, onClick = { })
        NavigationBarItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_hotel_24),
                contentDescription = null
            )
        }, selected = false, onClick = { })
    }
}

@Composable
fun DigitalClockComponent(
    hour: String,
    minute: String,
    amOrPm: String,
) {
    Text(
        text = "$hour:$minute $amOrPm", style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = "Berlin, Germany", style = MaterialTheme.typography.bodyMedium.merge(
            TextStyle(
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.6f
                )
            )
        )
    )
}
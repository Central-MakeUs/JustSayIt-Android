package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.theme.White

@Composable
fun Toggle(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isEnabled: Boolean
) {
    Switch(
        modifier = modifier.height(36.dp),
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = JustSayItTheme.Colors.mainBackground,
            uncheckedThumbColor = JustSayItTheme.Colors.mainBackground,
            checkedTrackColor = JustSayItTheme.Colors.mainTypo,
            uncheckedTrackColor = JustSayItTheme.Colors.subBackground,
            checkedBorderColor = JustSayItTheme.Colors.mainTypo,
            uncheckedBorderColor = JustSayItTheme.Colors.subBackground,
            disabledCheckedThumbColor = JustSayItTheme.Colors.mainBackground,
            disabledUncheckedThumbColor = JustSayItTheme.Colors.mainBackground,
            disabledCheckedTrackColor = JustSayItTheme.Colors.mainTypo,
            disabledUncheckedTrackColor = JustSayItTheme.Colors.subBackground,
            disabledCheckedBorderColor = JustSayItTheme.Colors.mainTypo,
            disabledUncheckedBorderColor = JustSayItTheme.Colors.subBackground
        ),
        thumbContent = {
            // 공백으로 두어야 thumb가 track에 꽉 차게 된다.
        },
        enabled = isEnabled
    )
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun TogglePreview() {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Toggle(isChecked = isChecked, onCheckedChange = { newState -> isChecked = newState }, isEnabled = true)
}
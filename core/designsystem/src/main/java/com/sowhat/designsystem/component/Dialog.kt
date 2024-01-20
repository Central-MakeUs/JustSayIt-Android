package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.theme.White

@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    buttonContent: Pair<String, String>,
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        properties = DialogProperties(),
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = modifier,
            shape = JustSayItTheme.Shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor =JustSayItTheme.Colors.mainBackground
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(JustSayItTheme.Spacing.spaceMd),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                DialogText(
                    title = title,
                    subTitle = subTitle
                )

                DialogButtons(
                    buttonContent = buttonContent,
                    onDismiss = onDismiss,
                    onAccept = onAccept
                )
            }
        }
    }
}

@Composable
private fun DialogButtons(
    buttonContent: Pair<String, String>,
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceXS)
    ) {
        DefaultButtonFull(
            modifier = Modifier.weight(1f),
            text = buttonContent.first,
            textStyle = JustSayItTheme.Typography.body1,
            textColor = White,
            backgroundColor = JustSayItTheme.Colors.mainTypo,
            onClick = onDismiss
        )

        DefaultButtonFull(
            modifier = Modifier.weight(1f),
            text = buttonContent.second,
            textStyle = JustSayItTheme.Typography.body1,
            textColor = Gray500,
            backgroundColor = JustSayItTheme.Colors.subBackground,
            onClick = onAccept
        )
    }
}

@Composable
private fun DialogText(title: String, subTitle: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceBase)
    ) {
        Text(
            text = title,
            style = JustSayItTheme.Typography.body2,
            color = JustSayItTheme.Colors.mainTypo,
            textAlign = TextAlign.Center
        )

        Text(
            text = subTitle,
            style = JustSayItTheme.Typography.detail1,
            color = JustSayItTheme.Colors.subTypo,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun DialogPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AlertDialog(
            title = "작성중인 글이 있어요",
            subTitle = "그만 작성하기를 누르면\n작성중인 글은 저장되지 않아요.",
            buttonContent = "그만 작성하기" to "계속 작성하기",
            onAccept = { /*TODO*/ }) {

        }
    }
}
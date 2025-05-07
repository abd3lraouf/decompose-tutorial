package dev.abd3lraouf.learn.decompose.navigation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme

@Preview(showBackground = true)
@Composable
fun NavigationButtonPreview() {
    DecomposeTutorialTheme {
        Surface {
            NavigationButton(
                isSelected = true,
                icon = {
                    Text(
                        text = "3",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                label = {
                    Text("Tasks")
                },
                onClick = {}
            )
        }
    }
} 
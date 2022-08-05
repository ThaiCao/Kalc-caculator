package com.kyant.kalc.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kyant.kalc.R
import com.kyant.kalc.data.AppViewModel
import com.kyant.kalc.theme.Fonts
import com.kyant.monet.a1
import com.kyant.monet.n1
import com.kyant.monet.withNight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppViewModel.History() {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(64.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .offset(y = 2.dp)
                        .clip(CircleShape)
                        .clickable { historyPanelExpanded = false }
                        .padding(8.dp)
                        .size(24.dp)
                )
                Text(
                    text = stringResource(id = R.string.history),
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Items(
                items = historyExpressions.toAnnotatedStrings(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) { _, exp, shapeModifier ->
                Column(
                    modifier = shapeModifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(100.n1 withNight 20.n1)
                        .combinedClickable(onLongClick = {
                            historyExpressions -= exp.text
                        }) {
                            expression = expression.copy(
                                annotatedString = exp,
                                selection = TextRange(exp.length)
                            )
                            historyPanelExpanded = false
                        }
                        .padding(24.dp, 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = exp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Fonts.googleSansFontFamily,
                        maxLines = 1,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    val equalSignColor = 40.a1 withNight 80.a1
                    Text(
                        text = buildAnnotatedString {
                            exp.text.calc()?.toString()?.let {
                                withStyle(SpanStyle(color = equalSignColor)) {
                                    append("= ")
                                }
                                append(it)
                            } ?: append("Invalid expression")
                        },
                        color = 40.n1 withNight 80.n1,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Fonts.googleSansFontFamily,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.Outlined.ClearAll,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(16.dp)
                .clip(CircleShape)
                .background(85.a1 withNight 80.a1)
                .clickable { historyExpressions.clear() }
                .padding(24.dp)
                .size(32.dp),
            tint = 0.n1
        )
    }
}

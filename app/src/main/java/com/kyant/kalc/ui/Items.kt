package com.kyant.kalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun <T> Items(
    items: List<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    itemContent: @Composable LazyItemScope.(index: Int, item: T, shapeModifier: Modifier) -> Unit
) {
    LazyColumn(
        modifier = modifier.clip(RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)),
        state = state,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(items) { index, item ->
            itemContent(
                index,
                item,
                if (index != items.lastIndex) Modifier
                else Modifier.clip(RoundedCornerShape(4.dp, 4.dp, 32.dp, 32.dp))
            )
        }
    }
}

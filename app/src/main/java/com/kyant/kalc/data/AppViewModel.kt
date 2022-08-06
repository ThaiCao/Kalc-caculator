package com.kyant.kalc.data

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import com.kyant.kalc.utils.mutableSaveableStateListOf
import com.kyant.monet.a1
import com.kyant.monet.withNight

class AppViewModel(application: Application) : AndroidViewModel(application) {
    var increment by mutableStateOf(0)

    var expression by mutableStateOf(TextFieldValue())

    var historyPanelExpanded by mutableStateOf(false)

    val historyExpressions = mutableSaveableStateListOf<String>("historyExpressions")

    @Composable
    fun List<String>.toAnnotatedStrings(): List<AnnotatedString> {
        val color = 40.a1 withNight 80.a1
        return try {
            map { exp ->
                if (exp.isNotEmpty()) {
                    buildAnnotatedString {
                        append(exp)
                        exp
                            .mapIndexed { index, c ->
                                index.takeIf { "[()%÷×+−^]".toRegex().matches(c.toString()) }
                            }
                            .filterNotNull()
                            .forEachIndexed { _, index ->
                                addStyle(SpanStyle(color = color), index, index + 1)
                            }
                    }
                } else buildAnnotatedString {}
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
}

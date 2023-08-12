package pl.sepka.mvvmrecipeapp.presentation.ui.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import pl.sepka.mvvmrecipeapp.presentation.components.GenericDialogInfo
import pl.sepka.mvvmrecipeapp.presentation.components.PositiveAction
import java.util.*

class DialogQueue {
    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    fun removeHeadMessage() {
        if (queue.value.isNotEmpty()) {
            val update = queue.value
            update.remove()
            queue.value = ArrayDeque()
            queue.value = update
        }
    }

    fun appendErrorMessage(title: String, description: String) {
        queue.value.offer(
            GenericDialogInfo.Builder()
                .title(title)
                .onDismiss(this::removeHeadMessage)
                .description(description)
                .positiveAction(
                    PositiveAction(
                        positiveBtnTxt = "OK",
                        onPositiveAction = this::removeHeadMessage
                    )
                )
                .build()
        )
    }
}

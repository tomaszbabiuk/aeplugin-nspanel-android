package ae.geekhome.panel.ui.message

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    fun onOptionClicked(optionIndex: Int) {
        //TODO
    }

    val options: Array<String>
    val content: String

    init {
        val params: String = savedStateHandle[MessageDestination.ARG_CBOR]!!
        val paramsDecoded = MessageDestination.decodeNavParams(params)
        content = paramsDecoded.content
        options = paramsDecoded.options
    }
}

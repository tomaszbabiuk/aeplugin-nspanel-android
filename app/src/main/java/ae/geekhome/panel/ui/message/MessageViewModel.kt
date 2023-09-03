package ae.geekhome.panel.ui.message

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val options: Array<String>
    private val title: String
    private val message: String

    init {
        val params: String = savedStateHandle[MessageDestination.ARG_CBOR]!!
        val paramsDecoded = MessageDestination.decodeNavParams(params)
        message = paramsDecoded.message
        title = paramsDecoded.title
        options = paramsDecoded.options
    }
}

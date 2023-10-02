package ae.geekhome.panel.ui.dialog

import ae.geekhome.panel.coap.impl.ActiveSceneResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DialogViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val activeSceneResource: ActiveSceneResource) : ViewModel() {

    private val sceneId = UUID.randomUUID().toString()

    init {
        activeSceneResource.newAction(sceneId)
    }

    fun onOptionClicked(optionIndex: Int) {
        activeSceneResource.newAction(sceneId, optionIndex)
    }

    val options: Array<String>
    val content: String

    init {
        val params: String = savedStateHandle[DialogDestination.ARG_CBOR]!!
        val paramsDecoded = DialogDestination.decodeNavParams(params)
        content = paramsDecoded.headline
        options = paramsDecoded.options
    }
}

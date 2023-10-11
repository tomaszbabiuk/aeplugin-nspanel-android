package ae.geekhome.panel.ui.dialog

import ae.geekhome.panel.coap.impl.ActiveSceneResource
import ae.geekhome.panel.navigation.RouteNavigator
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DialogViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val routeNavigator: RouteNavigator,
    private val activeSceneResource: ActiveSceneResource
) : ViewModel(), RouteNavigator by routeNavigator {

    init {
        activeSceneResource.newAction(activeSceneResource.sceneId)
    }

    fun onOptionClicked(optionIndex: Int) {
        activeSceneResource.newAction(activeSceneResource.sceneId, optionIndex)
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

package ae.geekhome.panel.coap

interface CoapService {
    enum class ServerState {
        Stopped,
        Starting,
        Listening,
        Stopping
    }

    interface ServerStateChangedListener {
        fun onServerStateChanged(state: ServerState)
    }

    fun start()
    fun stop()
    val port: Int
    var state: ServerState
    var stateListener: ServerStateChangedListener?
}

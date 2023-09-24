package ae.geekhome.panel.coap

interface CoapService {
    enum class ServerState {
        Stopped,
        Starting,
        Listening,
        Stopping
    }

    interface ServerStateChangedListener {
        suspend fun onServerStateChanged(state: ServerState)
    }

    suspend fun start()
    suspend fun stop()
    val port: Int
    var state: ServerState
    var stateListener: ServerStateChangedListener?
}

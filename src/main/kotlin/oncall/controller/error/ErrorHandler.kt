package oncall.controller.error

fun interface ErrorHandler {
    fun handle(action: () -> Unit, callback: () -> Unit, write: (message: String) -> Unit)
}
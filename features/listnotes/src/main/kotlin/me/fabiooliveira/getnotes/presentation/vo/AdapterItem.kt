package me.fabiooliveira.getnotes.presentation.vo

internal data class AdapterItem<F, S>(
        val first: F? = null,
        val second: S? = null,
        val viewType: ViewType
) {
    enum class ViewType(val type: Int) {
        HEADER(0),
        ITEM(1)
    }
}
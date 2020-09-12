package me.fabiooliveira.getnotes.base

internal interface Mapper<S, T> {
    fun map(source: S): T
}
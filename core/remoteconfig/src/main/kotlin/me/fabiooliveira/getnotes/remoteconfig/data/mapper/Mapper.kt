package me.fabiooliveira.getnotes.remoteconfig.data.mapper

interface Mapper<S, T> {
    fun transform(source: S): T
}
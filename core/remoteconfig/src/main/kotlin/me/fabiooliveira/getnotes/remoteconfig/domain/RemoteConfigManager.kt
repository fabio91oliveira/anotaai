package me.fabiooliveira.getnotes.remoteconfig.domain

interface RemoteConfigManager {
    fun fetchAndActive()
    fun <T> getDynamicObject(key: String, clazz: Class<T>): T?
}
package me.fabiooliveira.anotaai.vo

import me.fabiooliveira.anotaai.enum.StatusEnum

data class Resource<out T>(val statusEnum: StatusEnum, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(StatusEnum.SUCCESS, data, null)
        }
        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(StatusEnum.ERROR, data, msg)
        }
    }
}
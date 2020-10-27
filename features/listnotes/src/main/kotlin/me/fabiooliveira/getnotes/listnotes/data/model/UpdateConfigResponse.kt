package me.fabiooliveira.getnotes.listnotes.data.model


import com.google.gson.annotations.SerializedName

data class UpdateConfigResponse(
        @SerializedName("force_update")
        val forceUpdate: Boolean?,
        @SerializedName("latest_version_code")
        val latestVersionCode: Long?
)
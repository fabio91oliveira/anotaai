package me.fabiooliveira.getnotes.listnotes.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class UpdateConfig(
        val latestVersionCode: Long,
        val forceUpdate: Boolean
) : Parcelable
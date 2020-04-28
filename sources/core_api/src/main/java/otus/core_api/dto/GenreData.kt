package otus.core_api.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreData(
    val id: Int,
    val name: String
) : Parcelable

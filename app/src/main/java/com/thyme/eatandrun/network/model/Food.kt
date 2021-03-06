package com.thyme.eatandrun.network.model

import android.os.Parcelable
import com.thyme.eatandrun.foodNameForLayout
import com.thyme.eatandrun.foodNameToShortString
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food (
    val foodId: String = "",
    val label: String = "",
    val nutrients: Nutrients,
    val category: String = "",
    val categoryLabel: String = ""
) : Parcelable {
    val shortName
        get() = label.foodNameToShortString()

    val layoutName
        get() = label.foodNameForLayout()

}

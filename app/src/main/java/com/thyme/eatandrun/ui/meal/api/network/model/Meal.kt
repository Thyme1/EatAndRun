package com.thyme.eatandrun.ui.meal.api.network.model

import android.os.Parcelable
import com.thyme.eatandrun.utils.foodNameForLayout
import com.thyme.eatandrun.utils.foodNameToShortString
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Meal (
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

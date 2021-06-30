package com.thyme.eatandrun.ui.meal.api.network.model

import android.os.Parcelable
import com.thyme.eatandrun.utils.mealNameForLayout
import com.thyme.eatandrun.utils.mealNameToShortString
import kotlinx.parcelize.Parcelize




@Parcelize
data class Meal (
    val foodId: String = "",
    val label: String = "",
    val nutrients: Nutrients,
    val category: String = "",
    val categoryLabel: String = ""
) : Parcelable {

    val layoutName
        get() = label.mealNameForLayout()

}

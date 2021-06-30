package com.thyme.eatandrun.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun String.mealNameToShortString():String {
    if (this.length <= 20)
        return this
    return this.substring(0, 20) + "..."
}

fun String.mealNameForLayout(): String {
    if (this.length <= 35)
        return this
    return this.substring(0, 35) + "..."
}

fun Double.toKcalString(): String {
    val kcalString = " kcal"

    val doubleAsString = this.toString()
    val indexOfDecimal = doubleAsString.indexOf(".")

    if (doubleAsString.substring(indexOfDecimal + 1) == "0")
        return doubleAsString.substring(0, indexOfDecimal) + kcalString

    return doubleAsString.substring(0, indexOfDecimal + 2)  + kcalString
}

fun getCurrentDayString(): String {
    val date = Date();
    val formatter = SimpleDateFormat("MMM-dd-yyyy")
    return formatter.format(date)
}

fun doublesToIntOrOne(a: Double, b: Double, c: Double): Int {
    if ((a + b + c).roundToInt() > 0) {
        return (a + b + c).roundToInt()
    }
    return 1
}

enum class Month {
    JAN,
    FEB,
    MAR,
    APR,
    MAY,
    JUN,
    JUL,
    AUG,
    SEP,
    OCT,
    NOV,
    DEC
}

enum class MonthComplet {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOMBER,
    NOVEMBER,
    DECEMBER
}

fun getMonthString(month: Int): String = when(month) {
    1 -> Month.JAN.name.lowercase(Locale.getDefault())
    2 -> Month.FEB.name.lowercase(Locale.getDefault())
    3 -> Month.MAR.name.lowercase(Locale.getDefault())
    4 -> Month.APR.name.lowercase(Locale.getDefault())
    5 -> Month.MAY.name.lowercase(Locale.getDefault())
    6 -> Month.JUN.name.lowercase(Locale.getDefault())
    7 -> Month.JUL.name.lowercase(Locale.getDefault())
    8 -> Month.AUG.name.lowercase(Locale.getDefault())
    9 -> Month.SEP.name.lowercase(Locale.getDefault())
    10 -> Month.OCT.name.lowercase(Locale.getDefault())
    11 -> Month.NOV.name.lowercase(Locale.getDefault())
    12 -> Month.DEC.name.lowercase(Locale.getDefault())
    else -> ""
}

fun String.monthToMonthComplete(): String = when(this) {
    Month.JAN.name.lowercase(Locale.getDefault()) -> MonthComplet.JANUARY.name.lowercase(Locale.getDefault())
    Month.FEB.name.lowercase(Locale.getDefault()) -> MonthComplet.FEBRUARY.name.lowercase(Locale.getDefault())
    Month.MAR.name.lowercase(Locale.getDefault()) -> MonthComplet.MARCH.name.lowercase(Locale.getDefault())
    Month.APR.name.lowercase(Locale.getDefault()) -> MonthComplet.APRIL.name.lowercase(Locale.getDefault())
    Month.MAY.name.lowercase(Locale.getDefault()) -> MonthComplet.MAY.name.lowercase(Locale.getDefault())
    Month.JUN.name.lowercase(Locale.getDefault()) -> MonthComplet.JUNE.name.lowercase(Locale.getDefault())
    Month.JUL.name.lowercase(Locale.getDefault()) -> MonthComplet.JULY.name.lowercase(Locale.getDefault())
    Month.AUG.name.lowercase(Locale.getDefault()) -> MonthComplet.AUGUST.name.lowercase(Locale.getDefault())
    Month.SEP.name.lowercase(Locale.getDefault()) -> MonthComplet.SEPTEMBER.name.lowercase(Locale.getDefault())
    Month.OCT.name.lowercase(Locale.getDefault()) -> MonthComplet.OCTOMBER.name.lowercase(Locale.getDefault())
    Month.NOV.name.lowercase(Locale.getDefault()) -> MonthComplet.NOVEMBER.name.lowercase(Locale.getDefault())
    Month.DEC.name.lowercase(Locale.getDefault()) -> MonthComplet.DECEMBER.name.lowercase(Locale.getDefault())
    else -> ""
}


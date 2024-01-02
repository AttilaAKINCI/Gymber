package com.akinci.gymber.core.utils.distance

import com.akinci.gymber.core.location.Coordinate
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DistanceUtils @Inject constructor(
    private val distanceCalculator: DistanceCalculator,
) {

    /**
     *  calculateDistance provides calculated distance and it's text value
     * **/
    fun calculateDistance(from: Coordinate, to: Coordinate): Distance? {
        return distanceCalculator.calculateFlightDistance(from, to)?.let { distance ->
            Distance(value = distance, valueText = distance.toText())
        }
    }

    /**
     *  Converts meter distance to KM or M equivalent.
     * **/
    private fun Float.toText() = if (this >= 1000) {
        val formattedDistance = if (this.toInt() % 1000 == 0) {
            String.format("%.0f", this / 1000)
        } else {
            distanceFormatter.format(this / 1000)
        }
        "$formattedDistance km"
    } else {
        "${String.format("%.0f", this)} m"
    }

    private val distanceFormatter by lazy {
        DecimalFormat("0.0").apply {
            decimalFormatSymbols = DecimalFormatSymbols().also { it.decimalSeparator = '.' }
        }
    }
}

package com.akinci.gymber.core.utils

import com.akinci.gymber.domain.Gym

object GymMatchProvider {

    // TODO refactor

    /**
     * this function simulates create a match pattern for user
     * matchPercent should be in range [0, 1]
     *
     * --> matchPercent should be fetch with app config service and it can be used with different values
     * --> it corresponds A/B testing so on.
     * --> For this case it means %20 of items will be matched by default
     * **/
    fun createAMatchPattern(gyms: List<Gym>, matchPercent: Float = 0.2f): MutableList<Gym> {
        val percentage = matchPercent.coerceIn(0f, 1f)

        val countOfItemShouldBeMatched = (gyms.size * percentage).toInt()
        val indexs = (gyms.indices).toMutableList()

        for (turn in (0 until countOfItemShouldBeMatched)) {
            val ind = indexs.indices.random()
            indexs.removeAt(ind)
        }

        return gyms.toMutableList()
    }

}
package com.akinci.gymber.common.helper

import com.akinci.gymber.data.output.Partner

object PartnerMatchProvider {

    /**
     * this function simulates create a match pattern for user
     * matchPercent should be in range [0, 1]
     *
     * --> matchPercent should be fetch with app config service and it can be used with different values
     * --> it corresponds A/B testing so on.
     * --> For this case it means %20 of items will be matched by default
     * **/
    fun createAMatchPattern(partnerList: List<Partner>, matchPercent: Float = 0.2f): MutableList<Partner> {
        val percentage = when{
            matchPercent < 0.2f -> { 0f }
            matchPercent > 1f -> { 1f }
            else -> { matchPercent }
        }

        val countOfItemShouldBeMatched = (partnerList.size * percentage).toInt()
        val indexs = (partnerList.indices).toMutableList()

        for (turn in (0 until countOfItemShouldBeMatched)){
            val ind = indexs.indices.random()
            partnerList[indexs[ind]].isAMatch = true
            indexs.removeAt(ind)
        }

        return partnerList.toMutableList()
    }
}
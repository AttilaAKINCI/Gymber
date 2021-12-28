package com.akinci.gymber.common.helper

import com.akinci.gymber.data.output.Partner

object PartnerMatchProvider {

    /** this function simulates create a match pattern for user  **/
    fun createAMatchPattern(partnerList: List<Partner>): MutableList<Partner> {
        val countOfItemShouldBeMatched = partnerList.size / 5  // it means %20 of items will be matched
        val indexs = (partnerList.indices).toMutableList()

        for (turn in (0 until countOfItemShouldBeMatched)){
            val ind = indexs.indices.random()
            partnerList[indexs[ind]].isAMatch = true
            indexs.removeAt(ind)
        }

        return partnerList.toMutableList()
    }

}
package com.akinci.gymber.core.utils

import javax.inject.Inject
import kotlin.random.Random

/**
 *  GymMatchSimulator simulates user swipe and gym matches by %20 percentage.
 * **/
class GymMatchSimulator @Inject constructor() {

    // on this part we are simulating match chance by %20.
    fun isItAMatch() = Random.nextInt(0, 100) < 20
}

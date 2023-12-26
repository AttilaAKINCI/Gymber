package com.akinci.gymber.domain

import com.akinci.gymber.data.GymRepository
import com.akinci.gymber.domain.mapper.toDomain
import javax.inject.Inject

class GymUseCase @Inject constructor(
    private val gymRepository: GymRepository,
) {

    suspend fun getGyms() = gymRepository.getGyms().map { it.toDomain() }
}
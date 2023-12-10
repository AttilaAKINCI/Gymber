package com.akinci.gymber.domain

import com.akinci.gymber.data.PartnerRepository
import javax.inject.Inject

class PartnerUseCase @Inject constructor(
    private val partnerRepository: PartnerRepository,
) {

    suspend fun getPartners() = partnerRepository.getPartners()
}
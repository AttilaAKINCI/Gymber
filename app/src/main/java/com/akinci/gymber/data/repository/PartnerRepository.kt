package com.akinci.gymber.data.repository

import com.akinci.gymber.common.network.NetworkResponse
import com.akinci.gymber.common.repository.BaseRepository
import com.akinci.gymber.data.api.PartnerServiceDao
import com.akinci.gymber.data.output.PartnerListResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PartnerRepository @Inject constructor(
    private val partnerServiceDao: PartnerServiceDao,
    private val baseRepository: BaseRepository
){

    suspend fun getPartnerList(): Flow<NetworkResponse<PartnerListResponse>> =
            baseRepository.callServiceAsFlow { partnerServiceDao.getPartnerList() }

}
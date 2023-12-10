package com.akinci.gymbercompose.data.repository

import com.akinci.gymbercompose.common.network.NetworkResponse
import com.akinci.gymbercompose.common.repository.BaseRepository
import com.akinci.gymbercompose.data.api.PartnerServiceDao
import com.akinci.gymbercompose.data.output.PartnerListResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PartnerRepository @Inject constructor(
    private val partnerServiceDao: PartnerServiceDao,
    private val baseRepository: BaseRepository
){

    suspend fun getPartnerList(): Flow<NetworkResponse<PartnerListResponse>> =
            baseRepository.callServiceAsFlow { partnerServiceDao.getPartnerList() }

}
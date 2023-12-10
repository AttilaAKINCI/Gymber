package com.akinci.gymbercompose.ui.feature.dashboard.viewmodel

import com.akinci.gymbercompose.common.network.NetworkResponse
import com.akinci.gymbercompose.coroutine.TestContextProvider
import com.akinci.gymbercompose.data.output.PartnerListResponse
import com.akinci.gymbercompose.data.repository.PartnerRepository
import com.akinci.gymbercompose.jsonresponses.GetPartnerListResponse
import com.squareup.moshi.Moshi
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    @MockK
    lateinit var partnerRepository: PartnerRepository

    private lateinit var dashboardViewModel: DashboardViewModel
    private val moshi = Moshi.Builder().build()

    private val coroutineContextProvider = TestContextProvider()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        val data: PartnerListResponse = moshi.adapter(PartnerListResponse::class.java).fromJson(GetPartnerListResponse.partnerList)!!
        coEvery { partnerRepository.getPartnerList() } returns flow { emit(NetworkResponse.Success(data)) }

        dashboardViewModel = DashboardViewModel(coroutineContextProvider, partnerRepository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get Partner List returns data`() = runBlockingTest {

        /** call back should be fired. **/
        coVerify (exactly = 1) { partnerRepository.getPartnerList() }
        confirmVerified(partnerRepository)
    }

}
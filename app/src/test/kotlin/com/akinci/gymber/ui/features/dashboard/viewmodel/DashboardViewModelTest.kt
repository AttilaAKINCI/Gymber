package com.akinci.gymber.ui.features.dashboard.viewmodel

import com.akinci.gymber.coroutine.TestContextProvider
import com.akinci.gymber.data.remote.PartnerListServiceResponse
import com.akinci.gymber.data.PartnerRepository
import com.akinci.gymber.jsonresponses.GetPartnerListResponse
import com.akinci.gymber.ui.features.dashboard.DashboardViewModel
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

        val data: PartnerListServiceResponse = moshi.adapter(PartnerListServiceResponse::class.java).fromJson(GetPartnerListResponse.partnerList)!!
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
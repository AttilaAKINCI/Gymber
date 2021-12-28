package com.akinci.gymber.feature.dasboard.viewmodel

import app.cash.turbine.test
import com.akinci.gymber.common.helper.state.ListState
import com.akinci.gymber.common.helper.state.UIState
import com.akinci.gymber.common.network.NetworkChecker
import com.akinci.gymber.common.network.NetworkResponse
import com.akinci.gymber.coroutine.TestContextProvider
import com.akinci.gymber.data.output.PartnerListResponse
import com.akinci.gymber.data.repository.PartnerRepository
import com.akinci.gymber.feature.dashboard.viewmodel.DashboardViewModel
import com.akinci.gymber.jsonresponses.GetPartnerListResponse
import com.google.common.truth.Truth
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

    @MockK
    lateinit var networkChecker: NetworkChecker

    private lateinit var dashboardViewModel: DashboardViewModel
    private val moshi = Moshi.Builder().build()

    private val coroutineContextProvider = TestContextProvider()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        dashboardViewModel = DashboardViewModel(coroutineContextProvider, partnerRepository, networkChecker)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get Partner List returns loading`() = runBlockingTest {
        coEvery { partnerRepository.getPartnerList() } returns flow { emit(NetworkResponse.Loading()) }

        dashboardViewModel.getPartnerList()
        coroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle() // helps if your code has delay()

        dashboardViewModel.partnerListData.test {
            val item = awaitItem()
            Truth.assertThat(item).isInstanceOf(ListState.OnLoading::class.java)
            cancelAndIgnoreRemainingEvents()
        }

        /** call back should be fired. **/
        coVerify (exactly = 1) { partnerRepository.getPartnerList() }
        confirmVerified(partnerRepository)
    }

    @Test
    fun `get Partner List returns error`() = runBlockingTest {
        coEvery { partnerRepository.getPartnerList() } returns flow { emit(NetworkResponse.Error("Error")) }

        dashboardViewModel.getPartnerList()
        coroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle() // helps if your code has delay()

        dashboardViewModel.uiState.test {
            val item = awaitItem()
            Truth.assertThat(item).isInstanceOf(UIState.OnServiceError::class.java)
            cancelAndIgnoreRemainingEvents()
        }

        /** call back should be fired. **/
        coVerify (exactly = 1) { partnerRepository.getPartnerList() }
        confirmVerified(partnerRepository)
    }

    @Test
    fun `get Partner List returns network error`() = runBlockingTest {
        coEvery { partnerRepository.getPartnerList() } returns flow { emit(NetworkResponse.NetworkError) }

        dashboardViewModel.getPartnerList()
        coroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle() // helps if your code has delay()

        dashboardViewModel.uiState.test {
            val item = awaitItem()
            Truth.assertThat(item).isInstanceOf(UIState.OnNetworkError::class.java)
            cancelAndIgnoreRemainingEvents()
        }

        /** call back should be fired. **/
        coVerify (exactly = 1) { partnerRepository.getPartnerList() }
        confirmVerified(partnerRepository)
    }

    @Test
    fun `get Partner List returns data`() = runBlockingTest {
        val data: PartnerListResponse = moshi.adapter(PartnerListResponse::class.java).fromJson(GetPartnerListResponse.partnerList)!!

        coEvery { partnerRepository.getPartnerList() } returns flow { emit(NetworkResponse.Success(data)) }

        dashboardViewModel.getPartnerList()
        coroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle() // helps if your code has delay()

        dashboardViewModel.partnerListData.test {
            val item = awaitItem()
            Truth.assertThat(item).isInstanceOf(ListState.OnData::class.java)
            Truth.assertThat((item as ListState.OnData).data).isNotNull()
            Truth.assertThat(item.data!!.size).isEqualTo(data.data.size)
            cancelAndIgnoreRemainingEvents()
        }

        /** call back should be fired. **/
        coVerify (exactly = 1) { partnerRepository.getPartnerList() }
        confirmVerified(partnerRepository)
    }

    @Test
    fun `remove a item`() = runBlockingTest {
        val data: PartnerListResponse = moshi.adapter(PartnerListResponse::class.java).fromJson(GetPartnerListResponse.partnerList)!!

        coEvery { partnerRepository.getPartnerList() } returns flow { emit(NetworkResponse.Success(data)) }

        // fill partnerListData first
        dashboardViewModel.getPartnerList()

        dashboardViewModel.removeItem()
        coroutineContextProvider.testCoroutineDispatcher.advanceUntilIdle() // helps if your code has delay()

        dashboardViewModel.partnerListData.test {
            val item = awaitItem()
            Truth.assertThat(item).isInstanceOf(ListState.OnData::class.java)
            Truth.assertThat((item as ListState.OnData).data).isNotNull()
            Truth.assertThat(item.data!!.size).isEqualTo(data.data.size-1) // top item should be removed
            cancelAndIgnoreRemainingEvents()
        }

        /** call back should be fired. **/
        coVerify (exactly = 1) { partnerRepository.getPartnerList() }
        confirmVerified(partnerRepository)
    }

}
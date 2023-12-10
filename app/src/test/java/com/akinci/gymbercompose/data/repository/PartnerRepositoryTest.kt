package com.akinci.gymbercompose.data.repository

import com.akinci.gymbercompose.common.network.NetworkChecker
import com.akinci.gymbercompose.common.network.NetworkResponse
import com.akinci.gymbercompose.common.network.NetworkState
import com.akinci.gymbercompose.common.repository.BaseRepository
import com.akinci.gymbercompose.data.api.PartnerServiceDao
import com.akinci.gymbercompose.data.output.PartnerListResponse
import com.akinci.gymbercompose.jsonresponses.GetPartnerListResponse
import com.google.common.truth.Truth
import com.squareup.moshi.Moshi
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class PartnerRepositoryTest {

    @MockK
    lateinit var partnerServiceDao: PartnerServiceDao

    @MockK
    lateinit var networkChecker: NetworkChecker

    private lateinit var repository : PartnerRepository
    private val moshi = Moshi.Builder().build()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = PartnerRepository(
            partnerServiceDao,
            BaseRepository(networkChecker)
        )
    }

    @AfterEach
    fun tearDown() { unmockkAll() }

    @Test
    fun `Network is ok, getPartnerList function is called, returns NetworkResponse-Success for success`() = runBlockingTest {
        val data =  moshi.adapter(PartnerListResponse::class.java).fromJson(GetPartnerListResponse.partnerList)

        every { networkChecker.networkState.value } returns NetworkState.Connected
        coEvery { partnerServiceDao.getPartnerList() } returns Response.success(data)

        val flowResponse = repository.getPartnerList()

        launch {
            flowResponse.collect { response ->
                /** ignore first loading state **/
                if(response is NetworkResponse.Loading){ return@collect }

                /** repository function response type should be NetworkResponse.Success **/
                Truth.assertThat(response).isInstanceOf(NetworkResponse.Success::class.java)

                val fetchedResponse = (response as NetworkResponse.Success).data
                Truth.assertThat(fetchedResponse).isInstanceOf(PartnerListResponse::class.java)

                Truth.assertThat(
                    (fetchedResponse as PartnerListResponse).data.size
                ).isEqualTo(
                    data?.data?.size
                )

                /** call back should be fired. **/
                coVerify (exactly = 1) { partnerServiceDao.getPartnerList() }
                confirmVerified(partnerServiceDao)
            }
        }
    }

}
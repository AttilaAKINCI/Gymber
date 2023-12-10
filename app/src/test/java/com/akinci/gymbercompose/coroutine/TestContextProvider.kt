package com.akinci.gymbercompose.coroutine

import com.akinci.gymbercompose.common.coroutine.CoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestContextProvider(val testCoroutineDispatcher : TestCoroutineDispatcher = TestCoroutineDispatcher()): CoroutineContextProvider() {
    override val Main: CoroutineContext = testCoroutineDispatcher
    override val IO: CoroutineContext = testCoroutineDispatcher
}

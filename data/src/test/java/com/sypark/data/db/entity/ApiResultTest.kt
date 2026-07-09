package com.sypark.data.db.entity

import com.sypark.domain.model.ApiResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class ApiResultTest {

    @Test
    fun `safeFlow emits Success when service succeeds`() = runTest {
        safeFlow { "ok" }.collect { result ->
            assertTrue(result is ApiResult.Success)
        }
    }

    @Test
    fun `safeFlow emits Error when service throws NullPointerException`() = runTest {
        safeFlow<String> { throw NullPointerException("boom") }.collect { result ->
            assertTrue("expected Error but was $result", result is ApiResult.Error)
        }
    }

    @Test
    fun `safeFlow emits Error when service throws a generic exception`() = runTest {
        safeFlow<String> { throw IllegalStateException("boom") }.collect { result ->
            assertTrue(result is ApiResult.Error)
        }
    }
}

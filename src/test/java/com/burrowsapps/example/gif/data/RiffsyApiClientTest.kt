package com.burrowsapps.example.gif.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.burrowsapps.example.gif.data.model.RiffsyResponseDto
import com.burrowsapps.example.gif.di.module.NetModule
import com.burrowsapps.example.gif.di.module.RiffsyModule
import com.google.common.truth.Truth.assertThat
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import test.TestFileUtils.MOCK_SERVER_PORT
import test.TestFileUtils.getMockResponse
import java.net.HttpURLConnection.HTTP_NOT_FOUND

@RunWith(AndroidJUnit4::class)
class RiffsyApiClientTest {
  private val server = MockWebServer()
  private val dispatcher = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse = when {
      request.path!!.contains("/v1/trending") -> getMockResponse("/trending_results.json")
      request.path!!.contains("/v1/search") -> getMockResponse("/search_results.json")
      else -> MockResponse().setResponseCode(HTTP_NOT_FOUND)
    }
  }
  private lateinit var sut: RiffsyApiClient

  @Before fun setUp() {
    server.start(MOCK_SERVER_PORT)
    server.dispatcher = dispatcher

    sut = RiffsyModule(server.url("/").toString()).provideRiffsyApi(
      NetModule.provideRetrofit(ApplicationProvider.getApplicationContext())
    )
  }

  @After fun tearDown() {
    server.shutdown()
  }

  @Test fun testTrendingResultsURLShouldParseCorrectly() {
    val observer = TestObserver<RiffsyResponseDto>()

    val observable = sut.getTrendingResults(RiffsyApiClient.DEFAULT_LIMIT_COUNT, null)
    val response = observable.blockingFirst()
    observer.assertNoErrors()

    assertThat(response.results[0].media[0].gif.url)
      .contains("/images/7d95a1f8a8750460a82b04451be26d69/raw")
  }

  @Test fun testTrendingResultsURLPreviewShouldParseCorrectly() {
    val observer = TestObserver<RiffsyResponseDto>()

    val observable = sut.getTrendingResults(RiffsyApiClient.DEFAULT_LIMIT_COUNT, null)
    val response = observable.blockingFirst()
    observer.assertNoErrors()

    assertThat(response.results[0].media[0].gif.preview)
      .contains("/images/511fdce5dc8f5f2b88ac2de6c74b92e7/raw")
  }

  @Test fun testSearchResultsURLShouldParseCorrectly() {
    val observer = TestObserver<RiffsyResponseDto>()

    val observable = sut.getSearchResults("hello", RiffsyApiClient.DEFAULT_LIMIT_COUNT, null)
    val response = observable.blockingFirst()
    observer.assertNoErrors()

    assertThat(response.results[0].media[0].gif.url)
      .contains("/images/6088f94e6eb5dd7584dedda0fe1e52e1/raw")
  }

  @Test fun testSearchResultsURLPreviewShouldParseCorrectly() {
    val observer = TestObserver<RiffsyResponseDto>()

    val observable = sut.getSearchResults("hello", RiffsyApiClient.DEFAULT_LIMIT_COUNT, null)
    val response = observable.blockingFirst()
    observer.assertNoErrors()

    assertThat(response.results[0].media[0].gif.preview)
      .contains("/images/6f2ed339fbdb5c1270e29945ee1f0d77/raw")
  }
}

package com.burrowsapps.example.gif

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SchedulerProviderTest {
  private val sut = SchedulerProvider()

  @Test fun testIo() {
    assertThat(sut.io()).isEqualTo(Schedulers.io())
  }

  @Test fun testUi() {
    assertThat(sut.ui()).isEqualTo(AndroidSchedulers.mainThread())
  }
}

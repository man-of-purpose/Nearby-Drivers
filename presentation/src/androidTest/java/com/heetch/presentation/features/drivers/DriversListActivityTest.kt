package com.heetch.presentation.features.drivers

import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.heetch.domain.usecase.drivers.GetAddressFromLocationUseCase
import com.heetch.domain.usecase.drivers.GetNearbyDriversUseCase
import com.heetch.presentation.HeetchApplicationTest
import com.heetch.presentation.R
import com.heetch.presentation.features.drivers.activity.DriversListActivity
import com.heetch.presentation.features.drivers.viewmodel.DriversListViewModel
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest

class DriversListActivityTest : KoinTest {

    private lateinit var app: HeetchApplicationTest

    @Before
    fun init() {
        app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as HeetchApplicationTest
    }

    @Test
    fun testViews_isDisplayed() {

        val modules = module {
            single { mockk<GetNearbyDriversUseCase>(relaxed = true) }
            single { mockk<GetAddressFromLocationUseCase>(relaxed = true) }
            viewModel { DriversListViewModel(get(), get()) }
        }

        app.loadModules(modules) {

            ActivityScenario.launch(DriversListActivity::class.java)
            Screen.onScreen<DriversListScreen> {
                currentAddressTextView.isDisplayed()
                floatingActionButton.isClickable()
                addressSnapshotImageView.isDisplayed()
            }

            Screen.idle(3000)
        }
    }

    class DriversListScreen : Screen<DriversListScreen>() {
        val floatingActionButton = KButton { withId(R.id.drivers_fab) }
        val currentAddressTextView = KTextView { withId(R.id.yourCurrentAddressLabel) }
        val addressSnapshotImageView = KImageView { withId(R.id.address_snapshot) }
    }
}
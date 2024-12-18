package com.example.budgetbuddy.viewModels

import com.example.budgetbuddy.communication.IExchangeRateRemoteRepository
import com.example.budgetbuddy.model.PrimaryColor
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.datastore.DataStoreRepositoryImpl
import com.example.budgetbuddy.ui.screens.settings.SettingsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle

@RunWith(JUnit4::class)
class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private val authService: AuthService = mockk(relaxed = true)
    private val exchangeRateRepository: IExchangeRateRemoteRepository =
        mockk(relaxed = true)

    val mockDataStore = mockk<DataStoreRepositoryImpl>(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runBlocking{
        Dispatchers.setMain(StandardTestDispatcher())

        MockitoAnnotations.initMocks(this)
        viewModel = SettingsViewModel(
            authService,
            mockDataStore,
            exchangeRateRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() = runBlocking {
        Dispatchers.resetMain()
    }

    @Test
    fun `test toggleDarkMode calls getIsDarkTheme and setDarkTheme`() = runTest {
        val defaultModeValue = viewModel.isDarkMode.value
        viewModel.changeMode()

        advanceUntilIdle()

        coVerify { mockDataStore.setDarkTheme(!defaultModeValue) }

        val newValue = viewModel.isDarkMode.value
        assertTrue(newValue != defaultModeValue)
    }

    @Test
    fun `changeCurrency updates selected currency`() = runTest {
        coEvery { mockDataStore.getCurrency() } returns flowOf("USD")
        viewModel.changeCurrency("USD")

        advanceUntilIdle()

        coVerify { mockDataStore.setCurrency("USD") }

        assertTrue(viewModel.activeCurrency.value == "USD")

        val collectedCurrency = mockDataStore.getCurrency().first()
        assertTrue(collectedCurrency == "USD")
    }

    @Test
    fun `changePrimaryColor updates primary color`() = runTest {
        coEvery { mockDataStore.getPrimaryColor() } returns flowOf(PrimaryColor.BLUE.name)

        viewModel.changePrimaryColor(PrimaryColor.BLUE)

        advanceUntilIdle()

        coVerify { mockDataStore.setPrimaryColor(PrimaryColor.BLUE) }

        assertTrue(viewModel.primaryColor.value == PrimaryColor.BLUE)

        val selectedColor = mockDataStore.getPrimaryColor().first()
        assertTrue(selectedColor == PrimaryColor.BLUE.name)
    }
}

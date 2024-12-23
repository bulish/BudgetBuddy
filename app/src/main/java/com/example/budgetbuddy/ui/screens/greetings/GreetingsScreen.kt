package com.example.budgetbuddy.ui.screens.greetings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetbuddy.R
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.greetings.DotsIndicator
import com.example.budgetbuddy.ui.elements.greetings.GreetingButton
import com.example.budgetbuddy.ui.elements.greetings.ItemGreeting
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.example.budgetbuddy.ui.theme.LightGrey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GreetingsScreen(
    navigationRouter: INavigationRouter
) {
    val viewModel = hiltViewModel<GreetingsViewModel>()

    val greetings = listOf(
        Triple(
            R.raw.animation_01,
            stringResource(R.string.greetings_first_title),
            stringResource(R.string.greetings_first_subtitle)
        ),
        Triple(
            R.raw.animation_02,
            stringResource(R.string.greetings_second_title),
            stringResource(R.string.greetings_second_subtitle)
        ),
        Triple(
            R.raw.animation_03,
            stringResource(R.string.greetings_third_title),
            stringResource(R.string.greetings_third_subtitle)
        ),
        Triple(
            R.raw.animation_01,
            stringResource(R.string.greetings_fourth_title),
            stringResource(R.string.greetings_fourth_subtitle)
        )
    )

    var buttonName by rememberSaveable { mutableStateOf(R.string.next) }
    var visibleSkip by remember { mutableStateOf(true) }

    val pagerState = rememberPagerState { greetings.size }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page < 3) {
                visibleSkip = true
                buttonName = R.string.next
            } else {
                visibleSkip = false
                buttonName = R.string.get_started
            }
        }
    }

    GreetingsScreenContent(
        pagerState = pagerState,
        greetings = greetings,
        buttonName = stringResource(id = buttonName),
        scope = scope,
        visibleSkip = visibleSkip,
        navigateToSignUp = {
            navigationRouter.navigateToSignUpScreen()
            viewModel.setFirstRun()
        },
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GreetingsScreenContent(
    pagerState: PagerState,
    greetings: List<Triple<Int, String, String>>,
    buttonName: String,
    scope: CoroutineScope,
    visibleSkip: Boolean,
    navigateToSignUp: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(bottom = DoubleMargin())
    ) {

        HorizontalPager(
            modifier = Modifier
                .weight(1f)
                .padding(top = BasicMargin(), bottom = BasicMargin()),
            state = pagerState,
            key = { greetings[it] },
            pageSize = PageSize.Fill
        ) {
            ItemGreeting(greetings[it])
        }

        DotsIndicator(
            totalDots = greetings.size,
            selectedIndex = pagerState.currentPage,
            selectedColor = MaterialTheme.colorScheme.primary,
            unSelectedColor = LightGrey
        )

        Spacer(modifier = Modifier.height(BasicMargin()))

        GreetingButton(
            title = buttonName,
            modifier = Modifier.padding(horizontal = BasicMargin())
        ) {
            if (pagerState.currentPage == 0 || pagerState.currentPage < 3) {
                scope.launch {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage + 1
                    )
                }
            } else {
                navigateToSignUp()
            }
        }

        Spacer(modifier = Modifier.height(BasicMargin()))

        if (visibleSkip)
            Text(
                text = stringResource(R.string.skip),
                modifier = Modifier
                    .height(54.dp)
                    .padding(horizontal = HalfMargin(), vertical = HalfMargin())
                    .clickable {
                        navigateToSignUp()
                    },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        else Spacer(modifier = Modifier.height(DoubleMargin()))
    }
}

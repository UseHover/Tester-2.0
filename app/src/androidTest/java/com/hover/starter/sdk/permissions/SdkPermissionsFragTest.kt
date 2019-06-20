package com.hover.starter.sdk.permissions

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.hover.sdk.permissions.PermissionFragment
import com.hover.starter.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@LargeTest
class SdkPermissionsFragTest {

//    @Test
//    fun showsPermissionsSummary() {
//        val scenario = launchFragmentInContainer<PermissionFragment>()
//
//        scenario.onFragment { fragment ->
////            assertThat(fragment.dialog).isNotNull()
////            assertThat(fragment.requireDialog().isShowing).isTrue()
//        }
//
//        onView(withText(R.string.hsdk_perms_needed)).check(matches(isDisplayed()))
//        onView(withText(R.string.hsdk_want_phone)).check(matches(isDisplayed()))
//        onView(withText(R.string.hsdk_want_sms)).check(matches(isDisplayed()))
//        onView(withText(R.string.hsdk_want_overlay)).check(matches(isDisplayed()))
//        onView(withText(R.string.hsdk_want_accessibility)).check(matches(isDisplayed()))
//    }
}
package com.hover.starter.sdk.permissions;

import android.Manifest;
import android.app.UiAutomation;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.permission.PermissionRequester;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverHelper;
import com.hover.sdk.permissions.PermissionActivity;
import com.hover.sdk.permissions.PermissionHelper;
import com.hover.starter.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SdkPermissionSummaryTest {

	@Rule
	public ActivityScenarioRule<PermissionActivity> activityScenarioRule = new ActivityScenarioRule<>(PermissionActivity.class);

	@Test
	public void showsPermissionSummary() {
		activityScenarioRule.getScenario().recreate();
		onView(withText(R.string.hsdk_perms_needed)).check(matches(isDisplayed()));
		onView(withText(R.string.hsdk_want_phone)).check(matches(isDisplayed()));
		onView(withText(R.string.hsdk_want_sms)).check(matches(isDisplayed()));
		// Bug in Android, Overlay permission never gets reset
		if (!PermissionHelper.hasOverlayPerm(ApplicationProvider.getApplicationContext()))
		    onView(withText(R.string.hsdk_want_overlay)).check(matches(isDisplayed()));
		onView(withText(R.string.hsdk_want_accessibility)).check(matches(isDisplayed()));
	}

	@Test
	public void onlyShowsPermissionsThatArentGranted() {
		grantPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.SYSTEM_ALERT_WINDOW);
		activityScenarioRule.getScenario().recreate();
		onView(withText(R.string.hsdk_perms_needed)).check(matches(isDisplayed()));
		onView(withText(R.string.hsdk_want_phone)).check(matches(not(isDisplayed())));
		onView(withText(R.string.hsdk_want_sms)).check(matches(isDisplayed()));
		onView(withText(R.string.hsdk_want_overlay)).check(matches(not(isDisplayed())));
		onView(withText(R.string.hsdk_want_accessibility)).check(matches(isDisplayed()));
	}

	@Test
	public void doesntShowSummaryifOnlyOnePermission() {
		grantPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.RECEIVE_SMS, Manifest.permission.SYSTEM_ALERT_WINDOW);
		activityScenarioRule.getScenario().recreate();
		onView(withText(R.string.hsdk_access_btn)).check(matches(isDisplayed()));
	}

	private void grantPermissions(String... permissions) {
		PermissionRequester pr = new PermissionRequester();
		pr.addPermissions(permissions);
		pr.requestPermissions();
	}
}

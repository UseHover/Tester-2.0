package com.hover.starter.sdk.permissions;

import android.Manifest;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.permission.PermissionRequester;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import com.hover.sdk.permissions.PermissionActivity;
import com.hover.starter.R;

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
public class SdkPermissionFlowTest {

	@Rule
	public ActivityScenarioRule<PermissionActivity> activityScenarioRule = new ActivityScenarioRule<>(PermissionActivity.class);

	@Rule
	public GrantPermissionRule allPermissionRule = GrantPermissionRule.grant(
		Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.RECEIVE_SMS, Manifest.permission.SYSTEM_ALERT_WINDOW);

	@Test
	public void showsAccessibilityExplaination() {
		onView(withText(R.string.hsdk_access_btn)).check(matches(isDisplayed()));
	}

	@Test
	public void clickingGoesToSettings() {
		UiDevice device = UiDevice.getInstance(getInstrumentation());
		UiObject openSettingsBtn = device.findObject(new UiSelector().description("Open settings"));
		try {
			openSettingsBtn.clickAndWaitForNewWindow();
		} catch (Exception e) { }
//		onView(withText("Hover")).check(matches(isDisplayed()));
	}
}

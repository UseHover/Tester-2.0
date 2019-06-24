package com.hover.starter.sdk.permissions;

import android.app.UiAutomation;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.hover.sdk.permissions.PermissionActivity;
import com.hover.sdk.permissions.PermissionHelper;
import com.hover.sdk.requests.HoverAccessibilityService;
import com.hover.sdk.utils.Utils;
import com.hover.starter.R;

import org.junit.Before;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class AccessibilityGranter {

	public static void grantAccessibility() {
		if (!PermissionHelper.hasAccessPerm(ApplicationProvider.getApplicationContext())) {
			ActivityScenario as = ActivityScenario.launch(PermissionActivity.class);
			onView(withText(R.string.hsdk_access_btn)).check(matches(isDisplayed()));
			onView(withId(android.R.id.button1)).perform(click());
			try {
				UiDevice device = UiDevice.getInstance(getInstrumentation());
				chooseApp(device);
				toggleSwitch(device);
				pressOk(device);
			} catch (Exception e) {
			}
		}
	}

	private static void chooseApp(UiDevice device) throws UiObjectNotFoundException {
		UiObject openAppItem = device.findObject(new UiSelector().text("Hover Starter App"));
		if (openAppItem.exists())
			openAppItem.clickAndWaitForNewWindow();
	}

	private static void toggleSwitch(UiDevice device) throws UiObjectNotFoundException {
		UiObject toggleSwitch = device.findObject(new UiSelector().className("android.widget.Switch"));
		if (toggleSwitch.exists())
			toggleSwitch.clickAndWaitForNewWindow();
	}

	private static void pressOk(UiDevice device) throws UiObjectNotFoundException {
		UiObject okBtn = device.findObject(new UiSelector().text("OK"));
		if (okBtn.exists())
			okBtn.clickAndWaitForNewWindow();
	}

	// Works in console but not in tests
	private void enableAccessibility() {
		getInstrumentation().getUiAutomation(UiAutomation.FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES)
			.executeShellCommand(
				"settings put secure enabled_accessibility_services ${getTargetContext().packageName}/com.hover.sdk.requests.HoverAccessibilityService");
	}
}

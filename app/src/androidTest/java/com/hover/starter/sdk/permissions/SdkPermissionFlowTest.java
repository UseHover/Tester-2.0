package com.hover.starter.sdk.permissions;

import android.Manifest;
import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import com.hover.starter.MainActivity;
import com.hover.starter.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SdkPermissionFlowTest {

	@Rule
	public GrantPermissionRule allPermissionRule = GrantPermissionRule.grant(
		Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.RECEIVE_SMS, Manifest.permission.SYSTEM_ALERT_WINDOW);

	@Before
	public void grantAccessibility() {
		AccessibilityGranter.grantAccessibility();
	}

//	@Test
//	public void pressingBackExits() {
//		UiDevice device = UiDevice.getInstance(getInstrumentation());
//		device.pressHome();
//  }

	@Test
	public void doesntShowAnythingIfAllGranted() {
		ActivityScenario as = ActivityScenario.launch(MainActivity.class);
		onView(withText(R.string.hsdk_access_btn)).check(matches(not(isDisplayed())));
	}
}

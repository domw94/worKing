package de.dominikwieners.working;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.dominikwieners.working.ui.activities.about.AboutActivity;
import de.dominikwieners.working.ui.activities.main.MainActivity;
import de.dominikwieners.working.ui.activities.timer.TimerActivity;
import de.dominikwieners.working.ui.activities.welcome.WelcomeActivity;
import de.dominikwieners.working.ui.activities.working.AddWorkingActivity;

/**
 * Created by dominikwieners on 17.03.18.
 */

@Singleton
public class Navigator {

    @Inject
    Navigator() {
    }

    ////////////////////////////////////////////////////
    // Show WelcomeActivity
    ///////////////////////////////////////////////////

    public void showWelcomeActivity(Activity activity) {
        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    ////////////////////////////////////////////////////
    // Show MainActivity
    ///////////////////////////////////////////////////

    public void showMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void showMainActivityWithPosition(Activity activity, int pagerPos) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(Config.CURRENT_PAGER_POS, pagerPos);
        activity.startActivity(intent);
        activity.finish();
    }

    public void showMainActivityWithYear(Activity activity, int year) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(Config.SELECTED_YEAR, year);
        activity.startActivity(intent);
        activity.finish();

    }

    public void showMainActivityWithPositionAndYear(Activity activity, int pagerPos, int selectedYear) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(Config.CURRENT_PAGER_POS, pagerPos);
        intent.putExtra(Config.SELECTED_YEAR, selectedYear);
        activity.startActivity(intent);
        activity.finish();
    }

    ////////////////////////////////////////////////////
    // Show AddWorkingActivity
    ///////////////////////////////////////////////////

    public void showAddWorkingActivity(Activity activity) {
        Intent intent = new Intent(activity, AddWorkingActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void showAddWorkingActivityWithExtras(Activity activity) {
        Intent intent = new Intent(activity, AddWorkingActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }


    ////////////////////////////////////////////////////
    // Show TimerActivity
    ///////////////////////////////////////////////////

    public void showTimerActivity(Activity activity) {
        Intent intent = new Intent(activity, TimerActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    //////////////////////////////////////////////////
    // Show AboutActivity
    /////////////////////////////////////////////////

    public void showAboutActivity(Activity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    //////////////////////////////////////////////////
    // Show Website
    /////////////////////////////////////////////////
    public void showWebsite(Activity activity, String url) {
        CustomTabsIntent.Builder builderCustomTabs = new CustomTabsIntent.Builder();
        CustomTabsIntent intentCustomTabs = builderCustomTabs.build();
        intentCustomTabs.launchUrl(activity, Uri.parse(url));
    }

    //////////////////////////////////////////////////
    // Show Mail
    /////////////////////////////////////////////////
    public void showMail(Activity activity, String address) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", address, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        activity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}

/*
 * Copyright (C) 2014-2015 Oleg Kan, @Simplaapliko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplaapliko.about;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

public final class Assistant {

    /**
     * Starts activity chooser to send an email with feedback.
     */
    public static void sendFeedback(Activity activity, String email, String appName) {
        Intent feedback = getFeedbackIntent(activity, email, appName);
        startActivity(activity, feedback);
    }

    /**
     * Starts activity chooser to send an email with feedback.
     */
    public static void sendFeedback(Context context, String email, String appName) {
        Intent feedback = getFeedbackIntent(context, email, appName);
        startActivity(context, feedback);
    }

    /**
     * Starts activity chooser to send a message with information about this application.
     */
    public static void shareThisApp(Activity activity, String message) {
        Intent share = getSendIntent(message);
        startActivity(activity, share);
    }

    /**
     * Starts activity chooser to send a message with information about this application.
     */
    public static void shareThisApp(Context context, String message) {
        Intent share = getSendIntent(message);
        startActivity(context, share);
    }

    public static void showMoreFromDeveloper(Activity activity, String developerId) {
        String uri = activity.getString(R.string.a_fragment_about_more_from_developer_link) + developerId;
        Intent showMore = getViewIntent(uri);
        startActivity(activity, showMore);
    }

    public static void showMoreFromDeveloper(Context context, String developerId) {
        String uri = context.getString(R.string.a_fragment_about_more_from_developer_link) + developerId;
        Intent showMore = getViewIntent(uri);
        startActivity(context, showMore);
    }

    public static void showPage(Activity activity, String url) {
        Intent browserIntent = getViewIntent(url);
        startActivity(activity, browserIntent);
    }

    public static void showPage(Context context, String url) {
        Intent browserIntent = getViewIntent(url);
        startActivity(context, browserIntent);
    }

    private static Intent getFeedbackIntent(Context context, String email, String appName) {
        String newLine = "\n";
        StringBuilder body = new StringBuilder();
        body.append(context.getString(R.string.a_feedback_device));
        body.append(Build.MANUFACTURER);
        body.append(" ");
        body.append(Build.MODEL);
        body.append(newLine);
        body.append(context.getString(R.string.a_feedback_android_version));
        body.append(Build.VERSION.RELEASE);
        body.append(newLine);
        body.append(context.getString(R.string.a_feedback_region));
        body.append(Locale.getDefault().getLanguage());
        body.append(" / ");
        body.append(Locale.getDefault().getCountry());
        body.append(newLine);

        String appVersion = AppInfo.getAppVersion(context);

        body.append(context.getString(R.string.a_feedback_app_version));
        body.append(appVersion);
        body.append(newLine);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        body.append(context.getString(R.string.a_feedback_screen));
        body.append(context.getString(R.string.a_feedback_screen_width));
        body.append(Integer.toString(width));
        body.append(" ");
        body.append(context.getString(R.string.a_feedback_screen_height));
        body.append(Integer.toString(height));
        body.append(" ");
        body.append(context.getString(R.string.a_feedback_screen_density_dpi));
        body.append(Float.toString(dm.densityDpi/160));
        body.append(newLine);
        body.append(newLine);
        body.append(context.getString(R.string.a_feedback_message));
        body.append(newLine);
        body.append(newLine);

        String subject = "[" + appName + "] " + context.getString(R.string.a_feedback_subject);

        String uriText = "mailto:" + email +
                "?subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(body.toString());
        Uri uri = Uri.parse(uriText);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(uri);
        return intent;
    }

    private static Intent getSendIntent(String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        return intent;
    }

    private static Intent getViewIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    private static void startActivity(Activity activity, Intent intentToStart) {
        activity.startActivity(intentToStart);
    }

    private static void startActivity(Context context, Intent intentToStart) {
        Intent intent = Intent.createChooser(intentToStart, null);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
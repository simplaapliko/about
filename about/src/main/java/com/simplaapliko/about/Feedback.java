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

public final class Feedback {

    /**
     * Starts activity chooser to send an email with feedback.
     */
    public static void sendFeedback(Context context, String email, String appName) {

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

        Intent feedback = new Intent(Intent.ACTION_SENDTO);
        feedback.setType("message/rfc822");
        feedback.setData(uri);
        if (context instanceof Activity) {
            context.startActivity(Intent.createChooser(
                    feedback,
                    context.getString(R.string.a_feedback_dialog_intent_chooser_title)));
        } else {
            Intent intent = Intent.createChooser(
                    feedback,
                    context.getString(R.string.a_feedback_dialog_intent_chooser_title));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * Starts activity chooser to send a message with information about this application.
     */
    public static void shareThisApp(Context context, String message) {

        String subject = context.getString(R.string.a_share_this_app_subject);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, message);

        if (context instanceof Activity) {
            context.startActivity(Intent.createChooser(share, subject));
        } else {
            Intent intent = Intent.createChooser(share, subject);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void showMoreFromDeveloper(Context context, int developerId) {
        showMoreFromDeveloper(context, context.getString(developerId));
    }

    public static void showMoreFromDeveloper(Context context, String developerId) {

        String uri = context.getString(R.string.a_fragment_about_more_from_developer_link) + developerId;

        Intent showMore = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        if (context instanceof Activity) {
            context.startActivity(showMore);
        } else {
            Intent intent = Intent.createChooser(showMore, null);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
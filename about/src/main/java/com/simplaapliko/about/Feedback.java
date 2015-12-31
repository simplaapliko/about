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

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
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
        context.startActivity(Intent.createChooser(
                feedback,
                context.getString(R.string.a_feedback_dialog_intent_chooser_title)));

    }

}
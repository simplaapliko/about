/*
 * Copyright (C) 2014 Oleg Kan, @Simplaapliko
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

package com.simplaapliko.about.util;

import static com.simplaapliko.about.util.ActivityUtils.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public final class Share {

    /**
     * Starts activity chooser to send a message with information about this application.
     */
    public static void share(Activity activity, String message) {
        Intent share = getSendIntent(message);
        startActivity(activity, share);
    }

    /**
     * Starts activity chooser to send a message with information about this application.
     */
    public static void share(Context context, String message) {
        Intent share = getSendIntent(message);
        startActivity(context, share);
    }

    private static Intent getSendIntent(String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, title);
        return intent;
    }
}

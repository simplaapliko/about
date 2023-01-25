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
import android.net.Uri;

import com.simplaapliko.about.R;

public final class MoreFromDeveloper {

    public static void show(Activity activity, String developerId) {
        String uri = activity.getString(R.string.a_about_more_from_developer_link) + developerId;
        Intent showMore = getViewIntent(uri);
        startActivity(activity, showMore);
    }

    public static void show(Context context, String developerId) {
        String uri = context.getString(R.string.a_about_more_from_developer_link) + developerId;
        Intent showMore = getViewIntent(uri);
        startActivity(context, showMore);
    }

    private static Intent getViewIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }
}

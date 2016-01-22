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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutDialog extends DialogFragment {

    // Inner classes

    public static class Builder {

        private String mAppName;
        private int mAppIcon;
        private String mDeveloperName;
        private String mDeveloperId;
        private String mFeedbackEmail;

        private boolean mHasPositiveButton;

        public Builder setAppName(String appName) {
            mAppName = appName;
            return this;
        }

        public Builder setAppIcon(int appIcon) {
            mAppIcon = appIcon;
            return this;
        }

        public Builder setDeveloperName(String developerName) {
            mDeveloperName = developerName;
            return this;
        }

        public Builder setDeveloperId(String developerId) {
            mDeveloperId = developerId;
            return this;
        }

        public Builder setFeedbackEmail(String feedbackEmail) {
            mFeedbackEmail = feedbackEmail;
            return this;
        }

        public Builder setHasPositiveButton(boolean hasPositiveButton) {
            mHasPositiveButton = hasPositiveButton;
            return this;
        }

        public AboutDialog build() {

            if (mAppName == null || mAppName.isEmpty()) {
                throw new IllegalArgumentException("App name is required.");
            }

            return newInstance(mAppName, mAppIcon, mDeveloperName, mDeveloperId, mFeedbackEmail,
                    mHasPositiveButton);
        }
    }


    // Keys for arguments, saved state variables
    private static final String APP_NAME_KEY = "APP_NAME_KEY";
    private static final String APP_ICON_KEY = "APP_ICON_KEY";
    private static final String DEVELOPER_NAME_KEY = "DEVELOPER_NAME_KEY";
    private static final String DEVELOPER_ID_KEY = "DEVELOPER_ID_KEY";
    private static final String FEEDBACK_EMAIL_KEY = "FEEDBACK_EMAIL_KEY";

    private static final String HAS_DEVELOPER_SECTION_KEY = "HAS_DEVELOPER_SECTION_KEY";
    private static final String HAS_MORE_FROM_DEVELOPER_SECTION_KEY = "HAS_MORE_FROM_DEVELOPER_SECTION_KEY";
    private static final String HAS_SEND_FEEDBACK_TO_SECTION_KEY = "HAS_SEND_FEEDBACK_TO_SECTION_KEY";

    private static final String HAS_POSITIVE_BUTTON_KEY = "HAS_POSITIVE_BUTTON_KEY";


    // Class variables
    private DialogInterface.OnDismissListener mOnDismissListener;

    private String mAppName;
    private int mAppIcon;
    private String mDeveloperName;
    private String mDeveloperId;
    private String mFeedbackEmail;

    private boolean mHasDeveloperSection;
    private boolean mHasMoreFromDeveloperSection;
    private boolean mHasSendFeedbackToSection;

    private boolean mHasPositiveButton;


    // Factory

    private static AboutDialog newInstance(String appName,
                                          int appIcon,
                                          String developerName,
                                          String developerId,
                                          String feedbackEmail,
                                          boolean hasPositiveButton) {

        AboutDialog fragment = new AboutDialog();
        Bundle args = new Bundle();
        args.putString(APP_NAME_KEY, appName);
        args.putInt(APP_ICON_KEY, appIcon);
        args.putString(DEVELOPER_NAME_KEY, developerName);
        args.putString(DEVELOPER_ID_KEY, developerId);
        args.putString(FEEDBACK_EMAIL_KEY, feedbackEmail);

        args.putBoolean(HAS_DEVELOPER_SECTION_KEY, developerName != null);
        args.putBoolean(HAS_MORE_FROM_DEVELOPER_SECTION_KEY, developerId != null);
        args.putBoolean(HAS_SEND_FEEDBACK_TO_SECTION_KEY, feedbackEmail != null);

        args.putBoolean(HAS_POSITIVE_BUTTON_KEY, hasPositiveButton);

        fragment.setArguments(args);
        return fragment;
    }


    // Constructors

    public AboutDialog() {
    }


    // Android Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mAppName = args.getString(APP_NAME_KEY);
            mAppIcon = args.getInt(APP_ICON_KEY);
            mDeveloperName = args.getString(DEVELOPER_NAME_KEY);
            mDeveloperId = args.getString(DEVELOPER_ID_KEY);
            mFeedbackEmail = args.getString(FEEDBACK_EMAIL_KEY);

            mHasDeveloperSection = args.getBoolean(HAS_DEVELOPER_SECTION_KEY);
            mHasMoreFromDeveloperSection = args.getBoolean(HAS_MORE_FROM_DEVELOPER_SECTION_KEY);
            mHasPositiveButton = args.getBoolean(HAS_POSITIVE_BUTTON_KEY);
            mHasSendFeedbackToSection = args.getBoolean(HAS_SEND_FEEDBACK_TO_SECTION_KEY);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get root view
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.a_dialog_fragment_about, null);

        initUiWidgets(rootView);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);

        // set buttons
        if (mHasPositiveButton) {
            builder.setPositiveButton(
                    android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnDismissListener = null;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }


    // Public API

    public DialogInterface.OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    public void setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }


    // Private API

    private void initUiWidgets(View rootView) {

        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        int colorPrimaryDark = typedValue.data;

        ImageView appIcon = (ImageView) rootView.findViewById(R.id.app_icon);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            appIcon.setImageDrawable(getResources().getDrawable(mAppIcon));
        } else {
            appIcon.setImageDrawable(getResources().getDrawable(mAppIcon, getActivity().getTheme()));
        }

        TextView appNameTextView = (TextView) rootView.findViewById(R.id.app_name);
        appNameTextView.setTextColor(colorPrimaryDark);
        appNameTextView.setText(mAppName);

        TextView appVersionTextView = (TextView) rootView.findViewById(R.id.app_version);
        appVersionTextView.setText(AppInfo.getAppVersion(getActivity()));

        if (mHasDeveloperSection) {
            TextView developerTextView = (TextView) rootView.findViewById(R.id.developer);
            developerTextView.setTextColor(colorPrimaryDark);
            developerTextView.setText(mDeveloperName);
        } else {
            rootView.findViewById(R.id.developer_group)
                    .setVisibility(View.GONE);
        }

        if (mHasSendFeedbackToSection) {
            TextView feedbackEmailTextView = (TextView) rootView.findViewById(R.id.feedback_email);
            feedbackEmailTextView.setTextColor(colorPrimaryDark);
            feedbackEmailTextView.setText(mFeedbackEmail);
            feedbackEmailTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Feedback.sendFeedback(getContext(), mFeedbackEmail, mAppName);
                }
            });
        } else {
            rootView.findViewById(R.id.feedback_email_group)
                    .setVisibility(View.GONE);
        }

        if (mHasMoreFromDeveloperSection) {
            TextView moveFromDeveloper = (TextView) rootView.findViewById(R.id.more_from_developer);
            moveFromDeveloper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Feedback.showMoreFromDeveloper(getContext(), mDeveloperId);
                }
            });
        } else {
            rootView.findViewById(R.id.more_from_developer_group)
                    .setVisibility(View.GONE);
        }

    }

}
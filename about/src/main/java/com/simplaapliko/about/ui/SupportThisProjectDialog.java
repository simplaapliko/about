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

package com.simplaapliko.about.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;

import com.simplaapliko.about.R;
import com.simplaapliko.about.util.Assistant;

public class SupportThisProjectDialog extends DialogFragment {
    public static class Builder {

        private String mProjectPageLink;
        private boolean mHasNegativeButton;

        public Builder setLinkToProjectPage(String projectPageLink) {
            mProjectPageLink = projectPageLink;
            return this;
        }

        public Builder setHasNegativeButton(boolean hasNegativeButton) {
            mHasNegativeButton = hasNegativeButton;
            return this;
        }

        public SupportThisProjectDialog build() {
            if (TextUtils.isEmpty(mProjectPageLink)) {
                throw new IllegalArgumentException("Link to project page is required.");
            }

            return newInstance(mProjectPageLink, mHasNegativeButton);
        }
    }

    private static final String LINK_TO_PROJECT_PAGE_KEY = "LINK_TO_PROJECT_PAGE_KEY";
    private static final String HAS_NEGATIVE_BUTTON_KEY = "HAS_NEGATIVE_BUTTON_KEY";

    private String mProjectPageLink;
    private boolean mHasNegativeButton;

    private static SupportThisProjectDialog newInstance(String link, boolean hasNegativeButton) {

        SupportThisProjectDialog fragment = new SupportThisProjectDialog();
        Bundle args = new Bundle();
        args.putString(LINK_TO_PROJECT_PAGE_KEY, link);
        args.putBoolean(HAS_NEGATIVE_BUTTON_KEY, hasNegativeButton);

        fragment.setArguments(args);
        return fragment;
    }

    public SupportThisProjectDialog() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mProjectPageLink = args.getString(LINK_TO_PROJECT_PAGE_KEY);
            mHasNegativeButton = args.getBoolean(HAS_NEGATIVE_BUTTON_KEY);
        }

        setCancelable(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.a_dialog_fragment_support_this_project, null);

        initUiWidgets(rootView);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);

        if (mHasNegativeButton) {
            builder.setNegativeButton(
                    android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        return builder.create();
    }

    private void initUiWidgets(View rootView) {
        rootView.findViewById(R.id.show_project_page)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Assistant.showPage(getContext(), mProjectPageLink);
                    }
                });
    }
}

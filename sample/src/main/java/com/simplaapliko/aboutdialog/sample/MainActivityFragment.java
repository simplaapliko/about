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

package com.simplaapliko.aboutdialog.sample;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.simplaapliko.aboutdialog.AboutDialog;

public class MainActivityFragment extends Fragment implements DialogInterface.OnDismissListener {

    public MainActivityFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        final CheckBox hasPositiveButton =(CheckBox) view.findViewById(R.id.has_positive_button);

        final EditText applicationName =(EditText) view.findViewById(R.id.app_name);
        final EditText developerId =(EditText) view.findViewById(R.id.developer_id);
        final EditText developerName =(EditText) view.findViewById(R.id.developer_name);
        final EditText feedbackEmail =(EditText) view.findViewById(R.id.feedback_email);

        applicationName.setText("About Dialog");
        developerId.setText("Simplaapliko");
        developerName.setText("Oleg Kan");
        feedbackEmail.setText("simplaapliko@gmail.com");

        Button showDialog = (Button) view.findViewById(R.id.show_dialog);
        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    AboutDialog dialog = new AboutDialog.Builder()
                            .setAppName(applicationName.getText().toString())
                            .setAppIcon(R.mipmap.ic_launcher)
                            .setDeveloperName(developerName.getText().toString().trim().length() == 0 ? null : developerName.getText().toString())
                            .setDeveloperId(developerId.getText().toString().trim().length() == 0 ? null : developerId.getText().toString())
                            .setFeedbackEmail(feedbackEmail.getText().toString().trim().length() == 0 ? null : feedbackEmail.getText().toString())
                            .setHasPositiveButton(hasPositiveButton.isChecked())
                            .build();

                    dialog.setOnDismissListener(MainActivityFragment.this);
                    dialog.show(getFragmentManager(), AboutDialog.class.getSimpleName());
                } catch (IllegalArgumentException ex) {
                    Toast.makeText(getContext(), ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Toast.makeText(getContext(), "Dialog dismissed", Toast.LENGTH_SHORT).show();
    }
}

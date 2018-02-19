package com.codingwords.sobrien.gissues;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.codingwords.sobrien.gissues.entity.Issue;
import com.codingwords.sobrien.gissues.ui.GissuesAdapter;
import com.codingwords.sobrien.gissues.vm.GissuesViewModel;

import javax.inject.Inject;

/**
 * Created by Administrator on 2/19/2018.
 */

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codingwords.sobrien.gissues.GissuesApp;
import com.codingwords.sobrien.gissues.ui.GissuesAdapter;
import com.codingwords.sobrien.gissues.vm.GissuesViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2/19/2018.
 */

public class MainActivity  extends AppCompatActivity{

    private static String TAG = MainActivity.class.getSimpleName();

    private GissuesViewModel viewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private EditText repoEditText;

    private GissuesAdapter gissuesAdapter;

    private ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((GissuesApp) getApplication()).getAppComp().inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GissuesViewModel.class);
        updateView();

        repoEditText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String repo = repoEditText.getText().toString();
                if (repo.length() > 0) {
                    String[] query = repo.split("/");
                    if (query.length == 2) {
                        hideSoftKeyboard(MainActivity.this, v);
                        updateProgress(true);
                        viewModel.pullIssues(query[0], query[1]);
                    } else {
                        processError(new Exception(
                                "Error check format should be owner/repo")
                        );
                    }
                } else {
                    processError(new Exception(
                            "Repository name cannot be empty")
                    );
                }
                return true;
            }
            return false;
        });


        viewModel.getGapiResponse().observe(this, gapiResponse -> {
            Log.d(TAG, "observe called()");
            if (gapiResponse.getError() == null) {
                processResponse(gapiResponse.getIssues());

            } else {
                processError(gapiResponse.getError());
            }
        });
    }

    private void updateView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.issueList);
        repoEditText = (EditText) findViewById(R.id.repo_selector);

        progDialog = new ProgressDialog(MainActivity.this);
        progDialog.setIndeterminate(true);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setTitle(getString(R.string.prog_title));
        progDialog.setMessage(getString(R.string.prog_descr));
        progDialog.setCancelable(false);
        progDialog.setCanceledOnTouchOutside(false);

        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false)
        );
        recyclerView.hasFixedSize();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL
        );
        recyclerView.addItemDecoration(mDividerItemDecoration);
        gissuesAdapter = new GissuesAdapter(getLayoutInflater());
        recyclerView.setAdapter(gissuesAdapter);
    }

    private void processResponse(List<Issue> issues) {
        updateProgress(false);
        if (issues != null && issues.size() > 0) {
            gissuesAdapter.addGissues(issues);
        } else {
            gissuesAdapter.clearGissues();
            Toast.makeText(
                    this,
                    "No issues found!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void processError(Throwable error) {
        updateProgress(false);
        gissuesAdapter.clearGissues();
        Toast.makeText(this, "An error occured!", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Error: " + error.toString());
    }

    public void updateProgress(boolean show) {
        if (show) {
            progDialog.show();
        } else {
            progDialog.dismiss();
        }
    }

    private void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager inputMeth = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        if (inputMeth != null) {
            inputMeth.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

}

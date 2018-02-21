package com.codingwords.sobrien.gissues;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.codingwords.sobrien.gissues.databinding.ActivityMainBinding;
import com.codingwords.sobrien.gissues.entity.Issue;
import com.codingwords.sobrien.gissues.ui.GissuesAdapter;
import com.codingwords.sobrien.gissues.vm.GissuesViewModel;

import javax.inject.Inject;

/**
 * Created by Administrator on 2/19/2018.
 */

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;


/**
 * Created by Administrator on 2/19/2018.
 */

public class MainActivity  extends AppCompatActivity implements GissuesScreen {

    private static String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding dataBinding;

    private GissuesViewModel viewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private EditText etOwnerName;
    private EditText etRepoName;


    private GissuesAdapter gissuesAdapter;

    private ProgressDialog progDialog;

    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((GissuesApp) getApplication()).getAppComp().inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GissuesViewModel.class);
        viewModel.setGissuesScreen(this);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dataBinding.setData(viewModel);
        updateView();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String owner = etOwnerName.getText().toString();
                Boolean isValid = true;
                if (owner.length() <2) {
                    Toast.makeText(MainActivity.this,"Must provide repository owner!", Toast.LENGTH_LONG);
                    isValid = false;
                }
                String repo = etRepoName.getText().toString();

                if (repo.length() <2) {
                    Toast.makeText(MainActivity.this,"Must provide repository name!", Toast.LENGTH_LONG);
                    isValid = false;
                }

                if (isValid == true){
                    viewModel.pullIssues(owner, repo);
                    updateProgress(true);
                }

            }
        });

        viewModel.getGapiResponse().observe(this, gapiResponse -> {
            if (gapiResponse.getError() == null) {
                processResponse(gapiResponse.getIssues());

            } else {
                processError(gapiResponse.getError());
            }
        });
    }

    private void updateView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.issueList);
        searchBtn = (Button)findViewById(R.id.btn_search);
        etOwnerName = (EditText) findViewById(R.id.owner_name);
        etRepoName = (EditText) findViewById(R.id.repo_name);
        //dataBinding.btnRegister.setOnClickListener(this);

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

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL
        );
        recyclerView.hasFixedSize();
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
            showIssuesNotFound();
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

    @Override
    public void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOwnerError() {
        showToast("Must provide repository owner!");
    }

    @Override
    public void showRepoError() {
         showToast("Must provide repository name!");
    }

    @Override
    public void showIssuesNotFound() {
         showToast("No issues found!");
    }
}

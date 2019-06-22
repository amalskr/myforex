package com.ceylonapz.myforex.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ceylonapz.myforex.R;
import com.ceylonapz.myforex.databinding.ActivityMainBinding;
import com.ceylonapz.myforex.viewmodel.MainViewModel;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding(savedInstanceState);
    }

    private void initDataBinding(Bundle savedInstanceState) {
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (savedInstanceState == null) {
            mainViewModel.init(this);
        }
        mainBinding.setMainVm(mainViewModel);
    }

    private void showLogoutConfrimation() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you really want to logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        logoutNow();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void logoutNow() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            //Facebook Logout
            LoginManager.getInstance().logOut();
            startLoginScreen();
        } else {
            signOutGoogle();
        }
    }

    private void signOutGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startLoginScreen();
                    }
                });
    }

    private void startLoginScreen() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.action_search_history:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            case R.id.action_logout:
                showLogoutConfrimation();
                break;
        }
        return true;
    }


}

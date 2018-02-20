package com.androidtutorialpoint.demoemail;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends RuntimePermissionsActivity {
Context context;
    TextView textView,textView1;
    int REQUEST_CODE=40;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView  = (TextView) findViewById(R.id.text);
        textView1= (TextView) findViewById(R.id.text1);
        MainActivity.super.requestAppPermission(new String[]{Manifest.permission.GET_ACCOUNTS},R.string.per,REQUEST_CODE);

    }

    @Override
    public void onPermissionGranted(int requestCode) {
        check();
    }

    private void check() {
        Toast.makeText(getApplicationContext(),
                "Welcome", Toast.LENGTH_LONG).show();

        View v=findViewById(android.R.id.content);
        Pattern pattern= Patterns.EMAIL_ADDRESS;
        Account[] accounts= AccountManager.get(getApplicationContext()).getAccounts();
        StringBuilder builder = new StringBuilder();
        String regularExpression = "^(<([a-z0-9_\\.\\-]+)\\@([a-z0-9_\\-]+\\.)+[a-z]{2,6}>|([a-z0-9_\\.\\-]+)\\@([a-z0-9_\\-]+\\.)+[a-z]{2,6})$";
        String a="";
        String possibleEmail="";
        for (Account account : accounts) {
            if (pattern.matcher(account.name).matches()) {
                possibleEmail = account.name;
                builder.append(possibleEmail);
                builder.append("\n");
                textView1.setText(builder);
                a = account.type;

                // Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),possibleEmail,Toast.LENGTH_LONG).show();
                //  Snackbar.make(v,builder,Snackbar.LENGTH_LONG).show();
                //  textView.setText(possibleEmailtype);
            }
            if (a.equalsIgnoreCase("com.google"))
            {
                Toast.makeText(getApplicationContext(),
                        possibleEmail, Toast.LENGTH_LONG).show();
            }
        }
    }
}

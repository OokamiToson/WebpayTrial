package com.example.diego.webpaytrial;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements FragmentChanger {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new PayFragment();
        transaction.add(R.id.fragmentview, fragment, fragment.toString());
        transaction.addToBackStack(fragment.toString());
        transaction.commit();
        */

        setContentView(R.layout.activity_main);
    }

    @Override
    public void goToWebpay(int amount) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new PhpwebFragment();

        Bundle args = new Bundle();
        args.putInt("amount", amount);
        fragment.setArguments(args);

        transaction.replace(R.id.fragmentview, fragment, fragment.toString());
        transaction.addToBackStack(fragment.toString());
        transaction.commit();
    }
}

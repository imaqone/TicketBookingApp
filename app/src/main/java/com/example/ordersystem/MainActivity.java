package com.example.ordersystem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.ordersystem.*;
import com.example.ordersystem.dummy.DummyContent;
import com.example.ordersystem.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements
        MainFragment.OnFragmentInteractionListener, OrderFragment.OnListFragmentInteractionListener, personFragment.OnFragmentInteractionListener {
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item){

    }
    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();
        Fragment f1 = new MainFragment();
        Fragment f2 = new OrderFragment();
        Fragment f3 = new personFragment();
        fragmentList.add(f1);
        fragmentList.add(f2);
        fragmentList.add(f3);
        FragmentManager fragmentManager = getSupportFragmentManager();
        rFragmentPaperAdapter rfpa = new rFragmentPaperAdapter(fragmentManager, fragmentList);
        viewPager.setAdapter(rfpa);
        viewPager.setCurrentItem(0);
        f1.onAttach(this);
    }

    public void clickCity(View view) {
        Intent intent1 = new Intent(this, CityActivity.class);
        startActivity(intent1);
    }

    Calendar ca = Calendar.getInstance();
    int mYear = ca.get(Calendar.YEAR);
    int mMonth = ca.get(Calendar.MONTH);
    int mDay = ca.get(Calendar.DAY_OF_MONTH);

    public void clickTime(View view) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        final String data = (month + 1) + "月" + dayOfMonth + "日 ";
                        TextView textView = findViewById(R.id.timeSet);
                        textView.setText(data);
                    }
                },
                mYear, mMonth, mDay);
        datePickerDialog.show();

    }
    public void loginClick(View view){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

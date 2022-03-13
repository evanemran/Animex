package com.evanemran.animex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.evanemran.animex.adapters.AnimeListAdapter;
import com.evanemran.animex.adapters.ViewPagerAdapter;
import com.evanemran.animex.fragments.AnimeFragment;
import com.evanemran.animex.fragments.MusicFragment;
import com.evanemran.animex.listeners.ClickListener;
import com.evanemran.animex.listeners.ResponseListener;
import com.evanemran.animex.manager.RequestManager;
import com.evanemran.animex.models.AnimeResponse;
import com.evanemran.animex.models.Document;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =findViewById(R.id.toolbar_home);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AnimeFragment(), "Anime");
//        viewPagerAdapter.addFragment(new MusicFragment(), "Episodes");
        viewPagerAdapter.addFragment(new MusicFragment(), "Song");
        viewPager.setAdapter(viewPagerAdapter);
    }

}
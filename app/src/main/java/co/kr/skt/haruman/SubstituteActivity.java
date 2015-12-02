package co.kr.skt.haruman;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by T on 2015-12-02.
 */
public class SubstituteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView mRecyclerView_sub;
    SubstituteAdapter mSubstituteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        mRecyclerView_sub = (RecyclerView) findViewById(R.id.sub_recyclerview);

        mSubstituteAdapter = new SubstituteAdapter();
        mSubstituteAdapter.setOnItemClickListener(new SubstituteViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(SubstituteActivity.this, "title_sub : " + name, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView_sub.setAdapter(mSubstituteAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView_sub.setLayoutManager(manager);

        initData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mSubstituteAdapter.add("대타대타대타대타대타대타");
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_substitute) {

            Intent intent = new Intent(SubstituteActivity.this, SubstituteActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_alba) {

            Intent intent = new Intent(SubstituteActivity.this, AlbaListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_setting) {

            Intent intent = new Intent(SubstituteActivity.this, SettingActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_after) {

            Intent intent = new Intent(SubstituteActivity.this, AfterActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

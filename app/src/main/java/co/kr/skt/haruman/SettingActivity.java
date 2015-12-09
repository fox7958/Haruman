package co.kr.skt.haruman;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by T on 2015-12-02.
 */
public class SettingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    boolean mlnitSpinner;
    ArrayAdapter<CharSequence> townspin, boroughspin, yearspin, monthspin, dayspin;
    Spinner spinnerTown, spinnerBorough, spinnerYear, spinnerMonth, spinnerDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button substitute = (Button)findViewById(R.id.nav_substitute);
        Button alba = (Button)findViewById(R.id.nav_alba);
        Button setting = (Button)findViewById(R.id.nav_setting);
        Button after = (Button)findViewById(R.id.nav_after);

        substitute.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SubstituteActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        alba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AlbaActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AfterActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        spinnerTown = (Spinner)findViewById(R.id.spinner_town_setting);
        spinnerBorough = (Spinner)findViewById(R.id.spinner_borough_setting);
        spinnerYear = (Spinner)findViewById(R.id.spinner_year_setting);
        spinnerMonth = (Spinner)findViewById(R.id.spinner_month_setting);
        spinnerDay = (Spinner)findViewById(R.id.spinner_day_setting);

        townspin = ArrayAdapter.createFromResource(this, R.array.spinner_town, R.layout.support_simple_spinner_dropdown_item);
        townspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        yearspin = ArrayAdapter.createFromResource(this, R.array.year, R.layout.support_simple_spinner_dropdown_item);
        yearspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthspin = ArrayAdapter.createFromResource(this, R.array.month, R.layout.support_simple_spinner_dropdown_item);
        monthspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthspin);
        spinnerYear.setAdapter(yearspin);
        spinnerTown.setAdapter(townspin);

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mlnitSpinner == false) {
                    mlnitSpinner = true;
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mlnitSpinner == false){
                    mlnitSpinner = true;
                    return;
                }

                if (monthspin.getItem(position).equals("2")){
                    dayspin = ArrayAdapter.createFromResource(SettingActivity.this, R.array.day_29, R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);
                }
                else if (monthspin.getItem(position).equals("1")||monthspin.getItem(position).equals("3")||monthspin.getItem(position).equals("5")||monthspin.getItem(position).equals("7")||monthspin.getItem(position).equals("8")
                        ||monthspin.getItem(position).equals("10")||monthspin.getItem(position).equals("12")){

                    dayspin = ArrayAdapter.createFromResource(SettingActivity.this, R.array.day_31, R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);
                }else{
                    dayspin = ArrayAdapter.createFromResource(SettingActivity.this, R.array.day_30, R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mlnitSpinner == false) {
                    mlnitSpinner = true;
                    return;
                }
                Toast.makeText(SettingActivity.this, townspin.getItem(position), Toast.LENGTH_SHORT).show();

                if (townspin.getItem(position).equals("서울시")) {
                    boroughspin = ArrayAdapter.createFromResource(SettingActivity.this, R.array.spinner_borough_seoul, R.layout.support_simple_spinner_dropdown_item);
                    spinnerBorough.setAdapter(boroughspin);

                    spinnerBorough.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (mlnitSpinner == false) {
                                mlnitSpinner = true;
                                return;
                            }
                            Toast.makeText(SettingActivity.this, boroughspin.getItem(position), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (townspin.getItem(position).equals("경기도")) {
                    boroughspin = ArrayAdapter.createFromResource(SettingActivity.this, R.array.spinner_borough_gyungki, R.layout.support_simple_spinner_dropdown_item);
                    spinnerBorough.setAdapter(boroughspin);

                    spinnerBorough.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (mlnitSpinner == false) {
                                mlnitSpinner = true;
                                return;
                            }
                            Toast.makeText(SettingActivity.this, boroughspin.getItem(position), Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btnBack = (Button)findViewById(R.id.btn_back_setting);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

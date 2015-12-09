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

/**
 * Created by T on 2015-12-08.
 */
public class SubstituteInsertActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinnerTown, spinnerBorough, spinnerType, spinnerTime, spinnerMonth, spinnerDay;
    ArrayAdapter<CharSequence> townspin, boroughspin, typespin, timespin, monthspin, dayspin;
    boolean a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_insert);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button substitute = (Button) findViewById(R.id.nav_substitute);
        Button alba = (Button) findViewById(R.id.nav_alba);
        Button setting = (Button) findViewById(R.id.nav_setting);
        Button after = (Button) findViewById(R.id.nav_after);

        substitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubstituteInsertActivity.this, SubstituteActivity.class);
                startActivity(intent);
                finish();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        alba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubstituteInsertActivity.this, AlbaActivity.class);
                startActivity(intent);
                finish();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubstituteInsertActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubstituteInsertActivity.this, AfterActivity.class);
                startActivity(intent);
                finish();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        spinnerTown = (Spinner) findViewById(R.id.spinner_town_substitute_insert);
        spinnerBorough = (Spinner) findViewById(R.id.spinner_borough_substitute_insert);
        spinnerType = (Spinner) findViewById(R.id.spinner_type_substitute_insert);
        spinnerTime = (Spinner) findViewById(R.id.spinner_time_substitute_insert);
        spinnerMonth = (Spinner) findViewById(R.id.spinner_Month);
        spinnerDay = (Spinner) findViewById(R.id.spinner_day);

        townspin = ArrayAdapter.createFromResource(this, R.array.spinner_town, R.layout.support_simple_spinner_dropdown_item);
        typespin = ArrayAdapter.createFromResource(this, R.array.spinner_type, R.layout.support_simple_spinner_dropdown_item);
        timespin = ArrayAdapter.createFromResource(this, R.array.spinner_time, R.layout.support_simple_spinner_dropdown_item);
        monthspin = ArrayAdapter.createFromResource(this, R.array.month, R.layout.support_simple_spinner_dropdown_item);

        townspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typespin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        timespin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerTown.setAdapter(townspin);
        spinnerType.setAdapter(typespin);
        spinnerTime.setAdapter(timespin);
        spinnerMonth.setAdapter(monthspin);

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (a == false) {
                    a = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (a == false) {
                    a = true;
                }
                if (townspin.getItem(position).equals("서울시")) {
                    boroughspin = ArrayAdapter.createFromResource(SubstituteInsertActivity.this, R.array.spinner_borough_seoul, R.layout.support_simple_spinner_dropdown_item);
                    boroughspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerBorough.setAdapter(boroughspin);

                    spinnerBorough.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (a == false) {
                                a = true;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (townspin.getItem(position).equals("경기도")) {
                    boroughspin = ArrayAdapter.createFromResource(SubstituteInsertActivity.this, R.array.spinner_borough_gyungki, R.layout.support_simple_spinner_dropdown_item);
                    boroughspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerBorough.setAdapter(boroughspin);

                    spinnerBorough.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (a == false) {
                                a = true;
                            }
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
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (a == false) {
                    a = true;
                }
                String month = (String)monthspin.getItem(position);
                if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8") || month.equals("10") || month.equals("12")){
                    dayspin = ArrayAdapter.createFromResource(SubstituteInsertActivity.this, R.array.day_31, R.layout.support_simple_spinner_dropdown_item);
                    dayspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);

                    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if( a == false){
                                a=true;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else if (month.equals("2")){
                    dayspin = ArrayAdapter.createFromResource(SubstituteInsertActivity.this, R.array.day_29, R.layout.support_simple_spinner_dropdown_item);
                    dayspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);

                    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if( a == false ){
                                a = true;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else{
                    dayspin = ArrayAdapter.createFromResource(SubstituteInsertActivity.this, R.array.day_30, R.layout.support_simple_spinner_dropdown_item);
                    dayspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);

                    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if( a == false ){
                                a = true;
                            }
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
        Button btnBack = (Button)findViewById(R.id.btn_back_sub_insert);
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

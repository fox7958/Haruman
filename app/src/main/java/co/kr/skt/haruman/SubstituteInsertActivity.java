package co.kr.skt.haruman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by T on 2015-12-08.
 */
public class SubstituteInsertActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinnerTown, spinnerBorough, spinnerType, spinnerTime, spinnerMonth, spinnerDay;
    ArrayAdapter<CharSequence> townspin, boroughspin, typespin, timespin, monthspin, dayspin;
    boolean a;
    Button btnInsert;

    EditText townSave, boroughSave, timeSave, monthSave, daySave, genderSave, typeSave, editTitle, editPay, editMinAge, editMaxAge, editContent;
    CheckBox checkM, checkF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_insert);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        townSave = (EditText) findViewById(R.id.townSave);
        boroughSave = (EditText) findViewById(R.id.boroughSave);
        timeSave = (EditText) findViewById(R.id.timeSave);
        typeSave = (EditText) findViewById(R.id.typeSave);
        monthSave = (EditText) findViewById(R.id.monthSave);
        daySave = (EditText) findViewById(R.id.daySave);
        genderSave = (EditText) findViewById(R.id.genderSave);

        editTitle = (EditText) findViewById(R.id.edit_title_hr);
        editPay = (EditText) findViewById(R.id.edit_pay_hr);
        editMinAge = (EditText) findViewById(R.id.edit_min_age_hr);
        editMaxAge = (EditText) findViewById(R.id.edit_max_age_hr);
        editContent = (EditText) findViewById(R.id.edit_content_hr);

        checkM = (CheckBox) findViewById(R.id.check_m_add_hr);
        checkF = (CheckBox) findViewById(R.id.check_f_add_hr);

        btnInsert = (Button) findViewById(R.id.btn_insert_hr);

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
        checkM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkM.isChecked() == true) {
                    if (checkF.isChecked() == false) {
                        String gender = "M";
                        genderSave.setText(gender);
                    } else if (checkF.isChecked() == true) {
                        String gender = "A";
                        genderSave.setText(gender);
                    }
                } else if (checkM.isChecked() == false) {
                    if (checkF.isChecked() == true) {
                        String gender = "F";
                        genderSave.setText(gender);
                    } else if (checkF.isChecked() == false) {
                        String gender = "";
                        genderSave.setText(gender);
                    }
                }
            }
        });
        checkF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkF.isChecked() == true) {
                    if (checkM.isChecked() == false) {
                        String gender = "F";
                        genderSave.setText(gender);
                    } else if (checkM.isChecked() == true) {
                        String gender = "A";
                        genderSave.setText(gender);
                    }
                } else if (checkF.isChecked() == false) {
                    if (checkM.isChecked() == true) {
                        String gender = "M";
                        genderSave.setText(gender);
                    } else if (checkM.isChecked() == false) {
                        String gender = "";
                        genderSave.setText(gender);
                    }
                }
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

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (a == false) {
                    a = true;
                }
                if (typespin.getItem(position).toString().equalsIgnoreCase("서빙or주방")) {
                    typeSave.setText("E");
                } else if (typespin.getItem(position).toString().equalsIgnoreCase("매장관리")) {
                    typeSave.setText("M");
                } else if (typespin.getItem(position).toString().equalsIgnoreCase("서비스")) {
                    typeSave.setText("S");
                } else if (typespin.getItem(position).toString().equalsIgnoreCase("생산or기능")) {
                    typeSave.setText("P");
                } else {
                    typeSave.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (a == false) {
                    a = true;
                }
                if (timespin.getItem(position).toString().equalsIgnoreCase("모두가능")) {
                    timeSave.setText("A");
                } else if (timespin.getItem(position).toString().equalsIgnoreCase("주말")) {
                    timeSave.setText("W");
                } else if (timespin.getItem(position).toString().equalsIgnoreCase("평일")) {
                    timeSave.setText("D");
                } else if (timespin.getItem(position).toString().equalsIgnoreCase("평일야간")) {
                    timeSave.setText("N");
                } else {
                    timeSave.setText("");
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
                townSave.setText(townspin.getItem(position).toString());
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
                            boroughSave.setText(boroughspin.getItem(position).toString());
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
                            boroughSave.setText(boroughspin.getItem(position).toString());
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
                String month = monthspin.getItem(position).toString();
                monthSave.setText(month);
                if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8") || month.equals("10") || month.equals("12")) {
                    dayspin = ArrayAdapter.createFromResource(SubstituteInsertActivity.this, R.array.day_31, R.layout.support_simple_spinner_dropdown_item);
                    dayspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);

                    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (a == false) {
                                a = true;
                            }
                            daySave.setText(dayspin.getItem(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (month.equals("2")) {
                    dayspin = ArrayAdapter.createFromResource(SubstituteInsertActivity.this, R.array.day_29, R.layout.support_simple_spinner_dropdown_item);
                    dayspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);

                    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (a == false) {
                                a = true;
                            }
                            daySave.setText(dayspin.getItem(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    dayspin = ArrayAdapter.createFromResource(SubstituteInsertActivity.this, R.array.day_30, R.layout.support_simple_spinner_dropdown_item);
                    dayspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);

                    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (a == false) {
                                a = true;
                            }
                            daySave.setText(dayspin.getItem(position).toString());
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
        Button btnBack = (Button) findViewById(R.id.btn_back_sub_insert);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTitle.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(v.getRootView(), "제목을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (townSave.getText().toString().trim().equalsIgnoreCase("") || townSave.getText().toString().equalsIgnoreCase("======")) {
                    Snackbar.make(v.getRootView(), "지역을 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (boroughSave.getText().toString().trim().equalsIgnoreCase("") || boroughSave.getText().toString().equalsIgnoreCase("======")) {
                    Snackbar.make(v.getRootView(), "지역을 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (editPay.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(v.getRootView(), "급여를 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (Integer.parseInt(editPay.getText().toString()) < 6030) {
                    Snackbar.make(v.getRootView(), "최저시급 미만으로 설정할 수 없습니다.", Snackbar.LENGTH_SHORT).show();
                } else if (timeSave.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(v.getRootView(), "시간대를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (monthSave.getText().toString().trim().equalsIgnoreCase("==") || monthSave.getText().toString().equalsIgnoreCase("")) {
                    Snackbar.make(v.getRootView(), "채용마감 날짜를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (daySave.getText().toString().trim().equalsIgnoreCase("==") || daySave.getText().toString().equalsIgnoreCase("")) {
                    Snackbar.make(v.getRootView(), "채용마감 날짜를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (typeSave.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(v.getRootView(), "직종을 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (editMinAge.getText().toString().trim().equalsIgnoreCase("") || editMaxAge.getText().toString().equalsIgnoreCase("")) {
                    Snackbar.make(v.getRootView(), "연령을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (checkF.isChecked() == false && checkM.isChecked() == false) {
                    Snackbar.make(v.getRootView(), "성별을 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                } else if (editContent.getText().toString().trim().equalsIgnoreCase("")) {
                    Snackbar.make(v.getRootView(), "내용을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder ad = new AlertDialog.Builder(SubstituteInsertActivity.this);
                    ad.setMessage("단기알바 게시글을 등록하겠습니까?").setCancelable(false).setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new HRInsertTask().execute("id_1020",
                                            editTitle.getText().toString(),
                                            editContent.getText().toString(),
                                            townSave.getText().toString() + " " + boroughSave.getText().toString(),
                                            editPay.getText().toString(),
                                            timeSave.getText().toString(),
                                            "2016-" + monthSave.getText().toString() + "-" + daySave.getText().toString(),
                                            typeSave.getText().toString(),
                                            editMinAge.getText().toString(),
                                            editMaxAge.getText().toString(),
                                            genderSave.getText().toString());
                                    Log.e("???>>>>>>>>", editTitle.getText().toString() +
                                            editContent.getText().toString() +
                                            townSave.getText().toString() + " " + boroughSave.getText().toString() +
                                            editPay.getText().toString() +
                                            timeSave.getText().toString() +
                                            "2016-" + monthSave.getText().toString() + "-" + daySave.getText().toString() +
                                            typeSave.getText().toString() +
                                            editMinAge.getText().toString() +
                                            editMaxAge.getText().toString() +
                                            genderSave.getText().toString());
                                    Intent intent = new Intent(SubstituteInsertActivity.this, SubstituteActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    AlertDialog alertDialog = ad.create();
                    alertDialog.show();
                }
            }

        });
    }

    /* 등록 버튼 백그라운드 쓰레드 */
    private class HRInsertTask extends AsyncTask<String, Void, ArrayList<HRJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<HRJsonObject> doInBackground(String... params) {

            ArrayList<HRJsonObject> albaVlaues = null;
            HttpURLConnection connection = null;
            String id = params[0];
            String title = params[1];
            String content = params[2];
            String local = params[3];
            String pay = params[4];
            String time = params[5];
            String finish = params[6];
            String type = params[7];
            String min_age = params[8];
            String max_age = params[9];
            String gender = params[10];
            BufferedReader fromServer = null;
            try {
                URL url = new URL(HaumanURLConstant.HR_HR_INSERT_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.write(("ID=" + id
                        + "&HR_TITLE=" + title
                        + "&HR_CONTENT=" + content
                        + "&HR_LOCAL=" + local
                        + "&HR_PAY=" + pay
                        + "&HR_TIME=" + time
                        + "&HR_FINISH=" + finish
                        + "&HR_TYPE=" + type
                        + "&HR_MIN_AGE=" + min_age
                        + "&HR_MAX_AGE=" + max_age
                        + "&HR_GENDER=" + gender).getBytes("UTF-8"));
                toServer.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonBuf = new StringBuilder();
                    String line = "";

                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }

                    JSONObject root = new JSONObject(jsonBuf.toString());
                    String resultValue = root.getString("result");

                    if (resultValue.equalsIgnoreCase("success")) {

                        albaVlaues = new ArrayList<HRJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_insert");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {

                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            HRJsonObject vo = new HRJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.HR_TITLE = jsonObj.getString("HR_TITLE");
                            vo.HR_CONTENT = jsonObj.getString("HR_CONTENT");
                            vo.HR_LOCAL = jsonObj.getString("HR_LOCAL");
                            vo.HR_PAY = jsonObj.getString("HR_PAY");
                            vo.HR_TIME = jsonObj.getString("HR_TIME");
                            vo.HR_FINISH = jsonObj.getString("HR_FINISH");
                            vo.HR_TYPE = jsonObj.getString("HR_TYPE");
                            vo.HR_MIN_AGE = jsonObj.getString("HR_MIN_AGE");
                            vo.HR_MAX_AGE = jsonObj.getString("HR_MAX_AGE");
                            vo.HR_GENDER = jsonObj.getString("HR_GENDER");
                            vo.HR_DATE = jsonObj.getString("HR_DATE");
                            vo.HR_FINISH_CHECK = jsonObj.getString("HR_FINISH_CHECK");
                            vo.HR_NUMBER = jsonObj.getInt("HR_NUMBER");
                            albaVlaues.add(vo);
                        }
                    }
                } else {
                    //http 에러 처리
                }
            } catch (Exception e) {
                Log.e("AlbaInsert 문제발생", e.toString());

            } finally {
                if (fromServer != null) {
                    try {
                        fromServer.close();

                    } catch (IOException iew) {
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return albaVlaues;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SubstituteInsertActivity.this);
            pd = ProgressDialog.show(SubstituteInsertActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(ArrayList<HRJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (pd != null) {
                pd.dismiss();
            }
        }
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

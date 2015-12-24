package co.kr.skt.haruman;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.List;

/**
 * Created by T on 2015-12-02.
 */
public class SubstituteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    boolean mlnitSpinner;
    RecyclerView mRecyclerView_sub;
    SubstituteAdapter mSubstituteAdapter;
    Spinner spinnerTown, spinnerBorough, spinnerType;
    ArrayAdapter<CharSequence> townspin, boroughspin, typespin;
    Button buttonAdd;
    ArrayList<HRJsonObject> albaValues;
    EditText townSave, boroughSave, typeSave;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        mRecyclerView_sub = (RecyclerView) findViewById(R.id.sub_recyclerview);

        mRecyclerView_sub.setAdapter(mSubstituteAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView_sub.setLayoutManager(manager);

        townSave = (EditText)findViewById(R.id.town_save);
        boroughSave = (EditText)findViewById(R.id.borough_save);
        typeSave = (EditText)findViewById(R.id.type_save);

        searchButton = (Button)findViewById(R.id.search_btn);

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
                Intent intent = new Intent(SubstituteActivity.this, SubstituteActivity.class);
                startActivity(intent);
                finish();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        alba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubstituteActivity.this, AlbaActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubstituteActivity.this, SettingActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubstituteActivity.this, AfterActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        spinnerTown = (Spinner) findViewById(R.id.spinner_town_substitute);
        spinnerType = (Spinner) findViewById(R.id.spinner_type_substitute);
        spinnerBorough = (Spinner) findViewById(R.id.spinner_borough_substitue);

        townspin = ArrayAdapter.createFromResource(this, R.array.spinner_town, R.layout.support_simple_spinner_dropdown_item);
        townspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        typespin = ArrayAdapter.createFromResource(this, R.array.spinner_type, R.layout.support_simple_spinner_dropdown_item);
        typespin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerTown.setAdapter(townspin);
        spinnerType.setAdapter(typespin);

        spinnerTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mlnitSpinner == false) {
                    mlnitSpinner = true;
                    return;
                }

                if (townspin.getItem(position).toString().trim().equalsIgnoreCase("=====")) {
                    townSave.setText("");
                    Log.e("타운", townSave.getText().toString());
                } else {
                    townSave.setText(townspin.getItem(position).toString());
                    Log.e("타운", townSave.getText().toString());
                }

                if (townspin.getItem(position).equals("서울시")) {
                    boroughspin = ArrayAdapter.createFromResource(SubstituteActivity.this, R.array.spinner_borough_seoul, R.layout.support_simple_spinner_dropdown_item);
                    spinnerBorough.setAdapter(boroughspin);

                    spinnerBorough.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (mlnitSpinner == false) {
                                mlnitSpinner = true;
                                return;
                            }
                            if (boroughspin.getItem(position).toString().trim().equalsIgnoreCase("=====")) {
                                boroughSave.setText("");
                                Log.e("구", boroughSave.getText().toString());
                            } else {
                                boroughSave.setText(boroughspin.getItem(position).toString());
                                Log.e("구", boroughSave.getText().toString());
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (townspin.getItem(position).equals("경기도")) {
                    boroughspin = ArrayAdapter.createFromResource(SubstituteActivity.this, R.array.spinner_borough_gyungki, R.layout.support_simple_spinner_dropdown_item);
                    spinnerBorough.setAdapter(boroughspin);

                    spinnerBorough.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (mlnitSpinner == false) {
                                mlnitSpinner = true;
                                return;
                            }
                            if (boroughspin.getItem(position).toString().trim().equalsIgnoreCase("=====")) {
                                boroughSave.setText("");
                                Log.e("구", boroughSave.getText().toString());
                            } else {
                                boroughSave.setText(boroughspin.getItem(position).toString());
                                Log.e("구", boroughSave.getText().toString());
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
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mlnitSpinner == false) {
                    mlnitSpinner = true;
                    return;
                }
                if (typespin.getItem(position).toString().equalsIgnoreCase("매장관리")) {
                    typeSave.setText("M");
                } else if (typespin.getItem(position).toString().equalsIgnoreCase("서빙○주방")) {
                    typeSave.setText("E");
                } else if (typespin.getItem(position).toString().equalsIgnoreCase("생산○기능")) {
                    typeSave.setText("P");
                } else if (typespin.getItem(position).toString().equalsIgnoreCase("서비스")) {
                    typeSave.setText("S");
                } else {
                    typeSave.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonAdd = (Button)findViewById(R.id.btn_add_substitute);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubstituteActivity.this, SubstituteInsertActivity.class);
                startActivity(intent);
            }
        });
        Button btnBack = (Button)findViewById(R.id.btn_back_sub);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new HrListTask().execute();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (townSave.getText().toString().trim().equalsIgnoreCase("") && boroughSave.getText().toString().trim().equalsIgnoreCase("")) {
                    new HrSearchTypeTask().execute(typeSave.getText().toString());
                    boroughSave.setText("");
                    townSave.setText("");
                    typeSave.setText("");
                    spinnerType.setPrompt(typespin.getItem(1));
                } else if (typeSave.getText().toString().trim().equalsIgnoreCase("")) {
                    new HrSearchLocalTask().execute(townSave.getText().toString() + " " + boroughSave.getText().toString());
                    boroughSave.setText("");
                    townSave.setText("");
                    typeSave.setText("");
                } else {
                    new HrSearchTypeAndLocalTask().execute(typeSave.getText().toString(), townSave.getText().toString() + " " + boroughSave.getText().toString());
                    boroughSave.setText("");
                    townSave.setText("");
                    typeSave.setText("");
                }
                Log.e("로컬", townSave.getText().toString() + " " + boroughSave.getText().toString());
                Log.e("타입", typeSave.getText().toString());
            }
        });
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

    /* 지역으로 검색한 결과 출력해주는 백그라운드 쓰레드 */
    private class HrSearchLocalTask extends AsyncTask<String, Void, ArrayList<HRJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<HRJsonObject> doInBackground(String... params) {

            albaValues = new ArrayList();
            String local = params[0];
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.HR_LOCAL_SEARCH_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.write(("HR_LOCAL=" + local).getBytes("UTF-8"));
                toServer.close();

                int responseCod = connection.getResponseCode();

                if (responseCod == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonBuf = new StringBuilder();
                    String line = "";

                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
                    JSONObject root = new JSONObject(jsonBuf.toString());
                    String resultValue = root.getString("result");

                    if (resultValue.equalsIgnoreCase("success")) {
                        albaValues = new ArrayList<HRJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_local");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {
                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            HRJsonObject vo = new HRJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.HR_TITLE = jsonObj.getString("HR_TITLE");
                            vo.HR_FINISH = jsonObj.getString("HR_FINISH");
                            vo.HR_LOCAL = jsonObj.getString("HR_LOCAL");
                            vo.HR_TYPE = jsonObj.getString("HR_TYPE");
                            vo.HR_PAY = jsonObj.getString("HR_PAY");
                            vo.HR_DATE = jsonObj.getString("HR_DATE");
                            vo.HR_FINISH_CHECK = jsonObj.getString("HR_FINISH_CHECK");
                            vo.HR_NUMBER = jsonObj.getInt("HR_NUMBER");

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("HrSearchLocalTask 문제발생", e.toString());
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
            return albaValues;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SubstituteActivity.this);
            pd = ProgressDialog.show(SubstituteActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<HRJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {
                Log.e("사이즈>>", albaJsonObjects.size() + "");

                mSubstituteAdapter = new SubstituteAdapter(albaValues);
                mRecyclerView_sub.setAdapter(mSubstituteAdapter);

                mRecyclerView_sub.addOnItemTouchListener(new RecyclerItemClickListener(SubstituteActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Log.e("클릭됨>>", position + "");
                        Intent intent = new Intent(SubstituteActivity.this, AlbaInfo.class);
                        finish();
                        intent.putExtra("ListPosition", albaValues.get(position).HR_NUMBER);
                        startActivity(intent);
                    }
                }));
//                txtCount.setText(mAdapter.getItemCount() + "");
                if (pd != null) {
                    pd.dismiss();
                }
            } else {
                pd.dismiss();
                townSave.setText("");
                boroughSave.setText("");
                Snackbar.make(mRecyclerView_sub, "검색한 결과가 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        }
    }






    /* 직종과 지역으로 검색한 결과 출력해주는 백그라운드 쓰레드 */
    private class HrSearchTypeAndLocalTask extends AsyncTask<String, Void, ArrayList<HRJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<HRJsonObject> doInBackground(String... params) {

            albaValues = new ArrayList();
            String type = params[0];
            String local = params[1];
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.HR_TYPEANDLOCAL_SEARCH_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.write(("HR_TYPE=" + type + "&HR_LOCAL=" + local).getBytes("UTF-8"));
                toServer.close();

                int responseCod = connection.getResponseCode();

                if (responseCod == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonBuf = new StringBuilder();
                    String line = "";

                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
                    JSONObject root = new JSONObject(jsonBuf.toString());
                    String resultValue = root.getString("result");

                    if (resultValue.equalsIgnoreCase("success")) {
                        albaValues = new ArrayList<HRJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_typeAndlocal");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {
                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            HRJsonObject vo = new HRJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.HR_TITLE = jsonObj.getString("HR_TITLE");
                            vo.HR_FINISH = jsonObj.getString("HR_FINISH");
                            vo.HR_LOCAL = jsonObj.getString("HR_LOCAL");
                            vo.HR_TYPE = jsonObj.getString("HR_TYPE");
                            vo.HR_PAY = jsonObj.getString("HR_PAY");
                            vo.HR_DATE = jsonObj.getString("HR_DATE");
                            vo.HR_FINISH_CHECK = jsonObj.getString("HR_FINISH_CHECK");
                            vo.HR_NUMBER = jsonObj.getInt("HR_NUMBER");

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("HrSearchTypeAndLocalTask 문제발생", e.toString());
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
            return albaValues;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SubstituteActivity.this);
            pd = ProgressDialog.show(SubstituteActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<HRJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {
                Log.e("사이즈>>", albaJsonObjects.size() + "");

                mSubstituteAdapter = new SubstituteAdapter(albaValues);
                mRecyclerView_sub.setAdapter(mSubstituteAdapter);

//                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AlbaActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//
//                        Log.e("클릭됨>>", position + "");
//                        Intent intent = new Intent(AlbaActivity.this, AlbaInfo.class);
//                        finish();
//                        intent.putExtra("ListPosition", albaValues.get(position).AB_NUMBER);
//                        startActivity(intent);
//                    }
//                }));
//                txtCount.setText(mAdapter.getItemCount() + "");
                if (pd != null) {
                    pd.dismiss();
                }
            } else {
                pd.dismiss();
                townSave.setText("");
                boroughSave.setText("");
                Snackbar.make(mRecyclerView_sub, "검색한 결과가 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        }
    }






    /* 직종으로 검색한 결과 출력해주는 백그라운드 쓰레드 */
    private class HrSearchTypeTask extends AsyncTask<String, Void, ArrayList<HRJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<HRJsonObject> doInBackground(String... params) {

            albaValues = new ArrayList();
            String type = params[0];
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.HR_TYPE_SEARCH_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.write(("HR_TYPE=" + type).getBytes("UTF-8"));
                toServer.close();

                int responseCod = connection.getResponseCode();

                if (responseCod == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonBuf = new StringBuilder();
                    String line = "";

                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
                    JSONObject root = new JSONObject(jsonBuf.toString());
                    String resultValue = root.getString("result");

                    if (resultValue.equalsIgnoreCase("success")) {
                        albaValues = new ArrayList<HRJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_type");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {
                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            HRJsonObject vo = new HRJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.HR_TITLE = jsonObj.getString("HR_TITLE");
                            vo.HR_FINISH = jsonObj.getString("HR_FINISH");
                            vo.HR_LOCAL = jsonObj.getString("HR_LOCAL");
                            vo.HR_TYPE = jsonObj.getString("HR_TYPE");
                            vo.HR_PAY = jsonObj.getString("HR_PAY");
                            vo.HR_DATE = jsonObj.getString("HR_DATE");
                            vo.HR_FINISH_CHECK = jsonObj.getString("HR_FINISH_CHECK");
                            vo.HR_NUMBER = jsonObj.getInt("HR_NUMBER");

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("HrSearchTypeTask 문제발생", e.toString());
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
            return albaValues;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SubstituteActivity.this);
            pd = ProgressDialog.show(SubstituteActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<HRJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {
                Log.e("사이즈>>", albaJsonObjects.size() + "");

                mSubstituteAdapter = new SubstituteAdapter(albaValues);
                mRecyclerView_sub.setAdapter(mSubstituteAdapter);

//                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AlbaActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//
//                        Log.e("클릭됨>>", position + "");
//                        Intent intent = new Intent(AlbaActivity.this, AlbaInfo.class);
//                        finish();
//                        intent.putExtra("ListPosition", albaValues.get(position).AB_NUMBER);
//                        startActivity(intent);
//                    }
//                }));
//                txtCount.setText(mAdapter.getItemCount() + "");
                if (pd != null) {
                    pd.dismiss();
                }
            } else {
                pd.dismiss();
                townSave.setText("");
                boroughSave.setText("");
                Snackbar.make(mRecyclerView_sub, "검색한 결과가 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        }
    }






    /* 대타 게시글 목록을 추출하는 백그라운드 쓰레드 */
    private class HrListTask extends AsyncTask<String, Void, ArrayList<HRJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<HRJsonObject> doInBackground(String... params) {

            albaValues = new ArrayList();
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.HR_LIST_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.close();

                int responseCod = connection.getResponseCode();

                if (responseCod == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonBuf = new StringBuilder();
                    String line = "";

                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
                    JSONObject root = new JSONObject(jsonBuf.toString());
                    String resultValue = root.getString("result");

                    if (resultValue.equalsIgnoreCase("success")) {
                        albaValues = new ArrayList<HRJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_list");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {
                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            HRJsonObject vo = new HRJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.HR_TITLE = jsonObj.getString("HR_TITLE");
                            vo.HR_LOCAL = jsonObj.getString("HR_LOCAL");
                            vo.HR_PAY = jsonObj.getString("HR_PAY");
                            vo.HR_TYPE = jsonObj.getString("HR_TYPE");
                            vo.HR_DATE = jsonObj.getString("HR_DATE");
                            vo.HR_FINISH = jsonObj.getString("HR_FINISH");
                            vo.HR_FINISH_CHECK = jsonObj.getString("HR_FINISH_CHECK");
                            vo.HR_NUMBER = jsonObj.getInt("HR_NUMBER");

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("HrListTask 문제발생", e.toString());
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
            return albaValues;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SubstituteActivity.this);
            pd = ProgressDialog.show(SubstituteActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<HRJsonObject> hrJsonObjects) {
            super.onPostExecute(hrJsonObjects);

            if (hrJsonObjects != null && hrJsonObjects.size() > 0) {
                Log.e("사이즈>>", hrJsonObjects.size() + "");

                mSubstituteAdapter = new SubstituteAdapter(albaValues);
                mRecyclerView_sub.setAdapter(mSubstituteAdapter);

                mRecyclerView_sub.addOnItemTouchListener(new RecyclerItemClickListener(SubstituteActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Log.e("클릭됨>>", position + "");
                        Intent intent = new Intent(SubstituteActivity.this, HRInfo.class);
                        intent.putExtra("HRListPosition", albaValues.get(position).HR_NUMBER);
                        finish();
                        startActivity(intent);
                    }
                }));
//                txtCount.setText(mAdapter.getItemCount() + "");
                if (pd != null) {
                    pd.dismiss();
                }
            } else {
                pd.dismiss();
                Snackbar.make(mRecyclerView_sub, "연결상태가 원할하지 않습니다. 다시 시도해주세요.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}

class SubstituteAdapter extends RecyclerView.Adapter<SubstituteViewHolder> {

    List<HRJsonObject> albaJsonObjects = new ArrayList<HRJsonObject>();

    SubstituteViewHolder.OnItemClickListener mItemClickListener;

    public SubstituteAdapter(List<HRJsonObject> HRJsonObjects) {
        this.albaJsonObjects = HRJsonObjects;
    }

    @Override
    public SubstituteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.substitutelist_view, null);
        return new SubstituteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubstituteViewHolder holder, int position) {
        holder.setData(albaJsonObjects.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return albaJsonObjects.size();
    }
}

class SubstituteViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener {
        public void onItemClick(String name, int position);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }

    EditText editTitle, editType, editLocal, editPay;

    public SubstituteViewHolder(View itemView) {
        super(itemView);
        editTitle = (EditText) itemView.findViewById(R.id.edit_title_sub);
        editType = (EditText) itemView.findViewById(R.id.edit_type_sub);
        editLocal = (EditText) itemView.findViewById(R.id.edit_local_sub);
        editPay = (EditText) itemView.findViewById(R.id.edit_pay_sub);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mTitle, position);
                    mListener.onItemClick(mType, position);
                    mListener.onItemClick(mLocal, position);
                    mListener.onItemClick(mPay, position);
                }
            }
        });
    }

    String mTitle, mType, mLocal, mPay;

    public void setData(HRJsonObject albaJsonObject) {
        mTitle = albaJsonObject.HR_TITLE;
        mType = albaJsonObject.HR_TYPE;
        mLocal = albaJsonObject.HR_LOCAL;
        mPay = albaJsonObject.HR_PAY;
        editTitle.setText(mTitle);
        if (mType.equalsIgnoreCase("E")) {
            editType.setText("서빙○주방");
        } else if (mType.equalsIgnoreCase("M")) {
            editType.setText("매장관리");
        } else if (mType.equalsIgnoreCase("S")) {
            editType.setText("서비스");
        } else if (mType.equalsIgnoreCase("P")) {
            editType.setText("생산○기능");
        }
        editLocal.setText(mLocal);
        editPay.setText(mPay + "원");
    }
}

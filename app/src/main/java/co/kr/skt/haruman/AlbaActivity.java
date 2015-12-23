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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
 * Created by T on 2015-12-01.
 */
public class AlbaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    AlbaAdapter mAdapter;
    boolean mlnitSpinner;
    ArrayAdapter<CharSequence> townspin;
    ArrayAdapter<CharSequence> boroughspin;
    ArrayAdapter<CharSequence> typespin;
    Spinner spinnerTown, spinnerBorough, spinnerType;
    Button buttonNavigationBack;
    ArrayList<AlbaJsonObject> albaValues;
    EditText townSave, boroughSave, typeSave;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        townSave = (EditText) findViewById(R.id.town_save);
        boroughSave = (EditText) findViewById(R.id.borough_save);
        typeSave = (EditText) findViewById(R.id.type_save);
        searchButton = (Button) findViewById(R.id.btn_search);

        setTitle("단기알바 정보");

        buttonNavigationBack = (Button) findViewById(R.id.btn_navigationBack);
        buttonNavigationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnFit = (Button) findViewById(R.id.btn_fit);
        btnFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AlbaActivity.this, "12321312", Toast.LENGTH_SHORT).show();
            }
        });
        Button substitute = (Button) findViewById(R.id.nav_substitute);
        Button alba = (Button) findViewById(R.id.nav_alba);
        Button setting = (Button) findViewById(R.id.nav_setting);
        Button after = (Button) findViewById(R.id.nav_after);

        substitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbaActivity.this, SubstituteActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }

        });
        alba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbaActivity.this, AlbaActivity.class);
                startActivity(intent);
                finish();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbaActivity.this, SettingActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbaActivity.this, AfterActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        spinnerTown = (Spinner) findViewById(R.id.spinner_town_main);
        spinnerBorough = (Spinner) findViewById(R.id.spinner_borough_main);
        spinnerType = (Spinner) findViewById(R.id.spinner_type_main);

        townspin = ArrayAdapter.createFromResource(this, R.array.spinner_town, R.layout.support_simple_spinner_dropdown_item);
        townspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerTown.setAdapter(townspin);
        typespin = ArrayAdapter.createFromResource(this, R.array.spinner_type, R.layout.support_simple_spinner_dropdown_item);
        typespin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerType.setAdapter(typespin);

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
                    boroughspin = ArrayAdapter.createFromResource(AlbaActivity.this, R.array.spinner_borough_seoul, R.layout.support_simple_spinner_dropdown_item);
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
                    boroughspin = ArrayAdapter.createFromResource(AlbaActivity.this, R.array.spinner_borough_gyungki, R.layout.support_simple_spinner_dropdown_item);
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
        new AlbaListTask().execute();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (townSave.getText().toString().trim().equalsIgnoreCase("") && boroughSave.getText().toString().trim().equalsIgnoreCase("")) {
                    new AlbaSearchTypeTask().execute(typeSave.getText().toString());
                    boroughSave.setText("");
                    townSave.setText("");
                    typeSave.setText("");
                    spinnerType.setPrompt(typespin.getItem(1));
                } else if (typeSave.getText().toString().trim().equalsIgnoreCase("")) {
                    new AlbaSearchLocalTask().execute(townSave.getText().toString()+" "+boroughSave.getText().toString());
                    boroughSave.setText("");
                    townSave.setText("");
                    typeSave.setText("");
                } else {
                    new AlbaSearchTypeAndLocalTask().execute(typeSave.getText().toString(), townSave.getText().toString()+" "+boroughSave.getText().toString());
                    boroughSave.setText("");
                    townSave.setText("");
                    typeSave.setText("");
                }
                Log.e("로컬", townSave.getText().toString() + " " + boroughSave.getText().toString());
                Log.e("타입", typeSave.getText().toString());
            }
        });
    }


    /* 단기알바 게시글 목록을 추출하는 백그라운드 쓰레드 */
    private class AlbaListTask extends AsyncTask<String, Void, ArrayList<AlbaJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<AlbaJsonObject> doInBackground(String... params) {

            albaValues = new ArrayList();
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.SEARCH_LIST_DO);
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
                        albaValues = new ArrayList<AlbaJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_list");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {
                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            AlbaJsonObject vo = new AlbaJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.AB_TITLE = jsonObj.getString("AB_TITLE");
                            vo.AB_FINISH = jsonObj.getString("AB_FINISH");
                            vo.AB_TYPE = jsonObj.getString("AB_TYPE");
                            vo.AB_LOCAL = jsonObj.getString("AB_LOCAL");
                            vo.AB_PAY = jsonObj.getString("AB_PAY");
                            vo.AB_DATE = jsonObj.getString("AB_DATE");
                            vo.AB_FINISH_CHECK = jsonObj.getString("AB_FINISH_CHECK");
                            vo.AB_NUMBER = jsonObj.getInt("AB_NUMBER");

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("AlbaListTask 문제발생", e.toString());
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
            pd = new ProgressDialog(AlbaActivity.this);
            pd = ProgressDialog.show(AlbaActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<AlbaJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {
                Log.e("사이즈>>", albaJsonObjects.size() + "");

                mAdapter = new AlbaAdapter(albaValues);
                mRecyclerView.setAdapter(mAdapter);

                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AlbaActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Log.e("클릭됨>>", position + "");
                        Intent intent = new Intent(AlbaActivity.this, AlbaInfo.class);
                        finish();
                        intent.putExtra("ListPosition", albaValues.get(position).AB_NUMBER);
                        startActivity(intent);
                    }
                }));
//                txtCount.setText(mAdapter.getItemCount() + "");
                if (pd != null) {
                    pd.dismiss();
                }
            } else {
                pd.dismiss();
                Snackbar.make(mRecyclerView, "연결상태가 원할하지 않습니다. 다시 시도해주세요.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    /* 직종으로 검색한 결과 출력해주는 백그라운드 쓰레드 */
    private class AlbaSearchTypeTask extends AsyncTask<String, Void, ArrayList<AlbaJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<AlbaJsonObject> doInBackground(String... params) {

            albaValues = new ArrayList();
            String type = params[0];
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.SEARCH_TYPE_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.write(("AB_TYPE=" + type).getBytes("UTF-8"));
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
                        albaValues = new ArrayList<AlbaJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_type");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {
                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            AlbaJsonObject vo = new AlbaJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.AB_TITLE = jsonObj.getString("AB_TITLE");
                            vo.AB_FINISH = jsonObj.getString("AB_FINISH");
                            vo.AB_LOCAL = jsonObj.getString("AB_LOCAL");
                            vo.AB_TYPE = jsonObj.getString("AB_TYPE");
                            vo.AB_PAY = jsonObj.getString("AB_PAY");
                            vo.AB_DATE = jsonObj.getString("AB_DATE");
                            vo.AB_FINISH_CHECK = jsonObj.getString("AB_FINISH_CHECK");
                            vo.AB_NUMBER = jsonObj.getInt("AB_NUMBER");

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("AlbaSearchTypeTask 문제발생", e.toString());
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
            pd = new ProgressDialog(AlbaActivity.this);
            pd = ProgressDialog.show(AlbaActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<AlbaJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {
                Log.e("사이즈>>", albaJsonObjects.size() + "");

                mAdapter = new AlbaAdapter(albaValues);
                mRecyclerView.setAdapter(mAdapter);

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
                Snackbar.make(mRecyclerView, "검색한 결과가 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    /* 직종과 지역으로 검색한 결과 출력해주는 백그라운드 쓰레드 */
    private class AlbaSearchTypeAndLocalTask extends AsyncTask<String, Void, ArrayList<AlbaJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<AlbaJsonObject> doInBackground(String... params) {

            albaValues = new ArrayList();
            String type = params[0];
            String local = params[1];
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.SEARCH_TYPEANDLOCAL_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.write(("AB_TYPE=" + type + "&AB_LOCAL=" + local).getBytes("UTF-8"));
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
                        albaValues = new ArrayList<AlbaJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_typeAndlocal");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {
                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            AlbaJsonObject vo = new AlbaJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.AB_TITLE = jsonObj.getString("AB_TITLE");
                            vo.AB_FINISH = jsonObj.getString("AB_FINISH");
                            vo.AB_LOCAL = jsonObj.getString("AB_LOCAL");
                            vo.AB_TYPE = jsonObj.getString("AB_TYPE");
                            vo.AB_PAY = jsonObj.getString("AB_PAY");
                            vo.AB_DATE = jsonObj.getString("AB_DATE");
                            vo.AB_FINISH_CHECK = jsonObj.getString("AB_FINISH_CHECK");
                            vo.AB_NUMBER = jsonObj.getInt("AB_NUMBER");

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("TypeAndLocalTask 문제발생", e.toString());
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
            pd = new ProgressDialog(AlbaActivity.this);
            pd = ProgressDialog.show(AlbaActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<AlbaJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {
                Log.e("사이즈>>", albaJsonObjects.size() + "");

                mAdapter = new AlbaAdapter(albaValues);
                mRecyclerView.setAdapter(mAdapter);

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
                Snackbar.make(mRecyclerView, "검색한 결과가 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    /* 지역으로 검색한 결과 출력해주는 백그라운드 쓰레드 */
    private class AlbaSearchLocalTask extends AsyncTask<String, Void, ArrayList<AlbaJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<AlbaJsonObject> doInBackground(String... params) {

            albaValues = new ArrayList();
            String local = params[0];
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.SEARCH_LOCAL_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.write(("AB_LOCAL=" + local).getBytes("UTF-8"));
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
                        albaValues = new ArrayList<AlbaJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_local");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {
                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            AlbaJsonObject vo = new AlbaJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.AB_TITLE = jsonObj.getString("AB_TITLE");
                            vo.AB_FINISH = jsonObj.getString("AB_FINISH");
                            vo.AB_LOCAL = jsonObj.getString("AB_LOCAL");
                            vo.AB_TYPE = jsonObj.getString("AB_TYPE");
                            vo.AB_PAY = jsonObj.getString("AB_PAY");
                            vo.AB_DATE = jsonObj.getString("AB_DATE");
                            vo.AB_FINISH_CHECK = jsonObj.getString("AB_FINISH_CHECK");
                            vo.AB_NUMBER = jsonObj.getInt("AB_NUMBER");

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("AlbaTask 문제발생", e.toString());
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
            pd = new ProgressDialog(AlbaActivity.this);
            pd = ProgressDialog.show(AlbaActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<AlbaJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {
                Log.e("사이즈>>", albaJsonObjects.size() + "");

                mAdapter = new AlbaAdapter(albaValues);
                mRecyclerView.setAdapter(mAdapter);

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
                Snackbar.make(mRecyclerView, "검색한 결과가 없습니다.", Snackbar.LENGTH_LONG).show();
            }
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

class AlbaAdapter extends RecyclerView.Adapter<AlbaViewHolder> {

    List<AlbaJsonObject> albaJsonObjects = new ArrayList<AlbaJsonObject>();

    AlbaViewHolder.OnItemClickListener mItemClickListener;

    public AlbaAdapter(List<AlbaJsonObject> albaJsonObjects) {
        this.albaJsonObjects = albaJsonObjects;
    }

    @Override
    public AlbaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.albalist_view, null);
        return new AlbaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbaViewHolder holder, int position) {
        holder.setData(albaJsonObjects.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return albaJsonObjects.size();
    }
}

class AlbaViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener {
        public void onItemClick(String name, int position);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }

    EditText editTitle, editFinish, editType, editLocal, editPay;

    public AlbaViewHolder(View itemView) {
        super(itemView);
        editTitle = (EditText) itemView.findViewById(R.id.edit_title);
        editFinish = (EditText) itemView.findViewById(R.id.edit_finish);
        editType = (EditText) itemView.findViewById(R.id.edit_type);
        editLocal = (EditText) itemView.findViewById(R.id.edit_local);
        editPay = (EditText) itemView.findViewById(R.id.edit_pay);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mTitle, position);
                    mListener.onItemClick(mFinish, position);
                    mListener.onItemClick(mType, position);
                    mListener.onItemClick(mLocal, position);
                    mListener.onItemClick(mPay, position);
                }
            }
        });
    }

    String mFinish, mTitle, mType, mLocal, mPay;

    public void setData(AlbaJsonObject albaJsonObject) {
        mTitle = albaJsonObject.AB_TITLE;
        mFinish = albaJsonObject.AB_FINISH;
        mType = albaJsonObject.AB_TYPE;
        mLocal = albaJsonObject.AB_LOCAL;
        mPay = albaJsonObject.AB_PAY;
        editTitle.setText(mTitle);
        editFinish.setText(mFinish);
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
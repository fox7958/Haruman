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
public class AfterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    AfterAdapter mAdapter;
    Spinner spinnerType;
    ArrayAdapter<CharSequence> typespin;
    boolean a;
    ArrayList<AfterJsonObject> albaValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btn_Insert = (Button) findViewById(R.id.btn_add_after);

        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AfterActivity.this, AfterInsertActivity.class);
                startActivity(intent);
            }
        });
        Button substitute = (Button)findViewById(R.id.nav_substitute);
        Button alba = (Button)findViewById(R.id.nav_alba);
        Button setting = (Button)findViewById(R.id.nav_setting);
        Button after = (Button)findViewById(R.id.nav_after);

        substitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterActivity.this, SubstituteActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        alba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterActivity.this, AlbaActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterActivity.this, SettingActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterActivity.this, AfterActivity.class);
                startActivity(intent);
                finish();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.after_recyclerview);

        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        spinnerType = (Spinner)findViewById(R.id.spinner_type_after);
        typespin = ArrayAdapter.createFromResource(this, R.array.spinner_type, R.layout.support_simple_spinner_dropdown_item);
        typespin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerType.setAdapter(typespin);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(a == false){
                    a = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btnBack = (Button)findViewById(R.id.btn_back_after);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new AfterListTask().execute();
    }

    /* 만족도 게시판 게시글 목록을 추출하는 백그라운드 쓰레드 */
    private class AfterListTask extends AsyncTask<String, Void, ArrayList<AfterJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<AfterJsonObject> doInBackground(String... params) {

            albaValues = new ArrayList();
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.AFTER_SEARCH_DO);
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
                        albaValues = new ArrayList<AfterJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_list");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {
                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            AfterJsonObject vo = new AfterJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.AFTER_TITLE = jsonObj.getString("AFTER_TITLE");
                            vo.AFTER_CONTENT = jsonObj.getString("AFTER_CONTENT");
                            vo.AFTER_LOCAL = jsonObj.getString("AFTER_LOCAL");
                            vo.AFTER_LEVEL = jsonObj.getString("AFTER_LEVEL");
                            vo.AFTER_PAY_TIME = jsonObj.getString("AFTER_PAY_TIME");
                            vo.AFTER_DATE = jsonObj.getString("AFTER_DATE");
                            vo.AFTER_NUMBER = jsonObj.getInt("AFTER_NUMBER");

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("AfterListTask 문제발생", e.toString());
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
            pd = new ProgressDialog(AfterActivity.this);
            pd = ProgressDialog.show(AfterActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<AfterJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {
                Log.e("사이즈>>", albaJsonObjects.size() + "");

                mAdapter = new AfterAdapter(albaValues);
                mRecyclerView.setAdapter(mAdapter);

                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AfterActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Log.e("클릭됨>>", position + "");
                        Intent intent = new Intent(AfterActivity.this, AFTERInfo.class);
                        finish();
                        intent.putExtra("ListPosition", albaValues.get(position).AFTER_NUMBER);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}

class AfterAdapter extends RecyclerView.Adapter<AfterViewHolder> {

    List<AfterJsonObject> albaJsonObjects = new ArrayList<AfterJsonObject>();

    AfterViewHolder.OnItemClickListener mItemClickListener;

    public AfterAdapter(List<AfterJsonObject> albaJsonObjects) {
        this.albaJsonObjects = albaJsonObjects;
    }

    @Override
    public AfterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_view, null);
        return new AfterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AfterViewHolder holder, int position) {
        holder.setData(albaJsonObjects.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return albaJsonObjects.size();
    }
}

class AfterViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener {
        public void onItemClick(String name, int position);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }

    EditText editTitle, editLocal;

    public AfterViewHolder(View itemView) {
        super(itemView);
        editTitle = (EditText) itemView.findViewById(R.id.edit_title_after);
        editLocal = (EditText) itemView.findViewById(R.id.edit_local_after);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mTitle, position);
                    mListener.onItemClick(mLocal, position);
                }
            }
        });
    }

    String mTitle, mLocal;

    public void setData(AfterJsonObject albaJsonObject) {
        mTitle = albaJsonObject.AFTER_TITLE;
        mLocal = albaJsonObject.AFTER_LOCAL;
        editTitle.setText(mTitle);
        editLocal.setText(mLocal);
    }
}
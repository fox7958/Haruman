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
    AlbaViewHolder albaViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        setTitle("단기알바 정보");

        mAdapter = new AlbaAdapter();
        mAdapter.setOnItemClickListener(new AlbaViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(AlbaActivity.this, "title : " + name, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

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

        Button btnFit = (Button)findViewById(R.id.btn_fit);
        btnFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AlbaActivity.this, "12321312", Toast.LENGTH_SHORT).show();
            }
        });
        Button substitute = (Button)findViewById(R.id.nav_substitute);
        Button alba = (Button)findViewById(R.id.nav_alba);
        Button setting = (Button)findViewById(R.id.nav_setting);
        Button after = (Button)findViewById(R.id.nav_after);

        substitute.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbaActivity.this, SubstituteActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }

        });
        alba.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbaActivity.this, AlbaActivity.class);
                startActivity(intent);
                finish();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbaActivity.this, SettingActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        after.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbaActivity.this, AfterActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        spinnerTown = (Spinner)findViewById(R.id.spinner_town_main);
        spinnerBorough = (Spinner)findViewById(R.id.spinner_borough_main);
        spinnerType = (Spinner)findViewById(R.id.spinner_type_main);

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
                Toast.makeText(AlbaActivity.this, townspin.getItem(position), Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(AlbaActivity.this, boroughspin.getItem(position), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AlbaActivity.this, boroughspin.getItem(position), Toast.LENGTH_SHORT).show();
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
    }
    private void initData() {
        for (int i = 0; i < 10 ; i++){
            mAdapter.add("12345678910");
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

    List<String> items = new ArrayList<String>();

    AlbaViewHolder.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(AlbaViewHolder.OnItemClickListener listener){
        mItemClickListener = listener;
    }
    public void add(String item){
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public AlbaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.albalist_view, null);
        return new AlbaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbaViewHolder holder, int position) {
        holder.setData(items.get(position), items.get(position), items.get(position), items.get(position), items.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
class AlbaViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener{
        void onItemClick(String name, int position);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    EditText editTitle;
    EditText editLocal;
    EditText editType;
    EditText editPay;
    EditText editFinish;

    public AlbaViewHolder(View itemView) {
        super(itemView);
        editTitle = (EditText)itemView.findViewById(R.id.edit_title);
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(mTitle, position);
                    mListener.onItemClick(mLocal, position);
                    mListener.onItemClick(mType, position);
                    mListener.onItemClick(mPay, position);
                    mListener.onItemClick(mFinish, position);
                }
            }
        });
        editLocal = (EditText)itemView.findViewById(R.id.edit_local);
        editType = (EditText)itemView.findViewById(R.id.edit_type);
        editPay = (EditText)itemView.findViewById(R.id.edit_pay);
        editFinish = (EditText)itemView.findViewById(R.id.edit_finish);

    }
    String mLocal;
    String mType;
    String mPay;
    String mTitle;
    String mFinish;

    public void setData(String title, String local, String type, String pay, String finish){
        mTitle = title;
        mLocal = local;
        mType = type;
        mPay = pay;
        mFinish = finish;
        editTitle.setText(title);
        editLocal.setText(local);
        editType.setText(type);
        editPay.setText(pay);
        editFinish.setText(finish);
    }
}

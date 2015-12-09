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
 * Created by T on 2015-12-02.
 */
public class AfterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    AfterAdapter mAdapter;
    Spinner spinnerType;
    ArrayAdapter<CharSequence> typespin;
    boolean a;

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

        mAdapter = new AfterAdapter();
        mAdapter.setOnItemClickListener(new AfterViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(AfterActivity.this, "afteractivity : " + name, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        initData();

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
    }

    public void initData(){
        for (int i=0; i<11; i++){
            mAdapter.add("3ㅓㄹ329ㅓㄹ290ㅓ0932");
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

    List<String> items = new ArrayList<String>();

    AfterViewHolder.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(AfterViewHolder.OnItemClickListener listener){
        mItemClickListener = listener;
    }
    public void add(String item){
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public AfterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_view, null);
        return new AfterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AfterViewHolder holder, int position) {
        holder.setData(items.get(position), items.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class AfterViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener{
        public void onItemClick(String name, int position);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    EditText editTitle;
    EditText editLocal;

    public AfterViewHolder(View itemView) {
        super(itemView);
        editTitle = (EditText)itemView.findViewById(R.id.edit_title_after);
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(mTitle, position);
                    mListener.onItemClick(mLocal, position);
                }
            }
        });
        editLocal = (EditText)itemView.findViewById(R.id.edit_local_after);

    }
    String mLocal;
    String mTitle;

    public void setData(String title, String local){
        mTitle = title;
        mLocal = local;
        editTitle.setText(title);
        editLocal.setText(local);
    }
}

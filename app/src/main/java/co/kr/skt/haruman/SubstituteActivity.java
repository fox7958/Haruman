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
public class SubstituteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    boolean mlnitSpinner;
    RecyclerView mRecyclerView_sub;
    SubstituteAdapter mSubstituteAdapter;
    Spinner spinnerTown, spinnerBorough, spinnerType;
    ArrayAdapter<CharSequence> townspin, boroughspin, typespin;
    Button buttonAdd;

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
                Toast.makeText(SubstituteActivity.this, typespin.getItem(position), Toast.LENGTH_SHORT).show();
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
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mSubstituteAdapter.add("대타대타대타대타대타대타");
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

class SubstituteAdapter extends RecyclerView.Adapter<SubstituteViewHolder> {

    List<String> items = new ArrayList<String>();

    SubstituteViewHolder.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(SubstituteViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void add(String item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public SubstituteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.substitutelist_view, null);
        return new SubstituteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubstituteViewHolder holder, int position) {
        holder.setData(items.get(position), items.get(position), items.get(position), items.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
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

    EditText editTitle_sub;
    EditText editLocal_sub;
    EditText editType_sub;
    EditText editPay_sub;

    public SubstituteViewHolder(View itemView) {
        super(itemView);
        editTitle_sub = (EditText) itemView.findViewById(R.id.edit_title_sub);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mTitle, position);
                    mListener.onItemClick(mLocal, position);
                    mListener.onItemClick(mType, position);
                    mListener.onItemClick(mPay, position);
                }
            }
        });
        editLocal_sub = (EditText) itemView.findViewById(R.id.edit_local_sub);
        editType_sub = (EditText) itemView.findViewById(R.id.edit_type_sub);
        editPay_sub = (EditText) itemView.findViewById(R.id.edit_pay_sub);

    }

    String mLocal;
    String mType;
    String mPay;
    String mTitle;

    public void setData(String title, String local, String type, String pay) {
        mTitle = title;
        mLocal = local;
        mType = type;
        mPay = pay;
        editTitle_sub.setText(title);
        editLocal_sub.setText(local);
        editType_sub.setText(type);
        editPay_sub.setText(pay);
    }
}

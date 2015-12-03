package co.kr.skt.haruman;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by T on 2015-12-03.
 */
public class AfterInsertActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_insert);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button BtnInsert = (Button) findViewById(R.id.btn_insert_success);


        BtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    private void submitForm() {
        if (!validateTitle()) {
            return;
        }
        if (!validateLocal()) {
            return;
        }
        if(!validateContent()){
            return;
        }
        LinearLayout sub = (LinearLayout)findViewById(R.id.sub);
//        Snackbar snackbar = Snackbar.make(sub, "등록되었습니다.", Snackbar.LENGTH_LONG);
//        snackbar.show();
        Toast.makeText(AfterInsertActivity.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validateTitle() {
        TextInputLayout inputLayoutTitle = (TextInputLayout) findViewById(R.id.inputLayoutTitle);
        EditText inputTitle = (EditText) findViewById(R.id.edit_title_insert);
        if (inputTitle.getText().toString().trim().isEmpty()) {
            inputLayoutTitle.setError("제목을 입력해주세요.");
            requestFocus(inputTitle);
            return false;
        } else {
            inputLayoutTitle.setErrorEnabled(false);
        }

        return true;
    }



    private boolean validateContent() {
        TextInputLayout inputLayoutContent = (TextInputLayout) findViewById(R.id.inputLayoutContent);
        EditText inputContent = (EditText) findViewById(R.id.edit_content_insert);
        if (inputContent.getText().toString().trim().isEmpty()) {
            inputLayoutContent.setError("한줄후기를 입력해주세요");
            requestFocus(inputContent);
            return false;

        } else {
            inputLayoutContent.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateLocal() {
        TextInputLayout inputLayoutLocal = (TextInputLayout) findViewById(R.id.inputLayoutLocal);
        TextView inputLocal = (TextView) findViewById(R.id.edit_local_insert);
        if (inputLocal.getText().toString().trim().isEmpty()) {   // 이 조건을 스피너에서 아무것도 선택하지 않았을 때로 바꾸면된다.
            inputLayoutLocal.setError("지역을 선택해주세요");
            requestFocus(inputLocal);
            return false;

        } else {
            inputLayoutLocal.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_substitute) {

            Intent intent = new Intent(AfterInsertActivity.this, SubstituteActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_alba) {

            Intent intent = new Intent(AfterInsertActivity.this, AlbaListActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_setting) {

            Intent intent = new Intent(AfterInsertActivity.this, SettingActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_after) {

            Intent intent = new Intent(AfterInsertActivity.this, AfterActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

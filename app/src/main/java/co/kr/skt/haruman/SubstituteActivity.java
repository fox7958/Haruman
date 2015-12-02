package co.kr.skt.haruman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

/**
 * Created by T on 2015-12-02.
 */
public class SubstituteActivity extends AppCompatActivity {

    RecyclerView mRecyclerView_sub;
    SubstituteAdapter mSubstituteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_substitute);

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
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mSubstituteAdapter.add("abcdefghijklmnopqrstuvwxyz");
        }

    }
}

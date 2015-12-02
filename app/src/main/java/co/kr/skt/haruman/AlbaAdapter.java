package co.kr.skt.haruman;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 2015-12-01.
 */
public class AlbaAdapter extends RecyclerView.Adapter<AlbaViewHolder> {

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
        holder.setData(items.get(position), items.get(position), items.get(position), items.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

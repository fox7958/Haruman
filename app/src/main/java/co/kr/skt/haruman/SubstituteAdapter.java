package co.kr.skt.haruman;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 2015-12-02.
 */
public class SubstituteAdapter extends RecyclerView.Adapter<SubstituteViewHolder>{

    List<String> items = new ArrayList<String>();

    SubstituteViewHolder.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(SubstituteViewHolder.OnItemClickListener listener){
        mItemClickListener = listener;
    }
    public void add(String item){
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

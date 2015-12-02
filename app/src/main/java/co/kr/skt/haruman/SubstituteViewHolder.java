package co.kr.skt.haruman;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

/**
 * Created by T on 2015-12-02.
 */
public class SubstituteViewHolder extends RecyclerView.ViewHolder{
    public interface OnItemClickListener{
        public void onItemClick(String name, int position);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    EditText editTitle_sub;
    EditText editLocal_sub;
    EditText editType_sub;
    EditText editPay_sub;

    public SubstituteViewHolder(View itemView) {
        super(itemView);
        editTitle_sub = (EditText)itemView.findViewById(R.id.edit_title_sub);
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(mTitle, position);
                    mListener.onItemClick(mLocal, position);
                    mListener.onItemClick(mType, position);
                    mListener.onItemClick(mPay, position);
                }
            }
        });
        editLocal_sub = (EditText)itemView.findViewById(R.id.edit_local_sub);
        editType_sub = (EditText)itemView.findViewById(R.id.edit_type_sub);
        editPay_sub = (EditText)itemView.findViewById(R.id.edit_pay_sub);

    }
    String mLocal;
    String mType;
    String mPay;
    String mTitle;
    public void setData(String title, String local, String type, String pay){
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

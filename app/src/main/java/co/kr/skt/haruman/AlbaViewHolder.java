package co.kr.skt.haruman;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

/**
 * Created by T on 2015-12-01.
 */
public class AlbaViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener{
        public void onItemClick(String name, int position);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    EditText editTitle;
    EditText editLocal;
    EditText editType;
    EditText editPay;

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
                }
            }
        });
        editLocal = (EditText)itemView.findViewById(R.id.edit_local);
        editType = (EditText)itemView.findViewById(R.id.edit_type);
        editPay = (EditText)itemView.findViewById(R.id.edit_pay);

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
        editTitle.setText(title);
        editLocal.setText(local);
        editType.setText(type);
        editPay.setText(pay);
    }
}

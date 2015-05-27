package edu.jiangnan.dm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.Model.ContactModel;
import edu.jiangnan.dm.R;

import java.util.List;

/**
 * Created by Jelly on 2015/3/25.
 */
public class ContactCardAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<ContactModel> models;

    public ContactCardAdapter(Context context,List<ContactModel> models) {
        this.mContext = context;
        this.models = models;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( models == null ) {
            return null;
        }
        HolderView holderView = new HolderView();
//        if(convertView == null){
            String cardId =  models.get(position).getCardId();
            Log.e("CARDID=====", "======" + cardId);
            if( cardId == null) {
                convertView = inflater.inflate(R.layout.item_linker_list1, null);
            }else if ( cardId.equals(Constants.CARD_ONE) ) {
                convertView = inflater.inflate(R.layout.item_linker_list1, null);
            }else if ( cardId.equals(Constants.CARD_TWO) ) {
                convertView = inflater.inflate(R.layout.item_linker_list2, null);
            }else if ( cardId.equals(Constants.CARD_THREE) ) {
                convertView = inflater.inflate(R.layout.item_linker_list3, null);
            }else if ( cardId.equals(Constants.CARD_FOUR) ) {
                convertView = inflater.inflate(R.layout.item_linker_list4, null);
            }
            holderView.tvName = (TextView)convertView.findViewById(R.id.name);
            holderView.tvJob = (TextView)convertView.findViewById(R.id.job);
            holderView.tvTelephone = (TextView)convertView.findViewById(R.id.telephone);
            holderView.tvEmail = (TextView)convertView.findViewById(R.id.email);
             if (!cardId.equals(Constants.CARD_THREE)) {
                 holderView.tvOrganization = (TextView)convertView.findViewById(R.id.organization);
             }
            holderView.tvAddress = (TextView)convertView.findViewById(R.id.address);
            convertView.setTag(holderView);
//        } else {
//            holderView = (HolderView)convertView.getTag();
//        }
        holderView.tvName.setText(models.get(position).getName());
        holderView.tvJob.setText(models.get(position).getJob());
        holderView.tvTelephone.setText(models.get(position).getTelephone());
        holderView.tvEmail.setText(models.get(position).getEmail());
        if( !cardId.equals(Constants.CARD_THREE) ) {
            holderView.tvOrganization.setText(models.get(position).getOrganization());
        }
        holderView.tvAddress.setText(models.get(position).getAddress());

        return convertView;
    }

    class HolderView {
        TextView tvName;
        TextView tvJob;
        TextView tvTelephone;
        TextView tvEmail;
        TextView tvOrganization;
        TextView tvAddress;
    }

    public void swapModels( List<ContactModel> models ) {
        this.models = models;
    }
}

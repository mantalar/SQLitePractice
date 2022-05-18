package id.go.kominfo.andi.sqlitepractice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import id.go.kominfo.andi.sqlitepractice.R;
import id.go.kominfo.andi.sqlitepractice.model.Friend;

public class FriendAdapter extends BaseAdapter {
    private final Context ctx;
    private final List<Friend> ls;

    public FriendAdapter(Context ctx, List<Friend> ls) {
        this.ctx = ctx;
        this.ls = ls;
    }

    private static final class ViewHolder{
        TextView tvName;
        TextView tvPhone;

        public ViewHolder(View view) {
            this.tvName = view.findViewById(R.id.tv_name);
            this.tvPhone = view.findViewById(R.id.tv_phone);
        }
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public Object getItem(int position) {
        return ls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.item_list,parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        Friend friend = ls.get(position);
        holder.tvName.setText(friend.getName());
        holder.tvPhone.setText(friend.getPhone());

        return convertView;
    }
}

package www.wsxingjun.com.coolweather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import www.wsxingjun.com.coolweather.R;


/**
 * @function:
 */

public class MyAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private List<String> mStringList;

    public MyAdapter(Context context,List<String> mStringList ) {
        mInflater= LayoutInflater.from(context);
        this.mStringList = mStringList;
        Toast.makeText(context,"MyAdapter数据显示："+mStringList.toString(),Toast.LENGTH_SHORT).show();
        Log.d("wsxingjun+适配器",mStringList.toString());
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object getItem(int pos) {
        return mStringList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_listviewitem,viewGroup,false);
            holder = new ViewHolder();

            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_listView);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        String s = mStringList.get(pos);
        holder.mTextView.setText(s);

        return convertView;
    }

    private class ViewHolder{
         TextView mTextView;
    }
}

package com.example.adapterpicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class PersonAdapter extends BaseAdapter
{
    private Context context;
    private List<Person> personList;

    public PersonAdapter(Context context, List<Person> personList)
    {
        this.context = context;
        this.personList = personList;
    }

    @Override
    public int getCount() { return personList.size(); }
    @Override
    public Object getItem(int position) { return personList.get(position); }
    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.base_item, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.personImage);
            holder.name = convertView.findViewById(R.id.nameText);
            holder.birthday = convertView.findViewById(R.id.birthdayText);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Person person = personList.get(position);
        holder.image.setImageResource(person.getPictureResId());
        holder.name.setText(person.getFirstName() + " " + person.getLastName());
        holder.birthday.setText(person.getBirthday());
        return convertView;
    }

    private static class ViewHolder
    {
        ImageView image;
        TextView name, birthday;
    }
}

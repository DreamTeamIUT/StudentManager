package com.example.dd500076.studentmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

import static java.security.AccessController.getContext;

/**
 * Created by dd500076 on 27/02/17.
 */

public class UsersAdapter extends ArrayAdapter<User> {
    private ArrayList<User> users;

    public UsersAdapter(Context context, ArrayList<User> users) {

        super(context, 0, users);

        setUsers(users);
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        User user = getItem(position);


        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);

        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        TextView tvSurname = (TextView) convertView.findViewById(R.id.tvSurname);
        TextView tvYear = (TextView) convertView.findViewById(R.id.tvYear);
        TextView tvStudies = (TextView) convertView.findViewById(R.id.tvStudies);

        tvName.setText(user.name);

        tvSurname.setText(user.surname);

        tvYear.setText(String.valueOf(user.year));

        tvStudies.setText(user.studies);

        convertView.setTag(user);

        return convertView;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    private void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}

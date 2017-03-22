package com.example.dd500076.studentmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dd500076 on 15/03/17.
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> implements FastScroller.SectionIndexer {

    private ArrayList<User> users;

    public UserRecyclerAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(String.valueOf(users.get(position)).charAt(0));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvSurname;
        private TextView tvYear;
        private TextView tvStudies;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvSurname = (TextView) itemView.findViewById(R.id.tvSurname);
            tvYear = (TextView) itemView.findViewById(R.id.tvYear);
            tvStudies = (TextView) itemView.findViewById(R.id.tvStudies);
        }

        public void bind(User user) {
            tvName.setText(user.name);
            tvSurname.setText(user.surname);
            tvYear.setText(String.valueOf(user.year));
            tvStudies.setText(user.studies);
        }
    }
}

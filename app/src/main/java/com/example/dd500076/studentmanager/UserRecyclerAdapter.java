package com.example.dd500076.studentmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by dd500076 on 15/03/17.
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> implements FastScroller.SectionIndexer {

    private ArrayList<User> users;
    private RecyclerView recyclerView;

    public UserRecyclerAdapter(ArrayList<User> users, RecyclerView recyclerView) {
        this.users = users;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        view.setOnClickListener(onClickListener);

        return new ViewHolder(view);
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);

            User user = users.get(itemPosition);

            Intent intent = new Intent(recyclerView.getContext(), StudentActivity.class);
            intent.putExtra("studentId", user.getIdEtu());
            intent.putExtra("studentName", user.getName());
            intent.putExtra("studentSurname", user.getSurname());
            intent.putExtra("studentStudies", user.getStudies());
            intent.putExtra("studentYear", user.getYear());

            recyclerView.getContext().startActivity(intent);
        }
    };

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
            tvName.setText((user.name.length() > 12) ? (user.name.substring(0, 9) + "...") : user.name);
            tvSurname.setText(user.surname);
            tvYear.setText(String.valueOf(user.year));
            tvStudies.setText(user.studies);
        }
    }
}

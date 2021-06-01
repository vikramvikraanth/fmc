package com.kotlintest.app.view.fragment.navigation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kotlintest.app.R;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Activity activity;
    private Context context;

    public NavigationDrawerAdapter(Activity activity, List<NavDrawerItem> data) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public @NotNull MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_navigation_drawer_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        switch (position) {

            case 0:
                holder.nav_img.setImageResource(R.drawable.ic_family);
                holder.divider.setVisibility(View.GONE);
                break;
            case 1:
                holder.nav_img.setImageResource(R.drawable.ic_benifits);
                holder.divider.setVisibility(View.GONE);
                break;
            case 2:
                holder.nav_img.setImageResource(R.drawable.ic_medical);
                holder.divider.setVisibility(View.GONE);
                break;
            case 3:
                holder.nav_img.setImageResource(R.drawable.ic_pre_approval);
                holder.divider.setVisibility(View.GONE);
                break;

            case 4:
                holder.nav_img.setImageResource(R.drawable.ic_reimburse);
                holder.divider.setVisibility(View.GONE);
                break;
            case 5:
                holder.nav_img.setImageResource(R.drawable.ic_e_card);
                holder.divider.setVisibility(View.GONE);
                break;
            case 6:
                holder.nav_img.setImageResource(R.drawable.ic_compliants);
                holder.divider.setVisibility(View.VISIBLE);
                break;
            case 7:
                holder.nav_img.setImageResource(R.drawable.ic_faq);
                holder.divider.setVisibility(View.GONE);
                break;
            case 8:
                holder.nav_img.setImageResource(R.drawable.ic_about);
                holder.divider.setVisibility(View.GONE);
                break;
                case 9:
                holder.nav_img.setImageResource(R.drawable.ic_language);
                    holder.divider.setVisibility(View.GONE);
                break;
                case 10:
                holder.nav_img.setImageResource(R.drawable.ic_health_tip);
                    holder.divider.setVisibility(View.GONE);
                break;
                case 11:
                holder.nav_img.setImageResource(R.drawable.ic_call_white);
                    holder.divider.setVisibility(View.GONE);
                break;
                case 12:
                holder.nav_img.setImageResource(R.drawable.ic_logout);
                    holder.divider.setVisibility(View.GONE);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView nav_img;
        View divider;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.nav_title);
            nav_img = (ImageView) itemView.findViewById(R.id.nav_img);
            divider = (View) itemView.findViewById(R.id.divider);
        }
    }
}
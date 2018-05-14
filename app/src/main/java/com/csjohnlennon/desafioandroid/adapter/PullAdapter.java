package com.csjohnlennon.desafioandroid.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csjohnlennon.desafioandroid.R;
import com.csjohnlennon.desafioandroid.network.model.Pull;
import com.csjohnlennon.desafioandroid.utils.Data;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PullAdapter extends RecyclerView.Adapter<PullAdapter.Holder> {

    private Context context;
    private List<Pull> pullList;
    private PullAdapterClickListener pullAdapterClickListener;

    public PullAdapter(Context context, List<Pull> pullList, PullAdapterClickListener pullAdapterClickListener) {
        this.context = context;
        this.pullList = pullList;
        this.pullAdapterClickListener = pullAdapterClickListener;
    }

    @Override
    public void onBindViewHolder(PullAdapter.Holder holder, int position) {
        final Pull pull = pullList.get(position);

        holder.tvLogin.setText(pull.owner.login);
        holder.tvName.setText(pull.title);
        holder.tvDescription.setText(pull.body);
        holder.tvCreatedAt.setText(Data.formataDataBR(pull.created_at));

        Glide.with(context).load((!pull.owner.avatarUrl.isEmpty()) ? pull.owner.avatarUrl : R.drawable.ic_user).into(holder.ivAvatar_url);

        holder.cvPull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pull.html_url.equals(""))
                    pullAdapterClickListener.onClick(pull.html_url);
            }
        });

    }

    public void add(List<Pull> pullList) {
        for (int i = 0; i < pullList.size(); i++) {
            this.pullList.add(pullList.get(i));
        }
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.pullList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pullList.size();
    }

    @Override
    public PullAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pull_layout, parent, false);

        return new PullAdapter.Holder(itemView);
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvLogin)
        TextView tvLogin;
        @BindView(R.id.cvPull)
        CardView cvPull;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvCreatedAt)
        TextView tvCreatedAt;
        @BindView(R.id.ivAvatar_url)
        ImageView ivAvatar_url;

        public Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface PullAdapterClickListener {

        void onClick(String url);

    }

}


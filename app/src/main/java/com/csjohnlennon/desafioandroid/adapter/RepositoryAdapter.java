package com.csjohnlennon.desafioandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csjohnlennon.desafioandroid.R;
import com.csjohnlennon.desafioandroid.network.model.Repository;
import com.csjohnlennon.desafioandroid.ui.activity.PullActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryHolder> {

    private Context context;
    private List<Repository> repositoryList;

    public RepositoryAdapter(Context context, List<Repository> repositoryList) {
        this.context = context;
        this.repositoryList  = repositoryList;
    }

    @Override
    public void onBindViewHolder(RepositoryHolder holder, int position) {

        try {

            final Repository repository = repositoryList.get(position);

            holder.cvRepository.setTag(repository);
            holder.tvName.setText(repository.name);
            holder.tvDescription.setText(repository.description);
            holder.tvStargazersCount.setText("" + repository.stargazers_count);
            holder.tvForksCount.setText("" + repository.forks);
            holder.tvLogin.setText("" + repository.owner.login);
            Glide.with(context)
                    .load((!repository.owner.avatarUrl.isEmpty()) ? repository.owner.avatarUrl : R.drawable.ic_user)
                    .into(holder.ivAvatarUrl);

            holder.cvRepository.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PullActivity.class);
                    intent.putExtra(PullActivity.REPOSITORY_KEY, repository);
                    intent.putExtra(PullActivity.OWNER_KEY, repository.owner);
                    context.startActivity(intent);
                }
            });

        } catch(NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void add(List<Repository> rList) {

        for (int i = 0; i < rList.size(); i++) {
            this.repositoryList.add(rList.get(i));
        }
        this.notifyDataSetChanged();

    }

    public void clear() {
        this.repositoryList.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RepositoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_layout, parent, false);

        return new RepositoryHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    public class RepositoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvRepository)
        CardView cvRepository;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvLogin)
        TextView tvLogin;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvStargazersCount)
        TextView tvStargazersCount;
        @BindView(R.id.tvForksCount)
        TextView tvForksCount;
        @BindView(R.id.ivAvatarUrl)
        ImageView ivAvatarUrl;

        public RepositoryHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
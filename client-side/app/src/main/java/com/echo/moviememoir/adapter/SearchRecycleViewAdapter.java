package com.echo.moviememoir.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.activity.MovieMemoirActivity;
import com.echo.moviememoir.activity.MovieSearchActivity;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.utils.DateString;
import com.echo.moviememoir.utils.LocalStorage;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class SearchRecycleViewAdapter extends RecyclerView.Adapter<SearchRecycleViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View memoirView;
        public SuperTextView memoirText;

        public ViewHolder(View view) {
            super(view);

            memoirView = view;
            memoirText = view.findViewById(R.id.search_movie_list);
        }
    }

    private List<Memoir> memoirs;
    private Context context;

    @Override
    public int getItemCount() {
        return memoirs.size();
    }

    public SearchRecycleViewAdapter(List<Memoir> units, Context context) {
        this.memoirs = units;
        this.context = context;
    }

    public void addUnits(List<Memoir> units) {
        this.memoirs = units;
        notifyDataSetChanged();
    }

    @Override
    public SearchRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View unitsView = inflater.inflate(R.layout.search_movie_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(unitsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecycleViewAdapter.ViewHolder viewHolder, int position) {
        final Memoir memoir = memoirs.get(position);
        
        if (memoir.getMovieReleaseDate() != null) {
            viewHolder.memoirText.setCenterBottomString(DateString.date2String(memoir.getMovieReleaseDate()));
        }

        viewHolder.memoirText.setCenterTopString(memoir.getMovieName());

        viewHolder.memoirText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalStorage.setMemoir(memoir);
                Intent intent = new Intent(context, MovieMemoirActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

}

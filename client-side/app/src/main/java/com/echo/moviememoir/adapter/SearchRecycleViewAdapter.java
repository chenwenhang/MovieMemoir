package com.echo.moviememoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.entity.Memoir;
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

    @Override
    public int getItemCount() {
        return memoirs.size();
    }

    public SearchRecycleViewAdapter(List<Memoir> units) {
        this.memoirs = units;
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
        Memoir memoir = memoirs.get(position);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(memoir.getMovieReleaseDate());

        viewHolder.memoirText.setCenterTopString(memoir.getMovieName());
        viewHolder.memoirText.setCenterBottomString(dateString);
    }
}

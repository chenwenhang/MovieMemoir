package com.echo.moviememoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.utils.DateStringUtils;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View memoirView;
        public SuperTextView memoirText;

        public ViewHolder(View view) {
            super(view);

            memoirView = view;
            memoirText = view.findViewById(R.id.home_movie_list);
        }
    }

    private List<Memoir> memoirs;

    @Override
    public int getItemCount() {
        return memoirs.size();
    }

    public HomeRecyclerViewAdapter(List<Memoir> units) {
        this.memoirs = units;
    }

    public void addUnits(List<Memoir> units) {
        this.memoirs = units;
        notifyDataSetChanged();
    }

    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View unitsView = inflater.inflate(R.layout.list_home_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(unitsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Memoir memoir = memoirs.get(position);

        viewHolder.memoirText.setCenterTopString(memoir.getMovieName());
        viewHolder.memoirText.setCenterString(memoir.getScore());
        viewHolder.memoirText.setCenterBottomString(DateStringUtils.date2String(memoir.getMovieReleaseDate()));
    }
}

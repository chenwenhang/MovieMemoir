package com.echo.moviememoir.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.activity.MovieMemoirActivity;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.utils.DateStringUtils;
import com.echo.moviememoir.utils.LocalStorage;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

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
        View unitsView = inflater.inflate(R.layout.list_search_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(unitsView);
        return viewHolder;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(@NonNull final SearchRecycleViewAdapter.ViewHolder viewHolder, int position) {
        final Memoir memoir = memoirs.get(position);
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
        if (memoir.getMovieReleaseDate() != null) {
            viewHolder.memoirText.setCenterBottomString(DateStringUtils.date2String(memoir.getMovieReleaseDate()));
        }

        // get image
//        if (memoir.getImageUrl() != null) {
//            viewHolder.memoirText.setRightIcon(ImgHelper.getDrawableFormBitmap(context, ImageAPI.getImage(memoir.getImageUrl())));
//            new AsyncTask<String, Void, Bitmap>() {
//                @Override
//                protected Bitmap doInBackground(String... params) {
//                    Bitmap bitmap = null;
//                    String imageUrl = memoir.getImageUrl();
//                    bitmap = ImageAPI.getImage(imageUrl);
//                    return bitmap;
//                }
//
//                @Override
//                protected void onPostExecute(Bitmap bitmap) {
//                    viewHolder.memoirText.setRightIcon(ImgHelper.getDrawableFormBitmap(context, bitmap));
//                }
//            }.execute();
//        }


    }

}

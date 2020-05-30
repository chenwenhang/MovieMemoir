package com.echo.moviememoir.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.activity.MovieDetailActivity;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.utils.DateStringUtils;
import com.echo.moviememoir.utils.LocalStorage;
import com.echo.moviememoir.utils.RatingStarUtils;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class MemoirRecycleViewAdapter extends RecyclerView.Adapter<MemoirRecycleViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View memoirView;
        public SuperTextView memoirName;
        public SuperTextView memoirReleaseDate;
        public SuperTextView memoirWatchDate;
        public SuperTextView memoirCinema;
        public SuperTextView memoirComment;
        private LinearLayout memoirBox;
        public RatingBar memoirStar;

        public ViewHolder(View view) {
            super(view);

            memoirView = view;
            memoirName = view.findViewById(R.id.moviememoir_movie_name);
            memoirReleaseDate = view.findViewById(R.id.moviememoir_movie_release_date);
            memoirWatchDate = view.findViewById(R.id.moviememoir_movie_watch_date);
            memoirCinema = view.findViewById(R.id.moviememoir_movie_cinema);
            memoirComment = view.findViewById(R.id.moviememoir_movie_comment);
            memoirStar = view.findViewById(R.id.moviememoir_movie_star);
            memoirBox = view.findViewById(R.id.moviememoir_box);
        }
    }

    private List<Memoir> memoirs;
    private Context context;

    @Override
    public int getItemCount() {
        return memoirs.size();
    }

    public MemoirRecycleViewAdapter(List<Memoir> units, Context context) {
        this.memoirs = units;
        this.context = context;
    }

    public void addUnits(List<Memoir> units) {
        this.memoirs = units;
        notifyDataSetChanged();
    }

    @Override
    public MemoirRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View unitsView = inflater.inflate(R.layout.list_movie_memoir, parent, false);
        ViewHolder viewHolder = new ViewHolder(unitsView);
        return viewHolder;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(@NonNull final MemoirRecycleViewAdapter.ViewHolder viewHolder, int position) {
        final Memoir memoir = memoirs.get(position);
        viewHolder.memoirName.setCenterTopString(memoir.getMovieName());
        if (memoir.getMovieReleaseDate() != null)
            viewHolder.memoirReleaseDate.setCenterTopString(DateStringUtils.date2String(memoir.getMovieReleaseDate()));
        if (memoir.getWatchDate() != null)
            viewHolder.memoirWatchDate.setCenterTopString(DateStringUtils.date2String(memoir.getWatchDate()));
        viewHolder.memoirCinema.setCenterTopString(memoir.getCinemaId().getCinemaName());
        viewHolder.memoirComment.setCenterTopString(memoir.getComment());
        viewHolder.memoirStar.setRating(RatingStarUtils.rating2Star(memoir.getScore()));

        viewHolder.memoirName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalStorage.setMemoir(memoir);
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        viewHolder.memoirBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalStorage.setMemoir(memoir);
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

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

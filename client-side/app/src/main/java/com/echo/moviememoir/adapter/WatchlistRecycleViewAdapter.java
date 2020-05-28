package com.echo.moviememoir.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.activity.MovieMemoirActivity;
import com.echo.moviememoir.activity.WatchlistActivity;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.utils.DateString;
import com.echo.moviememoir.utils.LocalStorage;
import com.echo.moviememoir.viewmodel.MemoirViewModel;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

import static com.xuexiang.xui.XUI.getContext;

public class WatchlistRecycleViewAdapter extends RecyclerView.Adapter<WatchlistRecycleViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View memoirView;
        public SuperTextView memoirText;
        private Button viewBtn;
        private Button deleteBtn;

        public ViewHolder(View view) {
            super(view);

            memoirView = view;
            memoirText = view.findViewById(R.id.watchlist_movie_list);
            viewBtn = view.findViewById(R.id.watchlist_view);
            deleteBtn = view.findViewById(R.id.watchlist_delete);
        }
    }

    private List<Memoir> memoirs;
    private Context context;
    private MemoirViewModel memoirViewModel;
    private Activity parentActivity;

    @Override
    public int getItemCount() {
        return memoirs.size();
    }

    public WatchlistRecycleViewAdapter(List<Memoir> units, Context context, MemoirViewModel memoirViewModel, Activity parentActivity) {
        this.memoirs = units;
        this.context = context;
        this.memoirViewModel = memoirViewModel;
        this.parentActivity = parentActivity;
    }

    public void addUnits(List<Memoir> units) {
        this.memoirs = units;
        notifyDataSetChanged();
    }

    @Override
    public WatchlistRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View unitsView = inflater.inflate(R.layout.watchlist_movie_list, parent, false);
        WatchlistRecycleViewAdapter.ViewHolder viewHolder = new WatchlistRecycleViewAdapter.ViewHolder(unitsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistRecycleViewAdapter.ViewHolder viewHolder, int position) {
        final Memoir memoir = memoirs.get(position);

        viewHolder.memoirText.setCenterTopString(memoir.getMovieName());
        viewHolder.memoirText.setCenterString(DateString.date2String(memoir.getMovieReleaseDate()));
        viewHolder.memoirText.setCenterBottomString(DateString.dateTime2String(memoir.getAddDateTime()));

        viewHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalStorage.setMemoir(memoir);
                Intent intent = new Intent(context, MovieMemoirActivity.class);
                intent.putExtra("disableAdd", "disable");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoader.getInstance().showConfirmDialog(
                        parentActivity, "Confirm to delete?", "confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                memoirViewModel.delete(memoir);
                                dialog.dismiss();
                            }
                        }, "cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
            }
        });
    }
}

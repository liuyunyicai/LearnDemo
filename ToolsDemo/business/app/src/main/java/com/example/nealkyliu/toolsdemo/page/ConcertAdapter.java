package com.example.nealkyliu.toolsdemo.page;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/13
 **/
//public class ConcertAdapter extends PagedListAdapter<Concert, ConcertViewHolder> {
//    protected ConcertAdapter() {
//        super(DIFF_CALLBACK);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ConcertViewHolder holder,
//                                 int position) {
//        Concert concert = getItem(position);
//        if (concert != null) {
//            holder.bindTo(concert);
//        } else {
//            // Null defines a placeholder item - PagedListAdapter automatically
//            // invalidates this row when the actual object is loaded from the
//            // database.
//            holder.clear();
//        }
//    }
//
//    private static DiffUtil.ItemCallback<Concert> DIFF_CALLBACK =
//            new DiffUtil.ItemCallback<Concert>() {
//                // Concert details may have changed if reloaded from the database,
//                // but ID is fixed.
//                @Override
//                public boolean areItemsTheSame(Concert oldConcert, Concert newConcert) {
//                    return oldConcert.getId() == newConcert.getId();
//                }
//
//                @Override
//                public boolean areContentsTheSame(Concert oldConcert,
//                                                  Concert newConcert) {
//                    return oldConcert.equals(newConcert);
//                }
//            };
//}

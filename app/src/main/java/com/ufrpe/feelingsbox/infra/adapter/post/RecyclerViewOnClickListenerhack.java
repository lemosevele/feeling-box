package com.ufrpe.feelingsbox.infra.adapter.post;

import android.view.View;

/**
 * Created by Faig-PC on 31/07/2017.
 */

public interface RecyclerViewOnClickListenerhack {
    void onClickListener(View view, int position);
    void onLongPressClickListener(View view, int position);
}

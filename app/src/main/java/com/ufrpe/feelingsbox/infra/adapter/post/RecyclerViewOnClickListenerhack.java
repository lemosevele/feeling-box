package com.ufrpe.feelingsbox.infra.adapter.post;

import android.view.View;

public interface RecyclerViewOnClickListenerhack {
    void onClickListener(View view, int position);
    void onLongPressClickListener(View view, int position);
}

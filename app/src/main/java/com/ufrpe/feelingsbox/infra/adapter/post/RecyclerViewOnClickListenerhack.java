package com.ufrpe.feelingsbox.infra.adapter.post;

import android.view.View;

/**
 * Interface para a implementação dos cliques dos itens mostrados no {@link android.support.v7.widget.RecyclerView}.
 */

public interface RecyclerViewOnClickListenerhack {
    void onClickListener(View view, int position);
    void onLongPressClickListener(View view, int position);
}

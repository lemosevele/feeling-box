package com.ufrpe.feelingsbox.infra.adapter.post;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.gui.ActHome;

import java.util.List;

public class PostFragment extends Fragment implements RecyclerViewOnClickListenerhack{
    private RecyclerView mRecyclerView;
    private List<Post> mList;

    //Setando o RecyclerView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                PostRecyclerAdapter adapter = (PostRecyclerAdapter) mRecyclerView.getAdapter();

                if(mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1){
                        List<Post> listaAux = ActHome.gerarPosts();

                    for (int i = 0; i < listaAux.size(); i++){
                        adapter.addListItem( listaAux.get(i), mList.size() );

                    }

                }
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //llm.setReverseLayout(true);
        mRecyclerView.setLayoutManager(llm);

        mList = ActHome.gerarPosts();
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(getActivity(), mList);
        //adapter.setRecyclerViewOnClickListenerhack(this);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    //Click Normal
    @Override
    public void onClickListener(View view, int position) {
        GuiUtil.myToastShort(getActivity(), "onClickListener " + "Posição " + position);

    }
    //Click longo
    @Override
    public void onLongPressClickListener(View view, int position) {
        GuiUtil.myToastShort(getActivity(), "onLongPressClickListener " + "Posição " + position);

        PostRecyclerAdapter adapter = (PostRecyclerAdapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);
    }

    //Passa o objeto pressionado atraves da posição
    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack;

        //Construtor
        public RecyclerViewTouchListener(Context mContext, final RecyclerView recyclerView, final RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack) {
            this.mContext = mContext;
            this.mRecyclerViewOnClickListenerhack = mRecyclerViewOnClickListenerhack;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerhack != null){
                        mRecyclerViewOnClickListenerhack.onLongPressClickListener(cv, recyclerView.getChildPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerhack != null){
                        mRecyclerViewOnClickListenerhack.onClickListener(cv, recyclerView.getChildPosition(cv));
                    }

                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

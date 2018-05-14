package com.corkay.knote.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corkay.knote.R;
import com.corkay.knote.activity.MainActivity;
import com.corkay.knote.adapter.NoteAdapter;
import com.corkay.knote.bean.Note;
import com.corkay.knote.tool.PopOptionUtil;
import com.corkay.knote.tool.RecyclerItemClickListener;
import com.corkay.knote.toolsInterface.PopClickEvent;

import org.litepal.crud.DataSupport;

import java.util.List;

public class NoteFragment extends Fragment {

    private static final String TAG = "NoteFragment";
    public final static int NAME = 0;
    private RecyclerView recyclerView;
    private List<Note>noteList;
    private NoteAdapter mAdapter;
    PopOptionUtil mPop;

    public NoteFragment(){
        //一个空的公共构造函数
    }
    @Override
    public void onResume(){
        super.onResume();
        noteList.clear();//去除之前的数据
        List<Note>newList = DataSupport.order("date desc").find(Note.class);
        noteList.addAll(newList);//此处是将数据复制过来，而不是直接使用，否则无法更新数据
        mAdapter.notifyDataSetChanged();
        Log.d(TAG,"onResume: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        Log.d(TAG,"onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_note,container,false);

        FloatingActionButton add = (FloatingActionButton)view.findViewById(R.id.add_note);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d(TAG,"onClick: intent");
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("ActivityName",NAME);
                startActivity(intent);
            }
        });
        mPop = new PopOptionUtil(getContext());

        noteList = DataSupport.order("date desc").find(Note.class);
        mAdapter = new NoteAdapter(noteList);

        recyclerView = (RecyclerView)view.findViewById(R.id.note_recyclerView);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),recyclerView,
                new RecyclerItemClickListener.OnItemClickListener(){
                @Override
                    public void onItemClick(View view,int position){
                }
                @Override
                    public void onItemLongClick(View view,final int position){
                    Log.d(TAG,"onItemLongClick: ");
                    mPop.setOnPopClickEvent(new PopClickEvent() {

                        @Override
                        public void onNextClick() {
                            Log.d(TAG,"onNextClick:delete");

                            int id = noteList.get(position).getId();
                            Log.d(TAG,"onNextClick:position:"+position+"id:"+id);
                            deleteData(id);

                            noteList.remove(position);

                            mAdapter.notifyItemRemoved(position);
                            mAdapter.notifyItemRangeChanged(0,noteList.size());
                            mPop.dismiss();
                        }
                    });
                    mPop.show(view);
                }
                }));

                return view;
    }
    public void deleteData(int id){
        DataSupport.deleteAll(Note.class,"id = ?",String.valueOf(id));
        Log.d(TAG,"deleteData:"+id);
    }
}

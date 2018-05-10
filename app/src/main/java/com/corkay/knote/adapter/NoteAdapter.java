package com.corkay.knote.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corkay.knote.R;
import com.corkay.knote.activity.MainActivity;
import com.corkay.knote.bean.Note;
import com.hanks.library.AnimateCheckBox;

import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public final static int NAME = 1;
    private List<Note>list;
    private Context mContext;
    private int notsId;

    public NoteAdapter(List<Note>list){
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        AnimateCheckBox animateCheckBox;
        TextView noteText;
        TextView date;

        public ViewHolder(View itemView){
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.note_cardview);
            animateCheckBox = (AnimateCheckBox)itemView.findViewById(R.id.note_checkbox);
            noteText = (TextView)itemView.findViewById(R.id.note_text);
            date = (TextView)itemView.findViewById(R.id.note_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.note_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        //点击后跳转，可更改数据
        viewHolder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int position = viewHolder.getAdapterPosition();
                Note note = list.get(position);
                Intent intent = new Intent(mContext,MainActivity.class);
                intent.putExtra("ActivityName",NAME);
                intent.putExtra("Content",note);
                mContext.startActivity(intent);
            }
        });

        //设置checkBox点击后的数据存储
        viewHolder.animateCheckBox.setOnCheckedChangeListener(new AnimateCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                int position = viewHolder.getAdapterPosition();
                Note note = list.get(position);
                note.setFinish(viewHolder.animateCheckBox.isChecked());
                note.update(note.getId());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Note note = list.get(position);
        holder.date.setText(new SimpleDateFormat("yyyy/MM/dd").format(note.getDate()));
        holder.noteText.setText(note.getContent());
        holder.animateCheckBox.setChecked(note.getFinish());
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
}

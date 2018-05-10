package com.corkay.knote.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

public class Note extends DataSupport implements Serializable {

    private int id;
    private Date date;
    private String content;
    private Boolean finish;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }

    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }

    public boolean getFinish(){
        return finish;
    }
    public void setFinish(boolean finish){
        this.finish = finish;
    }
}

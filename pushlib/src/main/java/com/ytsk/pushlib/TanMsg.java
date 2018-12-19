package com.ytsk.pushlib;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TanMsg implements Parcelable {
    //消息类型
    public static final int PUSH_TYPE_NOTIFICATION=0;//通知类型
    public static final int PUSH_TYPE_PASSTHROUGHT=1;//透传消息

    @IntDef({PUSH_TYPE_NOTIFICATION,PUSH_TYPE_PASSTHROUGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MsgType{}
    @MsgType
    public int msgType=0;//

    public String title;
    public String content;
    public String custom;


    public static TanMsg newMsg(){
        return new TanMsg();
    }
    public TanMsg type(@MsgType int msgType){
        this.msgType=msgType;
        return this;
    }
    public TanMsg title(String title){
        this.title=title;
        return this;
    }
    public TanMsg content(String content){
        this.content=content;
        return this;
    }
    public TanMsg custom(String custom){
        this.custom=custom;
        return this;
    }


    @Override
    public String toString() {
        return "TanMsg{" +
                "msgType=" + msgType +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", custom='" + custom + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.msgType);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.custom);
    }

    private TanMsg() {
    }

    protected TanMsg(Parcel in) {
        this.msgType = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.custom = in.readString();
    }

    public static final Creator<TanMsg> CREATOR = new Creator<TanMsg>() {
        @Override
        public TanMsg createFromParcel(Parcel source) {
            return new TanMsg(source);
        }

        @Override
        public TanMsg[] newArray(int size) {
            return new TanMsg[size];
        }
    };
}

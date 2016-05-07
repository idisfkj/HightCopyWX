package com.idisfkj.hightcopywx.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.ui.widget.RegisterActivity;
import com.idisfkj.hightcopywx.util.CursorUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public class ChatAdapter extends RecyclerViewCursorBaseAdapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private View view;
    private static final int RECEIVE_MESSAGE = 0;
    private static final int SEND_MESSAGE = 1;
    private static final int SYSTEM_MESSAGE = 2;
    private Cursor mCursor;
    private Bitmap sendBitmap;

    public ChatAdapter(Context context) {
        super(context, null);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        File path = new File(RegisterActivity.SAVE_PATH + RegisterActivity.PICTURE_NAME);
        if (path.exists()) {
            sendBitmap = BitmapFactory.decodeFile(RegisterActivity.SAVE_PATH + RegisterActivity.PICTURE_NAME);
        }
    }

    public void setCursor(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RECEIVE_MESSAGE) {
            view = mLayoutInflater.inflate(R.layout.chat_receive, parent, false);
            return new ChatReceiveViewHolder(view);
        } else if (viewType == SEND_MESSAGE) {
            view = mLayoutInflater.inflate(R.layout.chat_send, parent, false);
            return new ChatSendViewHolder(view);
        } else {
            view = mLayoutInflater.inflate(R.layout.chat_system, parent, false);
            return new ChatSystemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        if (holder instanceof ChatReceiveViewHolder) {
            ((ChatReceiveViewHolder) holder).chatReceiveTime.setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.TIME));
//            ((ChatReceiveViewHolder) holder).chatReceivePicture.setImageBitmap();
            ((ChatReceiveViewHolder) holder).chatReceiveContent.setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.MESSAGE));
        } else if (holder instanceof ChatSendViewHolder) {
            ((ChatSendViewHolder) holder).chatSendTime.setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.TIME));
            if (sendBitmap != null)
                ((ChatSendViewHolder) holder).chatSendPicture.setImageBitmap(sendBitmap);
            ((ChatSendViewHolder) holder).chatSendContent.setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.MESSAGE));
        } else {
            ((ChatSystemViewHolder) holder).chatSystemTime.setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.TIME));
            ((ChatSystemViewHolder) holder).chatSystemContent.setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.MESSAGE));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mCursor.moveToPosition(position)) {
            if (CursorUtils.formatInt(mCursor, ChatMessageDataHelper.ChatMessageDataInfo.FLAG) == 0) {
                return RECEIVE_MESSAGE;
            } else if (CursorUtils.formatInt(mCursor, ChatMessageDataHelper.ChatMessageDataInfo.FLAG) == 1) {
                return SEND_MESSAGE;
            } else {
                return SYSTEM_MESSAGE;
            }
        }
        return 0;
    }

    public static class ChatSendViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.chat_send_time)
        TextView chatSendTime;
        @InjectView(R.id.chat_send_picture)
        ImageView chatSendPicture;
        @InjectView(R.id.chat_send_content)
        TextView chatSendContent;

        ChatSendViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public static class ChatReceiveViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.chat_receive_time)
        TextView chatReceiveTime;
        @InjectView(R.id.chat_receive_picture)
        ImageView chatReceivePicture;
        @InjectView(R.id.chat_receive_content)
        TextView chatReceiveContent;

        ChatReceiveViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public static class ChatSystemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.chat_system_time)
        TextView chatSystemTime;
        @InjectView(R.id.chat_system_content)
        TextView chatSystemContent;

        ChatSystemViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

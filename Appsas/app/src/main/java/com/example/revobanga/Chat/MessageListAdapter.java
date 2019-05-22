//package com.example.revobanga.Chat;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import android.widget.TextView;
//
//import com.example.revobanga.R;
//
//import java.util.List;
//
//public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//    private static final int SENT = 0;//ct
//    private static final int RECEIVED = 1;//et
//    private List<Message> mList;
//
//    public MessageListAdapter(List<Message> list) {
//        this.mList = list;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;
//        switch (viewType) {
//            case SENT:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);
//                return new SentMessageHolder(view);
//            case RECEIVED:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.their_message, parent, false);
//                return new ReceivedMessageHolder(view);
//        }
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Message object = mList.get(position);
//        if (object != null) {
//            switch (object.getmType()) {
//                case SENT:
//                    ((SentMessageHolder) holder).messageText.setText(object.getMessage());
//                    break;
//                case RECEIVED:
//                    ((ReceivedMessageHolder) holder).messageText.setText(object.getMessage());
//                    ((ReceivedMessageHolder) holder).nameText.setText(object.getSender());
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mList == null)
//            return 0;
//        return mList.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (mList != null) {
//            Message object = mList.get(position);
//            if (object != null) {
//                return object.getmType();
//            }
//        }
//        return 0;
//    }
//
//    public static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
//        TextView messageText,timeText,nameText;
//
//        public ReceivedMessageHolder(View itemView) {
//            super(itemView);
//            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
//            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
//            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
//        }
//    }
//
//    public static class SentMessageHolder extends RecyclerView.ViewHolder {
//        TextView messageText,timeText;
//
//
//        public SentMessageHolder(View itemView) {
//            super(itemView);
//            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
//            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
//        }
//    }
//}
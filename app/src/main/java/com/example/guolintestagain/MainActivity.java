package com.example.guolintestagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button send;
    EditText editText;
    RecyclerView recyclerView;
    MsgAdapter msgAdapter;
    List<Msg> msgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsgs();
        send = (Button) findViewById(R.id.send);
        editText = (EditText) findViewById(R.id.msg);
        recyclerView = (RecyclerView) findViewById(R.id.content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        msgAdapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(msgAdapter);
        send.setOnClickListener(new sendButtonListener());
    }

    private class sendButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String message = editText.getText().toString();
            if (!"".equals(message)) {
                Msg msg = new Msg(message, Msg.TYPE_SEND);
                msgList.add(msg);
                msgAdapter.notifyItemInserted(msgList.size() - 1);
                recyclerView.scrollToPosition(msgList.size() - 1);
                editText.setText("");
            }
        }
    }


    private void initMsgs() {
        Msg msg = new Msg("hello guy.", Msg.TYPE_RECEIVE);
        msgList.add(msg);
        Msg msg1 = new Msg("hello.who is that?", Msg.TYPE_SEND);
        msgList.add(msg1);
        Msg msg2 = new Msg("This is Maqian.Nice to meet you.", Msg.TYPE_RECEIVE);
        msgList.add(msg2);
    }

}

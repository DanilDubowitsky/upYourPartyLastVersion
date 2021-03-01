package com.studenttomsk.upYourParty.Views.Fragments.Messages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.studenttomsk.upYourParty.Adapters.RecycleChatsAdapter;
import com.studenttomsk.upYourParty.Classes.AllMessagesClass;
import com.studenttomsk.upYourParty.Classes.ChatsItemClass;
import com.studenttomsk.upYourParty.Classes.DidClass;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.SingletonDid;
import com.studenttomsk.upYourParty.Classes.SingletonEmail;
import com.studenttomsk.upYourParty.Classes.SingletonMessages;
import com.studenttomsk.upYourParty.Network.ModelMessages;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Fragments.Chat.FragmentChat;

import java.util.ArrayList;
import java.util.List;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class FragmentMessages extends Fragment implements MessagesMethods.MessagesView {
    View view;

    private RecyclerView recyclerView;
    private RecycleChatsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Fragment fragment;
    StompClient mStompClient;
    MessagesPresenter messagesPresenter;
    ArrayList<ChatsItemClass> list;
    OnFragmentInteractionListener fragmentListener;

    String myEmail;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_messages,container,false);


        messagesPresenter = new MessagesPresenter(this,new ModelMessages());

        messagesPresenter.postMyEmail();

        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://178.170.220.39:8080/gs-guide-websocket/websocket");
        mStompClient.connect();

        setListenerMessages();

        setDataToActivity();

        return view;
    }

    public void toChat(){
        fragment = new FragmentChat();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).addToBackStack(null);
        ft.replace(R.id.screen_area, fragment);
        ft.commit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentListener = (OnFragmentInteractionListener) context;
        }
        catch (Exception e){

        }

    }
    void setDataToActivity(){
        fragmentListener.onFragmentInteraction("СООБЩЕНИЯ");
        fragmentListener.setBackButton(false);
    }

    public void initRecycle(final ArrayList<ChatsItemClass> list){
        recyclerView = view.findViewById(R.id.recWithChats);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecycleChatsAdapter(getContext(),list);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecycleChatsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                fragmentListener.onFragmentInteraction(list.get(position).getFio());
                SingletonEmail.getInstance().setEmail(list.get(position).getEmail_to());
                SingletonDid.getInstance().setDid(list.get(position).getDid());
                if(list.get(position).getLastEmail().equals(myEmail)){

                    toChat();
                }
                else{
                    messagesPresenter.redMes(new DidClass(list.get(position).getDid()));
                }

            }
        });
    }

    void setListenerMessages(){
        mStompClient.topic("/topic/"+myEmail).subscribe(topicMessage -> {
            AllMessagesClass message = new Gson().fromJson(topicMessage.getPayload(),AllMessagesClass.class);
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messagesPresenter.postMyMessages();
                    }
                });
            }
            catch (Exception e){

            }

        });
    }

    @Override
    public void onSuccessLoadMessages(List<AllMessagesClass> allMessagesClasses) {
        SingletonMessages.getInstance().SetData(allMessagesClasses);

    }

    @Override
    public void onSuccessLoad(List<ChatsItemClass> chatsItemClass) {
        for(int i=0;i<chatsItemClass.size();i++){
            chatsItemClass.get(i).setMyEmail(myEmail);
        }
        list = new ArrayList<>(chatsItemClass);
        initRecycle(list);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onSuccessEmail(String email) {
        myEmail = email;
        messagesPresenter.postMyMessages();
        setListenerMessages();
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onSuccessReadMessage() {
        toChat();
    }
}

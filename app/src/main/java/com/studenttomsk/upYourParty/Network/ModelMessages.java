package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.AllMessagesClass;
import com.studenttomsk.upYourParty.Classes.ChatsItemClass;
import com.studenttomsk.upYourParty.Classes.DidClass;
import com.studenttomsk.upYourParty.Views.Fragments.Messages.MessagesMethods;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelMessages implements MessagesMethods.MessagesModel {
    @Override
    public void getAllMesages(OnFinishedListener onFinishedListener, DidClass did) {
       NetworkService.getInstance()
               .getJSONApi()
               .getAllMessages(did, Singleton.getInstance().GetToken())
               .enqueue(new Callback<List<AllMessagesClass>>() {
                   @Override
                   public void onResponse(Call<List<AllMessagesClass>> call, Response<List<AllMessagesClass>> response) {
                       if(response.isSuccessful()){
                           onFinishedListener.onFinishedLoadMessages(response.body());
                       }
                       else{
                           onFinishedListener.onFailure(response.message());
                       }
                   }

                   @Override
                   public void onFailure(Call<List<AllMessagesClass>> call, Throwable t) {
                       onFinishedListener.onFailure(t.getMessage());
                   }
               });
    }

    @Override
    public void getMyMessages(OnFinishedListener onFinishedListener, String token) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getMyChats(Singleton.getInstance().GetToken())
                .enqueue(new Callback<List<ChatsItemClass>>() {
                    @Override
                    public void onResponse(Call<List<ChatsItemClass>> call, Response<List<ChatsItemClass>> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onFinished(response.body());
                        }
                        else{
                            onFinishedListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ChatsItemClass>> call, Throwable t) {
                        onFinishedListener.onFailure(t.getMessage());
                    }
                });
    }

    @Override
    public void getMyEmail(OnFinishedListener onFinishedListener, String token) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getEmail(token)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onFinishedEmail(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    @Override
    public void readMessage(OnFinishedListener onFinishedListener, DidClass did) {
        NetworkService.getInstance()
                .getJSONApi()
                .readMessage(did,Singleton.getInstance().GetToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onFinishedReading();
                        }
                        else{

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }


}

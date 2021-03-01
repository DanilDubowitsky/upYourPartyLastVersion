package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.ReviewClass;
import com.studenttomsk.upYourParty.Network.NetworkService;
import com.studenttomsk.upYourParty.Views.Fragments.AddReview.AddReviewMethods;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendToBdReview implements AddReviewMethods.ModelReviews {


    @Override
    public void setReview(final OnFinishedListener onFinishedListener, ReviewClass reviewClass, String token) {
        NetworkService.getInstance().getJSONApi().setReview(reviewClass,token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            onFinishedListener.onSuccess();
                        }
                        else {
                            onFinishedListener.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onFinishedListener.onFailure(t.getMessage());
                    }
                });

    }
}

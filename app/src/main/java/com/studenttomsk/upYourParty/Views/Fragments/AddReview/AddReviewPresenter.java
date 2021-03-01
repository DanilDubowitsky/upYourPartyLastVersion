package com.studenttomsk.upYourParty.Views.Fragments.AddReview;

import com.studenttomsk.upYourParty.Classes.ReviewClass;

public class AddReviewPresenter implements AddReviewMethods.PresenterReviews, AddReviewMethods.ModelReviews.OnFinishedListener {

    AddReviewMethods.ViewReviews view;
    AddReviewMethods.ModelReviews model;

    public AddReviewPresenter(AddReviewMethods.ViewReviews view, AddReviewMethods.ModelReviews model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void setReviewBd(ReviewClass reviewClass, String token) {
        model.setReview(this,reviewClass,token);
    }

    @Override
    public void onSuccess() {
        view.onSuccessListener();
    }

    @Override
    public void onFailure(String s) {

    }
}

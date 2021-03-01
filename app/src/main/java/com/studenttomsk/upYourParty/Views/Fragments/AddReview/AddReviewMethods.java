package com.studenttomsk.upYourParty.Views.Fragments.AddReview;

import com.studenttomsk.upYourParty.Classes.ReviewClass;

public interface AddReviewMethods {
    interface ViewReviews{
        void onSuccessListener();
        void onFailListener();
    }

    interface PresenterReviews{
        void setReviewBd(ReviewClass reviewClass, String token);
    }

    interface ModelReviews{
        interface OnFinishedListener{
            void onSuccess();
            void onFailure(String s);
            }
            void setReview(OnFinishedListener onFinishedListener, ReviewClass reviewClass, String token);
        }
    }



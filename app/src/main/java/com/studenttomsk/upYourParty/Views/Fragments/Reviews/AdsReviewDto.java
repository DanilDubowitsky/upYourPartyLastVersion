package com.studenttomsk.upYourParty.Views.Fragments.Reviews;

import com.studenttomsk.upYourParty.Classes.AdsReviews;

import java.util.ArrayList;
import java.util.List;

public class AdsReviewDto {
    List<AdsReviews> adsReviewList = new ArrayList<>();
    public List<AdsReviews> getAdsReviewList() {
        return adsReviewList;
    }

    public void setAdsReviewList(List<AdsReviews> adsReviewList) {
        this.adsReviewList = adsReviewList;
    }

}

package com.studenttomsk.upYourParty.Views.Fragments.AddReview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.ReviewClass;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonDataAnnounce;
import com.studenttomsk.upYourParty.Network.SendToBdReview;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Fragments.Reviews.FragmentReviews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FragmentAddReview extends Fragment implements View.OnClickListener, AddReviewMethods.ViewReviews {
    ImageView starOne, starTwo, starThree, starFour, starFive;
    Button sendRevBtn;
    TextView textGraduate;
    EditText content;
    private float raiting;
    AddReviewPresenter presenter;
    RatingBar ratingBar;
    View v;
    Toast toast;
    OnFragmentInteractionListener fragmentListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_set_review,container,false);

        sendRevBtn = v.findViewById(R.id.sendReviewBtn);
        textGraduate = v.findViewById(R.id.text_graduate);
        content = v.findViewById(R.id.textReview);
        raiting = 3;
        toast = Toast.makeText(getContext(),"Отзыв не может быть пустым",Toast.LENGTH_SHORT);
        ratingBar = v.findViewById(R.id.ratingBarReview);
        ratingBar.setRating(raiting);
        ratingBar.invalidate();
        setDataToActivity();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                raiting = rating;
                switch ((int)rating){
                    case 0:
                        textGraduate.setText("Ужасно");
                        break;
                    case 1:
                        textGraduate.setText("Ужасно");
                        break;
                    case 2:
                        textGraduate.setText("Плохо");
                        break;
                    case 3:
                        textGraduate.setText("Хорошо");
                        break;
                    case 4:
                        textGraduate.setText("Отлично");
                        break;
                    case 5:
                        textGraduate.setText("Великолепно");
                        break;
                }
            }
        });
        sendRevBtn.setOnClickListener(this);
        presenter = new AddReviewPresenter(this,new SendToBdReview());
        return v;
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
        fragmentListener.onFragmentInteraction("ОСТАВИТЬ ОТЗЫВ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendReviewBtn:{
                if(raiting != 0){

                    Date currentDate = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                    String dateText = dateFormat.format(currentDate);
                    String graduate = content.getText().toString();
                    ReviewClass reviewClass = new ReviewClass(graduate, SingletonDataAnnounce.getInstance().getData().getId(),Float.toString(raiting),dateText);
                    if(!graduate.equals("")) {
                        sendRevBtn.setClickable(false);
                        presenter.setReviewBd(reviewClass, Singleton.getInstance().GetToken());
                    }
                    else{
                        toast.cancel();
                        toast = Toast.makeText(getContext(),"Отзыв не может быть пустым",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else{
                    toast.cancel();
                    toast = Toast.makeText(getContext(),"Поставьте оценку!",Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            }


        }
    }

    @Override
    public void onSuccessListener() {
        Toast.makeText(getContext(),"Отзыв оставлен",Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void onFailListener() {
        Toast.makeText(getContext(),"Ошибка соединения с сервером",Toast.LENGTH_LONG).show();
    }
}

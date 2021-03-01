package com.studenttomsk.upYourParty.Network;

import com.studenttomsk.upYourParty.Classes.RefreshClass;
import com.studenttomsk.upYourParty.Classes.AddAnnounceClass;
import com.studenttomsk.upYourParty.Classes.AnnouncementClass;
import com.studenttomsk.upYourParty.Classes.ReviewClass;
import com.studenttomsk.upYourParty.Classes.AddFavorites;
import com.studenttomsk.upYourParty.Classes.ClassId;
import com.studenttomsk.upYourParty.Classes.AnnounceClass;
import com.studenttomsk.upYourParty.Classes.ClassEmail;
import com.studenttomsk.upYourParty.Classes.ClassFavorites;
import com.studenttomsk.upYourParty.Classes.AllMessagesClass;
import com.studenttomsk.upYourParty.Classes.ChatsItemClass;
import com.studenttomsk.upYourParty.Classes.DidClass;
import com.studenttomsk.upYourParty.Classes.RoomChatDto;
import com.studenttomsk.upYourParty.Classes.ChangeProfileClass;
import com.studenttomsk.upYourParty.Classes.PasswordClass;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;
import com.studenttomsk.upYourParty.Views.Fragments.Reviews.AdsReviewDto;
import com.studenttomsk.upYourParty.Classes.AuthorizationRequestDto;
import com.studenttomsk.upYourParty.Classes.AdsSearchFilterDto;
import com.studenttomsk.upYourParty.Classes.SearchAnnouncementRes;
import com.studenttomsk.upYourParty.Classes.RegistrationClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {


    @POST("/auth/refresher")
    public Call<Map<String,String>> sendRefresh(@Body RefreshClass refreshClass);

    @GET("/auth/success")
    public Call<ResponseBody> getSuc(@Header("Authorization") String authToken);

    @Multipart
    @POST("/auth/image/load")
    Call<ResponseBody> loadProfileImage(@Part MultipartBody.Part image, @Header("Authorization") String authToken);

    @POST("/ads/create")
    public Call<ResponseBody>addAnnouncement(@Body AddAnnounceClass announcementClass, @Header("Authorization") String authToken);

    @POST("/ads/search")
    public Call<ArrayList<SearchAnnouncementRes>> searchAds(@Body AdsSearchFilterDto adsSearchFilterDto, @Header("Authorization") String authToken);

    @POST("/auth/login")
    public  Call<Map<String,String>>login(@Body AuthorizationRequestDto authorizationRequestDto);

    @GET("/chat/my")
    Call<List<ChatsItemClass>>getMyChats(@Header("Authorization") String authToken);

    @POST("/chat/read-message")
    Call<ResponseBody>readMessage(@Body DidClass didClass, @Header("Authorization") String authToken);

    @POST("/chat/message")
    Call<List<AllMessagesClass>>getAllMessages(@Body DidClass did, @Header("Authorization") String authToken);

    @POST("/ads/setrating")
    public Call<ResponseBody>setReview(@Body ReviewClass reviewClass, @Header("Authorization") String authToken);

    @GET("/ads/profile/{id}")
    public Call<AnnounceClass>getAnnounceById(@Path("id") long id, @Header("Authorization") String authToken);

    @GET("/auth/profile-person")
    Call<ProfileNumbNameTel> getProfileInfo(@Header("Authorization") String authToken);

    @POST("/auth/new-password")
    Call<ResponseBody>changePassword(@Body PasswordClass passwordClass, @Header("Authorization") String authToken);

    @POST("/ads/change-profile")
    Call<ResponseBody>changeProfile(@Body ChangeProfileClass changeProfileClass, @Header("Authorization") String authToken);

    @POST("/ads/change")
    Call<ResponseBody> changeAnnounce(@Body AnnouncementClass announcementClass, @Header("Authorization") String authToken);

    @POST("/chat/create-room")
    Call<DidClass> createRoom(@Body RoomChatDto roomChatDto, @Header("Authorization") String authToken);

    @POST("/auth/reset-password")
    Call<ResponseBody>resetPass(@Body ClassEmail email);

    @POST("/ads/remove")
    Call<ResponseBody>deleteAds(@Body ClassId id,@Header("Authorization") String authToken);

    @GET("/chat/email")
    Call<String>getEmail(@Header("Authorization") String authToken);

    @POST("/ads/add-favorites")
    Call<ClassId> addToFavorites(@Body AddFavorites id, @Header("Authorization") String authToken);

    @POST("/ads/remove-favorites")
    Call<ResponseBody> removeFavorites(@Body AddFavorites id, @Header("Authorization") String authToken);

    @POST("/auth/send-email")
    Call<ResponseBody>confirmEmail(@Header("Authorization") String authToken);

    @GET("/ads/my-favorites")
    Call<ArrayList<ClassFavorites>>getMyFavorites(@Header("Authorization") String authToken);

    @Multipart
    @POST("/ads/image/load")
    Call<Map<String,String>>uploadImage(@Part MultipartBody.Part image);

    @POST("/ads/image/delete")
    Call<ResponseBody>deleteImage(@Body List<String> strList);

    @GET("ads/my")
    Call<List<AnnounceClass>> getMyAnnouncements(@Header("Authorization") String authToken);

    @GET("/ads/getrating/{id}")
    public Call<AdsReviewDto>getReviews(@Path("id") long id);

    @POST("/auth/register")
    Call<ResponseBody> reg(@Body RegistrationClass registrationClass);
}

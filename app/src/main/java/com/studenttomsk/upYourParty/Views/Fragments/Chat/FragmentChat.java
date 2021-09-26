package com.studenttomsk.upYourParty.Views.Fragments.Chat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.studenttomsk.upYourParty.Adapters.RecycleChatAdapter;
import com.studenttomsk.upYourParty.Classes.ChatMessageClass;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.ProfilePersonFrom;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonDataAnnounce;
import com.studenttomsk.upYourParty.Classes.SingletonDid;
import com.studenttomsk.upYourParty.Classes.SingletonEmail;
import com.studenttomsk.upYourParty.Classes.SingletonImageProfile;
import com.studenttomsk.upYourParty.Classes.SingletonMyFIO;
import com.studenttomsk.upYourParty.Network.ModelChat;
import com.studenttomsk.upYourParty.Network.NetworkService;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Classes.AllMessagesClass;
import com.studenttomsk.upYourParty.Classes.ChatsItemClass;
import com.studenttomsk.upYourParty.Classes.DidClass;
import com.studenttomsk.upYourParty.Views.Fragments.Messages.MessagesMethods;
import com.studenttomsk.upYourParty.Views.Fragments.Messages.MessagesPresenter;
import com.studenttomsk.upYourParty.Network.ModelMessages;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

import static android.app.Activity.RESULT_OK;

public class FragmentChat extends Fragment implements  View.OnClickListener, MessagesMethods.MessagesView, ChatMethods.ChatView {
    View v;
    ImageButton sendMsg, addButton;
    StompClient mStompClient;
    EditText textMessage;
    RecyclerView recyclerView;
    RecycleChatAdapter adapter;
    LinearLayoutManager layoutManager;
    List<AllMessagesClass> list;
    String myEmail;
    ChatPresenter chatPresenter;
    String did;
    Toast toast;
    ProgressDialog pd;
    static final int GALLERY_REQUEST = 1;
    MessagesPresenter messagesPresenter;
    String emailTo;
    final private String urlImage = "http://37.21.255.194:8000/ads/image/";
    String imageUrl;
    Uri selectedImage;
    OnFragmentInteractionListener fragmentListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.fragment_chat,container,false);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        sendMsg = v.findViewById(R.id.sendMsg);
        sendMsg.setOnClickListener(this);
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://37.21.255.194:8000/gs-guide-websocket/websocket");
        mStompClient.connect();
        chatPresenter = new ChatPresenter(this,new ModelChat());
        textMessage = v.findViewById(R.id.message);
        addButton = v.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        messagesPresenter = new MessagesPresenter(this,new ModelMessages());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        imageUrl = "";
        recyclerView = v.findViewById(R.id.rec_chat);
        selectedImage = Uri.parse("");
        toast = Toast.makeText(getContext(),"Фотография не может быть формата gif",Toast.LENGTH_SHORT);
        did = SingletonDid.getInstance().getDid();
        emailTo = SingletonEmail.getInstance().getEmail();
        fragmentListener.setBackButton(true);
        messagesPresenter.getMessages(new DidClass(did));
        NetworkService
                .getInstance()
                .getJSONApi()
                .getEmail(Singleton.getInstance().GetToken())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            setListener(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });

       return v;
    }

    void setDataToActivity(String fio){
        fragmentListener.onFragmentInteraction(fio);

    }

    public void createFile(){
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            File file = new File(filePath);
            RequestBody fileRequest = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selectedImage)), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
            pd = new ProgressDialog(getContext());
            pd.setTitle("Подождите");
            pd.setMessage("Идет загрузка");
            pd.show();
            chatPresenter.setImagePost(part);
        }
        catch (Exception e){
            Toast.makeText(getContext(),"Невозможно загрузить данное изображение",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addButton.setClickable(true);
        switch(requestCode){
            case GALLERY_REQUEST:{
                if(resultCode == RESULT_OK)
                    try {
                        selectedImage = data.getData();
                        if (data != null) {
                            String mimetype = getActivity().getContentResolver().getType(selectedImage);
                            if (!mimetype.equals("image/gif")) {
                                createFile();
                            } else {
                                Toast.makeText(getContext(), "Фотография не может быть формата gif", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                catch (Exception e){
                    Toast.makeText(getContext(),"Невозможно загрузить данное изображение",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    void setData(){
        try {
            list = new ArrayList<>();
            initRecycle();
        }
        catch (Exception e){

        }
    }
    void initRecycle(){

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecycleChatAdapter(getContext(),(ArrayList<AllMessagesClass>) list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        try{
            recyclerView.scrollToPosition(list.size()-1);
        }
        catch (Exception e){

        }
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


    void setListener(String email){
        myEmail = email;

        mStompClient.topic("/topic/"+myEmail).subscribe(topicMessage -> {
           AllMessagesClass message = new Gson().fromJson(topicMessage.getPayload(),AllMessagesClass.class);

           try {
               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       if(SingletonDid.getInstance().getDid().equals(message.getDid())) {
                           messagesPresenter.redMes(new DidClass(SingletonDid.getInstance().getDid()));
                           list.add(message);
                           recyclerView.scrollToPosition(list.size()-1);
                           adapter.notifyDataSetChanged();
                       }
                   }
               });
           }
           catch (Exception e){

           }
        });
    }

    void setMessage(){
        Date currentDate = new Date();
        DateFormat timeFormat = new SimpleDateFormat("dd.MM HH:mm", Locale.getDefault());
        String timeString = timeFormat.format(currentDate);
        if((!did.equals("") && !textMessage.getText().toString().equals("")) || !selectedImage.toString().equals("")) {
            ChatMessageClass message = new ChatMessageClass(myEmail, emailTo,
                    textMessage.getText().toString(), timeString, SingletonDid.getInstance().getDid(),imageUrl);
            mStompClient.send("/app/send-message", message.json()).subscribe();
            selectedImage = Uri.parse("");
            try {
                list.add(new AllMessagesClass(new ProfilePersonFrom("",
                        new ProfileNumbNameTel("",
                                SingletonMyFIO.getInstance().getFirstName(),
                                "",
                                "",
                                SingletonMyFIO.getInstance().getLastName(),
                                SingletonImageProfile.getInstance().getDid(),"")),
                        "",
                        timeString, "",
                        false, textMessage.getText().toString(),"",imageUrl));
                imageUrl = "";
            } catch (Exception e) {
                list = new ArrayList<>();
                imageUrl = "";
            }
            textMessage.setText("");
            recyclerView.scrollToPosition(list.size()-1);
            adapter.notifyDataSetChanged();
        }
        else{
            toast.cancel();
            toast = Toast.makeText(getContext(),"Сообщение не может быть пустым",Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sendMsg){
            setMessage();
        }

        if(v.getId() == R.id.addButton){
            addButton.setClickable(false);
            selectImage();
        }
    }

    @Override
    public void onSuccessLoadMessages(List<AllMessagesClass> allMessagesClasses) {
        list = new ArrayList<>(allMessagesClasses);
        initRecycle();
    }

    @Override
    public void onSuccessLoad(List<ChatsItemClass> chatsItemClass) {

    }

    @Override
    public void onSuccessEmail(String email) {

    }

    @Override
    public void onFailure() {
        setData();
    }

    @Override
    public void onSuccessReadMessage() {

    }

    @Override
    public void onSuccessChange(Map<String, String> name) {
        imageUrl =  name.get("filename");
        pd.dismiss();
        Toast.makeText(getContext(),"Фотография загружена, она отобразится в вашем следующем сообщении",Toast.LENGTH_SHORT).show();
        addButton.setClickable(true);
    }

    @Override
    public void onFail(String string) {
        Toast.makeText(getContext(),"Ошибка загрузки фотографии", Toast.LENGTH_SHORT).show();
    }
}

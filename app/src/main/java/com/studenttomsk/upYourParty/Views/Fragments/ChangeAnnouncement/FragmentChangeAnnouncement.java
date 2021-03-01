package com.studenttomsk.upYourParty.Views.Fragments.ChangeAnnouncement;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studenttomsk.upYourParty.Adapters.RecImagesAdapterChange;
import com.studenttomsk.upYourParty.Classes.FIOValidator;
import com.studenttomsk.upYourParty.Classes.ModelChangeRecycleWithImages;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.PhoneNumberValidator;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonDataAnnounce;
import com.studenttomsk.upYourParty.Classes.SingletonListImagesNames;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Fragments.AddAnnoun.AddAnnounMethods;
import com.studenttomsk.upYourParty.Network.AddAnnounceModel;
import com.studenttomsk.upYourParty.Classes.AdsProfile;
import com.studenttomsk.upYourParty.Classes.AnnouncementClass;
import com.studenttomsk.upYourParty.Classes.ModelRecycleWithImages;
import com.studenttomsk.upYourParty.Views.Fragments.AddAnnoun.PresenterAddAnnounce;
import com.studenttomsk.upYourParty.Views.Fragments.MenegerAnnoun.FragmentManagerAnnouncement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class FragmentChangeAnnouncement extends Fragment implements View.OnClickListener, AddAnnounMethods.View {

    View view;
    static final int GALLERY_REQUEST = 1;
    ArrayList<ModelChangeRecycleWithImages> list;
    FloatingActionButton saveAnnouncement;
    private String urlImage = "http://178.170.220.39:8080/ads/image/";
    SharedPreferences savenToken;
    EditText title, description, price, city, phone;
    private RecyclerView recyclerView;
    private RecImagesAdapterChange recAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelRecycleWithImages> listImages;
    Uri selectedImage;
    Uri uri;
    ProgressDialog pd;
    String token;
    List<String> imagesNames;
    List<String> imgToDelete;
    List<ModelChangeRecycleWithImages> listImg;
    PresenterAddAnnounce presenter;
    ArrayList<String>imagesToSend;
    Spinner spinner;
    OnFragmentInteractionListener fragmentListener;
    Toast toast;
    int countClicks;
    private int currPos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_announcement,container,false);
        pd = new ProgressDialog(getContext());
        pd.setTitle("Подождите");
        pd.setMessage("Создание объявления...");
        pd.show();
        saveAnnouncement = view.findViewById(R.id.saveChangesButton);
        saveAnnouncement.setOnClickListener(this);
        token = Singleton.getInstance().GetToken();
        savenToken = PreferenceManager.getDefaultSharedPreferences(getContext());
        spinner = view.findViewById(R.id.spinnerCategoriesEdit);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.spinnerCategories,R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_item_text);
        spinner.setAdapter(adapter);
        toast = Toast.makeText(getContext(), "Поле город имеет неверный формат", Toast.LENGTH_SHORT);
        imagesToSend = new ArrayList<>();
        imgToDelete = new ArrayList<>();

        getActivity().setTitle("Изменить объявление");
        countClicks = 0;
        title = view.findViewById(R.id.name_edit_announce);
        description = view.findViewById(R.id.description_edit_announce);
        price = view.findViewById(R.id.price_edit_announce);
        city =view.findViewById(R.id.cityEditAnnounce);
        phone = view.findViewById(R.id.phoneNumberEditAnnounce);
        setDataToActivity();


        presenter = new PresenterAddAnnounce(this,new AddAnnounceModel());


        listImg = new ArrayList<>();
        imagesNames = new ArrayList<>(5);
        for(int i=0;i<5;i++){
            imagesNames.add("");
        }
        if(SingletonDataAnnounce.getInstance().getData().getAdsImages().size()>0) {
            if(SingletonDataAnnounce.getInstance().getData().getAdsImages().size()>5){
                for (int i = 0; i < 5; i++) {
                    listImg.add(new ModelChangeRecycleWithImages(urlImage + SingletonDataAnnounce.getInstance().getData().getAdsImages().get(i).getFilename()));
                    imagesNames.set(i, SingletonDataAnnounce.getInstance().getData().getAdsImages().get(i).getFilename());
                }
            }
            else {
                for (int i = 0; i < SingletonDataAnnounce.getInstance().getData().getAdsImages().size(); i++) {
                    listImg.add(new ModelChangeRecycleWithImages(urlImage + SingletonDataAnnounce.getInstance().getData().getAdsImages().get(i).getFilename()));
                    imagesNames.set(i, SingletonDataAnnounce.getInstance().getData().getAdsImages().get(i).getFilename());
                }
            }
       }
        uri = Uri.parse("android.resource://com.studenttomsk.upYourParty/drawable/stand_img_icon");
        try {
            title.setText(SingletonDataAnnounce.getInstance().getData().getTitle());
            description.setText(SingletonDataAnnounce.getInstance().getData().getDescription());
            price.setText(String.valueOf(SingletonDataAnnounce.getInstance().getData().getPrice()));
            city.setText(SingletonDataAnnounce.getInstance().getData().getCity());
            phone.setText(SingletonDataAnnounce.getInstance().getData().getNumber_phone());
            if(SingletonDataAnnounce.getInstance().getData().getCategory().equals("День рождения"))
                spinner.setSelection(0);
            else if(SingletonDataAnnounce.getInstance().getData().getCategory().equals("Детский праздник"))
                spinner.setSelection(1);
            else if(SingletonDataAnnounce.getInstance().getData().getCategory().equals("Свадьба"))
                spinner.setSelection(2);
            else if(SingletonDataAnnounce.getInstance().getData().getCategory().equals("Корпоратив"))
                spinner.setSelection(3);
        }
        catch (Exception e){

        }
        setDataToRecycle();
        createRecycle();
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        pd.dismiss();
        return view;
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
        fragmentListener.onFragmentInteraction("РЕДАКТИРОВАНИЕ");
        fragmentListener.setBackButton(true);
    }

    void createFile(){
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
            presenter.setImagePost(part);
            pd = new ProgressDialog(getContext());
            pd.setTitle("Подождите");
            pd.setMessage("Загрузка фотографии...");
            pd.show();
        }
        catch (Exception e){
            Toast.makeText(getContext(),"Невозможно загрузить данное изображение",Toast.LENGTH_SHORT).show();
            list.get(currPos).changeImage(String.valueOf(uri));
        }
    }

    void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    public void createRecycle(){
        recyclerView = view.findViewById(R.id.image_toDownload_change);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recAdapter = new RecImagesAdapterChange(getContext(),list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recAdapter);

        recAdapter.setOnItemClickListener(new RecImagesAdapterChange.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    if (countClicks == 0) {
                        countClicks++;
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        currPos = position;
                        if (!imagesNames.get(position).equals("")) {
                            imgToDelete.add(imagesNames.get(position));
                            presenter.deleteImagePost(imgToDelete);
                            imgToDelete = new ArrayList<>();
                        }
                        selectImage();
                    }
                }
                catch (Exception e){

                }
            }

            @Override
            public void onDeleteClick(int position) {
                try {
                    if (!list.get(position).getImageUri().equals(uri)) {
                        imgToDelete.add(imagesNames.get(position));
                        presenter.deleteImagePost(imgToDelete);
                        imagesNames.set(position, "");
                        SingletonListImagesNames.getInstance().SetData(imagesNames);
                        list.get(position).changeImage(String.valueOf(uri));
                        recAdapter.notifyItemChanged(position);
                        imgToDelete = new ArrayList<>();
                    }
                }
                catch (Exception e){

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        countClicks = 0;
        switch (requestCode){
            case GALLERY_REQUEST:{
                if(resultCode == RESULT_OK){
                    try {
                        selectedImage = data.getData();
                        String mimetype = getActivity().getContentResolver().getType(selectedImage);
                        if (!mimetype.equals("image/gif")) {
                            list.get(currPos).changeImage(String.valueOf(selectedImage));
                            recAdapter.notifyItemChanged(currPos);
                            createFile();
                        } else {
                            Toast.makeText(getContext(), "Изображение не может быть формата gif", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),"Невозможно загрузить данное изображение",Toast.LENGTH_SHORT).show();

                    }
                }
                break;
            }
        }
    }

    public void setDataToRecycle(){
        list = new ArrayList<>();
        list.addAll(listImg);
        for(int i=listImg.size();i<5;i++){
            list.add(new ModelChangeRecycleWithImages(String.valueOf(uri)));
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.saveChangesButton){
            PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
            FIOValidator fioValidator = new FIOValidator();
            for(int i=0;i<5;i++){
                if(!imagesNames.get(i).equals("")){
                    imagesToSend.add(imagesNames.get(i));
                }
            }
            try {
                int t = Integer.parseInt(price.getText().toString());
                if(t>0) {
                    if (!title.getText().toString().trim().isEmpty()) {
                        if (!city.getText().toString().equals("")) {
                            if (!description.getText().toString().trim().isEmpty()) {
                                if (phoneNumberValidator.validate(phone.getText().toString())) {
                                    if(fioValidator.validate(city.getText().toString())) {
                                        saveAnnouncement.setClickable(false);
                                        AnnouncementClass announcementClass = new AnnouncementClass(SingletonDataAnnounce.getInstance().getData().getId(), title.getText().toString(), price.getText().toString(),
                                                city.getText().toString(), spinner.getSelectedItem().toString(), new AdsProfile(description.getText().toString()), imagesToSend);
                                        presenter.setPostChangeAnnounce(announcementClass, token);
                                    }
                                    else{
                                        toast.cancel();
                                        toast = Toast.makeText(getContext(), "Поле город имеет неверный формат", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                                else {
                                    toast.cancel();
                                    toast =  Toast.makeText(getContext(), "Телефон имеет неверный формат", Toast.LENGTH_SHORT);
                                    toast.show();
                                }


                            } else {
                                toast.cancel();
                                toast =  Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT);
                                toast.show();

                            }
                        } else {
                            toast.cancel();
                            toast =  Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                    } else {
                        toast.cancel();
                        toast =   Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
                else{
                    toast.cancel();
                    toast =   Toast.makeText(getContext(), "Неправильный формат цены", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
            catch (Exception e){
                toast.cancel();
                toast =   Toast.makeText(getContext(), "Неправильный формат цены", Toast.LENGTH_SHORT);
                toast.show();

            }
        }
    }

    @Override
    public void onSuccessChange() {
        toast.cancel();
        toast = Toast.makeText(getContext(),"Объявление успешно изменено",Toast.LENGTH_SHORT);
        toast.show();
        SingletonListImagesNames.getInstance().clearSingleton();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void onSuccessDelete() {
        Toast.makeText(getContext(),"Фотография удалена",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successUploadImage(Map<String, String> name) {
        pdHied();
        Toast.makeText(getContext(),"Фотография загружена",Toast.LENGTH_LONG).show();
        imagesNames.set(currPos,name.get("filename"));
        SingletonListImagesNames.getInstance().SetData(imagesNames);
    }

    @Override
    public void showMessage() {

    }

    @Override
    public void onFailMessage(String message) {

        Toast.makeText(getContext(),"Ошибка "+message,Toast.LENGTH_LONG);
    }

    @Override
    public void pdShow() {
        pd = new ProgressDialog(getContext());
        pd.setTitle("Подождите");
        pd.setMessage("Создание объявления...");
        pd.show();
    }

    @Override
    public void pdHied() {
        pd.dismiss();
    }
}

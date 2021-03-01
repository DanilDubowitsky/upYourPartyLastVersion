package com.studenttomsk.upYourParty.Views.Fragments.AddAnnoun;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.common.StringUtils;
import com.studenttomsk.upYourParty.Adapters.RecycleImagesAdapter;
import com.studenttomsk.upYourParty.Classes.AddAnnounceClass;
import com.studenttomsk.upYourParty.Classes.AdsProfile;
import com.studenttomsk.upYourParty.Classes.FIOValidator;
import com.studenttomsk.upYourParty.Classes.ModelRecycleWithImages;
import com.studenttomsk.upYourParty.Classes.OnFragmentInteractionListener;
import com.studenttomsk.upYourParty.Classes.PhoneNumberValidator;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Classes.SingletonListImagesNames;
import com.studenttomsk.upYourParty.Network.AddAnnounceModel;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Fragments.MenegerAnnoun.FragmentManagerAnnouncement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


public class FragmentAddAnnouncement extends Fragment implements View.OnClickListener, AddAnnounMethods.View  {
    static final int GALLERY_REQUEST = 1;
    View view;
    ViewPager viewPager;
    private RecyclerView recyclerView;
    private RecycleImagesAdapter recAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelRecycleWithImages> list;
    EditText name, price, description, city, phone, tags;
    FloatingActionButton addAnnounceBtn;
    String token;
    String category;
    Spinner spinner;
    Uri selectedImage;
    Uri uri;
    Toast toast;
    int clickCountImage;
    OnFragmentInteractionListener fragmentListener;
    List<String> imagesToSend;
    List<String> imagesNames;
    List<String> imgToDelete;
    private int currPos;
    PresenterAddAnnounce presenterAddAnnounce;
    ProgressDialog pd;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_announcement, container, false);
        token = Singleton.getInstance().GetToken();
        toast =  Toast.makeText(getContext(), "Поле город имеет неверный формат", Toast.LENGTH_SHORT);
        getActivity().setTitle("НОВОЕ ОБЪЯВЛЕНИЕ");

        imagesNames = new ArrayList<>(5);
        for(int i=0;i<5;i++){
            imagesNames.add("");
        }
        spinner = view.findViewById(R.id.spinnerCategoriesAdd);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.spinnerCategories,R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_item_text);
        spinner.setAdapter(adapter);
        clickCountImage = 0;

        imgToDelete = new ArrayList<>();

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        phone = view.findViewById(R.id.phoneNumberAddAnnounce);
        name = view.findViewById(R.id.name_add_announce);
        description = view.findViewById(R.id.description_add_announce);
        price = view.findViewById(R.id.add_announcement_price);
        city = view.findViewById(R.id.city);
        addAnnounceBtn = view.findViewById(R.id.add_announcement_btn);
        addAnnounceBtn.setOnClickListener(this);
        uri = Uri.parse("android.resource://com.studenttomsk.upYourParty/drawable/stand_img_icon");
        imagesToSend = new ArrayList<>();
        tags = view.findViewById(R.id.tegs);

        setDataToRecycle();
        createRecycle();
        setDataToActivity();


        presenterAddAnnounce = new PresenterAddAnnounce(this,new AddAnnounceModel());

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
        fragmentListener.onFragmentInteraction("НОВОЕ ОБЪЯВЛЕНИЕ");
        fragmentListener.setBackButton(true);
    }

    public void createRecycle(){
        recyclerView = view.findViewById(R.id.rec_with_images_toDownload);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recAdapter = new RecycleImagesAdapter(list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recAdapter);

        recAdapter.setOnItemClickListener(new RecycleImagesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    if (clickCountImage == 0) {
                        clickCountImage++;
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        currPos = position;
                        if (!imagesNames.get(position).equals("")) {
                            imgToDelete.add(imagesNames.get(position));
                            presenterAddAnnounce.deleteImagePost(imgToDelete);
                            imgToDelete = new ArrayList<>();
                            list.get(position).changeImage(uri);
                            recAdapter.notifyItemChanged(position);
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
                    if (!list.get(position).getImage().equals(uri)) {
                        imgToDelete.add(imagesNames.get(position));
                        presenterAddAnnounce.deleteImagePost(imgToDelete);
                        imagesNames.set(position, "");
                        SingletonListImagesNames.getInstance().SetData(imagesNames);
                        list.get(position).changeImage(uri);
                        recAdapter.notifyItemChanged(position);
                        imgToDelete = new ArrayList<>();
                    }
                }
                catch (Exception e){

                }
            }
        });
    }

    public void setDataToRecycle(){
        list = new ArrayList<>();
        list.add(new ModelRecycleWithImages(uri));
        list.add(new ModelRecycleWithImages(uri));
        list.add(new ModelRecycleWithImages(uri));
        list.add(new ModelRecycleWithImages(uri));
        list.add(new ModelRecycleWithImages(uri));
    }

    void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
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
            pd = new ProgressDialog(getContext());
            pd.setTitle("Подождите");
            pd.setMessage("Загрузка картинки...");
            pd.show();
            presenterAddAnnounce.setImagePost(part);
        }
        catch (Exception e){
            Toast.makeText(getContext(),"Невозможно загрузить данное изображение",Toast.LENGTH_SHORT).show();
            list.get(currPos).changeImage(uri);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        clickCountImage=0;
        switch (requestCode){
            case GALLERY_REQUEST:{
                if(resultCode == RESULT_OK){
                    try {
                        selectedImage = data.getData();
                        String mimetype = getActivity().getContentResolver().getType(selectedImage);
                        if (!mimetype.equals("image/gif")) {
                            list.get(currPos).changeImage(selectedImage);
                            recAdapter.notifyItemChanged(currPos);
                            createFile();
                        } else {
                            Toast.makeText(getContext(), "Изображение не может быть формата gif", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),"Невозможно загрузить данное изображение",Toast.LENGTH_SHORT).show();
                        list.get(currPos).changeImage(uri);
                    }
                }
                break;
            }
        }
    }

   void topreviousFragment(){
        SingletonListImagesNames.getInstance().clearSingleton();
        Fragment fragment = new FragmentManagerAnnouncement();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
   }

    @Override
    public void onClick(View v) {
        category = spinner.getSelectedItem().toString();
        String token = Singleton.getInstance().GetToken();
        for(int i=0;i<5;i++){
            if(!imagesNames.get(i).equals("")){
                imagesToSend.add(imagesNames.get(i));
            }
        }

        if(v.getId() == R.id.add_announcement_btn){
            PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
            FIOValidator fioValidator = new FIOValidator();
            String check;
            try {
                int t = Integer.parseInt(price.getText().toString());
                if (t > 0) {
                    if (!name.getText().toString().trim().isEmpty()) {
                        if (!city.getText().toString().equals("")) {
                            if(!description.getText().toString().trim().isEmpty()) {
                                if (phoneNumberValidator.validate(phone.getText().toString())) {
                                    if(fioValidator.validate(city.getText().toString())) {
                                        if(!tags.getText().toString().trim().isEmpty()) {
                                            addAnnounceBtn.setClickable(false);
                                            pdShow();
                                            AddAnnounceClass announcement = new AddAnnounceClass(name.getText().toString()
                                                    , price.getText().toString(),
                                                    city.getText().toString(),
                                                    category, new AdsProfile(description.getText().toString()), imagesToSend);
                                            presenterAddAnnounce.setPostAddAnnounce(announcement, token);
                                        }
                                        else{
                                            toast.cancel();
                                            toast = Toast.makeText(getContext(), "Поле теги имеет неверный формат", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                    }
                                    else{
                                        toast.cancel();
                                        toast = Toast.makeText(getContext(), "Поле город имеет неверный формат", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                                else{
                                    toast.cancel();
                                    toast = Toast.makeText(getContext(), "Телефон имеет неверный формат", Toast.LENGTH_SHORT);
                                    toast.show();

                                }
                            }
                            else{
                                toast.cancel();
                                toast = Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_SHORT);
                                toast.show();

                            }
                        } else {
                            toast.cancel();
                            toast = Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                    } else {
                        toast.cancel();
                        toast = Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
                else{
                    toast.cancel();
                    toast = Toast.makeText(getContext(),"Неправильный формат цены",Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
            catch (Exception e){
                toast.cancel();
                toast = Toast.makeText(getContext(),"Неправильный формат цены",Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }


    @Override
    public void onSuccessChange() {

    }

    @Override
    public void onSuccessDelete() {
        Toast.makeText(getContext(),"Фотография удалена",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successUploadImage(Map<String,String> name) {
        pdHied();
        Toast.makeText(getContext(),"Фотография загружена",Toast.LENGTH_SHORT).show();
        imagesNames.set(currPos,name.get("filename"));
        SingletonListImagesNames.getInstance().SetData(imagesNames);
    }

    @Override
    public void showMessage() {
        SingletonListImagesNames.getInstance().clearSingleton();
        pdHied();
        Toast.makeText(getContext(),"Объявление успешно создано",Toast.LENGTH_SHORT).show();
        topreviousFragment();
    }

    @Override
    public void onFailMessage(String message) {
        SingletonListImagesNames.getInstance().clearSingleton();
        pdHied();
        Toast.makeText(getContext(),"Ошибка соединения: " + message,Toast.LENGTH_SHORT).show();
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

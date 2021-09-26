package com.studenttomsk.upYourParty.Views.Fragments.Profile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.studenttomsk.upYourParty.Classes.ChangeProfileClass;
import com.studenttomsk.upYourParty.Classes.FIOValidator;
import com.studenttomsk.upYourParty.Classes.PasswordClass;
import com.studenttomsk.upYourParty.Classes.PasswordValidator;
import com.studenttomsk.upYourParty.Classes.ProfileNumbNameTel;
import com.studenttomsk.upYourParty.Classes.Singleton;
import com.studenttomsk.upYourParty.Network.ProfileModel;
import com.studenttomsk.upYourParty.R;
import com.studenttomsk.upYourParty.Views.Login.LoginActivity;
import com.studenttomsk.upYourParty.Classes.EmailValidator;
import com.studenttomsk.upYourParty.Classes.PhoneNumberValidator;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment implements View.OnClickListener, ProfileMethods.ViewProfile {
    View view;
    Button btnSave, cancel;
    static final int GALLERY_REQUEST = 1;
    ImageButton confEmail;
    CircleImageView changeProfileImage;
    PresenterProfile presenterProfile;
    Uri selectedImage;
    Uri standImg;
    int countImage;
    Bitmap bitmap;
    ProgressDialog pd;
    String oldName, oldSurname,oldOtchestvo,oldCity,oldEmail, oldNumberPhone;
    Toast toast;
    private String urlImage = "http://37.21.255.194:8000/ads/image/";
    EditText name,surname,otchestvo,email,city,phoneNumber, oldPassword, newPassword, confNewPassword;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        getActivity().setTitle("РЕДАКТИРОВАТЬ ПРОФИЛЬ");
        btnSave = view.findViewById(R.id.btn_saveChanges_profile);
        countImage = 0;
        standImg = Uri.parse("android.resource://com.studenttomsk.upYourParty/drawable/profileimage");
        toast = Toast.makeText(getContext(),"Пароль имеет неверный формат",Toast.LENGTH_LONG);
        name = view.findViewById(R.id.nameChange);
        surname = view.findViewById(R.id.surnameChange);
        otchestvo = view.findViewById(R.id.otchestvoChange);
        email = view.findViewById(R.id.emailChange);
        cancel = view.findViewById(R.id.btn_cancel);
        city = view.findViewById(R.id.cityChange);
        phoneNumber = view.findViewById(R.id.phoneNumberChange);
        oldPassword = view.findViewById(R.id.oldPasswordChange);
        newPassword = view.findViewById(R.id.newPasswordChange);
        confNewPassword = view.findViewById(R.id.confirmNewPasswordChange);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        if(Singleton.getInstance().getSocialEnter()) {
            surname.setFocusable(false);
            name.setFocusable(false);
            email.setFocusable(false);
            oldPassword.setFocusable(false);
            newPassword.setFocusable(false);
            confNewPassword.setFocusable(false);
        }

        changeProfileImage = view.findViewById(R.id.changeProfileImage);
        changeProfileImage.setOnClickListener(this);

        confEmail = view.findViewById(R.id.sendConfrimEmail);

        presenterProfile = new PresenterProfile(this,new ProfileModel());
        presenterProfile.setRequestProfileInfo(Singleton.getInstance().GetToken());
        cancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        confEmail.setOnClickListener(this);
        return view;

    }

    public void toLoginActivity(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
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
            presenterProfile.loadImage(part);
        }
        catch (Exception e){
            toast.cancel();
            toast = Toast.makeText(getContext(), "Фотография не может быть формата gif", Toast.LENGTH_SHORT);
            toast.show();
            changeProfileImage.setImageURI(standImg);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        countImage = 0;
        switch(requestCode){
            case GALLERY_REQUEST:{
                if(resultCode == RESULT_OK)
                    try {
                        selectedImage = data.getData();
                        if (data != null) {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImage);
                            String mimetype = getActivity().getContentResolver().getType(selectedImage);
                            if (!mimetype.equals("image/gif")) {
                                changeProfileImage.setImageBitmap(bitmap);
                                createFile();
                            } else {
                                toast.cancel();
                                toast = Toast.makeText(getContext(), "Фотография не может быть формата gif", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                catch (Exception e){
                    Toast.makeText(getContext(),"Невозможно загрузить данное изображение",Toast.LENGTH_SHORT).show();
                    changeProfileImage.setImageURI(standImg);
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

    void setRequest(){
        presenterProfile.setChangePost(new ChangeProfileClass(name.getText().toString(),
                        surname.getText().toString(),
                        phoneNumber.getText().toString(),
                        email.getText().toString(),
                        otchestvo.getText().toString(), city.getText().toString()),
                Singleton.getInstance().GetToken());
    }

    void changeProfileInfo(){
        if(!name.getText().toString().equals("") && !surname.getText().toString().equals("") &&
                !phoneNumber.getText().toString().equals("")
                && !city.getText().toString().equals("") &&
                !email.getText().toString().equals("")
                && !otchestvo.getText().toString().equals("")) {
            PhoneNumberValidator numbValid = new PhoneNumberValidator();
            EmailValidator emailValidator = new EmailValidator();
            FIOValidator fioValidator = new FIOValidator();
            if(numbValid.validate(phoneNumber.getText().toString())) {
                if(emailValidator.validate(email.getText().toString()) ) {
                    if(fioValidator.validate(surname.getText().toString()) && fioValidator.validate(name.getText().toString()) && fioValidator.validate(otchestvo.getText().toString()) && fioValidator.validate(city.getText().toString())) {
                        setRequest();
                    }
                    else{
                        toast.cancel();
                        toast = Toast.makeText(getContext(),"Неверный формат введенных данных",Toast.LENGTH_SHORT);
                        toast.show();
                        btnSave.setClickable(true);
                    }
                }
                else{
                    if(Singleton.getInstance().getSocialEnter()){
                        setRequest();
                    }
                    else {
                        toast.cancel();
                        toast = Toast.makeText(getContext(), "Неправильный формат e-mail", Toast.LENGTH_SHORT);
                        toast.show();
                        btnSave.setClickable(true);
                    }
                }
            }
            else{
                toast.cancel();
                toast = Toast.makeText(getContext(),"Неправильный формат номера телефона",Toast.LENGTH_SHORT);
                toast.show();
                btnSave.setClickable(true);
            }
        }
        else{
            toast.cancel();
            toast = Toast.makeText(getContext(),"Все поля должны быть заполнены",Toast.LENGTH_SHORT);
            toast.show();
            btnSave.setClickable(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:{
                name.setText(oldName);

                otchestvo.setText(oldOtchestvo);

                surname.setText(oldSurname);

                city.setText(oldCity);

                email.setText(oldEmail);

                phoneNumber.setText(oldNumberPhone);
                confNewPassword.setText("");
                newPassword.setText("");
                oldPassword.setText("");
                break;
            }
            case R.id.btn_saveChanges_profile:{
                PasswordValidator passwordValidator = new PasswordValidator();
                if(newPassword.getText().toString().equals("") && confNewPassword.getText().toString().equals("") && oldPassword.getText().toString().equals("")) {
                    btnSave.setClickable(false);
                    changeProfileInfo();
                }
                else {
                    if (newPassword.getText().toString().equals(confNewPassword.getText().toString()) && !newPassword.getText().toString().equals("")) {
                        if(newPassword.length()>=8) {
                            if(passwordValidator.validate(newPassword.getText().toString())) {

                                presenterProfile.setChangePasswordRequest(new PasswordClass(oldPassword.getText().toString(), newPassword.getText().toString()), Singleton.getInstance().GetToken());
                            }
                            else{
                                toast.cancel();
                                toast =  Toast.makeText(getContext(),"Пароль имеет неверный формат",Toast.LENGTH_SHORT);
                                toast.show();

                            }
                        }
                        else{
                            toast.cancel();
                            toast =  Toast.makeText(getContext(),"Пароль должен быть больше или равен 8 символам",Toast.LENGTH_SHORT);
                            toast.show();

                        }
                    }
                    else{
                        toast.cancel();
                        toast =  Toast.makeText(getContext(),"Ошибка при заполнении полей пароля",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                break;
            }
            case R.id.changeProfileImage:{
                if(countImage == 0) {
                    countImage++;
                    selectImage();
                }
                break;
            }
            case R.id.sendConfrimEmail:{
                if(!Singleton.getInstance().getSocialEnter()) {
                    confEmail.setClickable(false);
                    presenterProfile.setConfEmail();
                }
                else{
                    toast.cancel();
                    toast = Toast.makeText(getContext(),"Вы вошли через соц.сеть",Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            }
        }
    }

    @Override
    public void onSuccessChangePasswordMessage() {
        Toast.makeText(getContext(),"Пароль успешно изменен",Toast.LENGTH_SHORT).show();
       changeProfileInfo();
    }

    @Override
    public void onSuccessLoadInfo(ProfileNumbNameTel info) {
        name.setText(info.getName());
        oldName = info.getName();
        otchestvo.setText(info.getSurname());
        oldOtchestvo = info.getSurname();
        surname.setText(info.getMiddle_name());
        oldSurname = info.getMiddle_name();
        city.setText(info.getCity());
        oldCity = info.getCity();
        email.setText(info.getEmail());
        oldEmail = info.getEmail();
        phoneNumber.setText(info.getNumberPhone());
        oldNumberPhone = info.getNumberPhone();
        if(info.getAvatar()!=null) {
            try {
                Glide.with(getContext()).load(urlImage + info.getAvatar()).into(changeProfileImage);
            }
            catch (Exception e){
                Toast.makeText(getContext(),"Изображение не может быть загружено",Toast.LENGTH_SHORT).show();

            }

        }
        else{
            changeProfileImage.setImageResource(R.drawable.profileimage);
        }
    }

    @Override
    public void showSuccessMessage(String message) {

        toLoginActivity();
        Toast.makeText(getContext(),"Профиль успешно изменен, чтобы изменения вступили в силу, перезапустите приложение",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFailMessage(String code) {
        btnSave.setClickable(true);
            if(code.equals("409")) {
                toast.cancel();
                toast = Toast.makeText(getContext(), "Данный E-mail уже используется", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                toast.cancel();
                toast = Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT);
                toast.show();
            }
    }

    @Override
    public void onFailChangePas() {
        toast.cancel();
        toast =  Toast.makeText(getContext(),"Неверный старый пароль",Toast.LENGTH_SHORT);
        toast.show();

    }

    @Override
    public void successUploadImage(String t) {
        pd.dismiss();
        Toast.makeText(getContext(),"Фотография изменена, чтобы изменения вступили в силу перезапустите приложение",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailConfirm(int code) {
        if(code == 409) {
            Toast.makeText(getContext(), "Вы уже подтвердили свой email", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessSend() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_message,null);
        mBuilder.setView(view);
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onFailConnection() {
        toast.cancel();
        toast =  Toast.makeText(getContext(),"Ошибка соединения с сервером",Toast.LENGTH_SHORT);
        toast.show();
    }
}

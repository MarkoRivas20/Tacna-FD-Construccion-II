package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface RegistrarItemMenu {

    interface View{
        void onSaveItemMenuSuccessful();
        void onSaveItemMenuFailure();
        void onUploadItemMenuImageSuccessful(String Url_Imagen);
        void onUploadItemMenuImageFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void SaveItemMenu(DatabaseReference Database_Reference, ItemMenu_Modelo Item_Menu);
        void UploadItemMenuImage(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performSaveItemMenu(DatabaseReference Database_Reference, ItemMenu_Modelo Item_Menu);
        void performUploadItemMenuImage(StorageReference Storage_Reference, String ID_Establecimiento, Uri Imagen_Uri);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessSaveItemMenu();
        void onFailureSaveItemMenu();
        void onSuccessUploadItemMenuImage(String Url_Imagen);
        void onFailureUploadItemMenuImage();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }
}

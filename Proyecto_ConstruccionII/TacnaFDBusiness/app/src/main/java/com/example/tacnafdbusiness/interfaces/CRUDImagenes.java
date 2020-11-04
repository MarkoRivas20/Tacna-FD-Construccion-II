package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.ImagenEstablecimiento_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public interface CRUDImagenes {
    interface View{
        void onUploadImageSuccessful();
        void onUploadImageFailure();
        void onDeleteImageSuccessful();
        void onDeleteImageFailure();
        void onGetAllImagesSuccessful(ArrayList<ImagenEstablecimiento_Modelo> Imagenes_Establecimiento, Boolean Existe_Imagen);
        void onGetAllImagesFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void UploadImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, ImagenEstablecimiento_Modelo Imagen_Establecimiento, Uri Imagen_Uri);
        void DeleteImage(DatabaseReference Database_Reference, String ID_Imagen_Establecimiento, String Url_Imagen);
        void GetAllImages(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performUploadImage(StorageReference Storage_Reference, DatabaseReference Database_Reference, ImagenEstablecimiento_Modelo Imagen_Establecimiento, Uri Imagen_Uri);
        void performDeleteImage(DatabaseReference Database_Reference, String ID_Imagen_Establecimiento, String Url_Imagen);
        void performGetAllImages(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessUploadImage();
        void onFailureUploadImage();
        void onSuccessDeleteImage();
        void onFailureDeleteImage();
        void onSuccessGetAllImages(ArrayList<ImagenEstablecimiento_Modelo> Imagenes_Establecimiento, Boolean Existe_Imagen);
        void onFailureGetAllImages();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }
}

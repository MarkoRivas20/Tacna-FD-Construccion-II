package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.ImagenEstablecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.RepartidorEstablecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public interface CRUDRepartidores {

    interface View{
        void onSaveDeliveryManSuccessful();
        void onSaveDeliveryManFailure();
        void onSearchDeliveryManSuccessful(Repartidor_Modelo repartidor_modelos, Boolean Existe_Repartidor);
        void onSearchDeliveryManFailure();
        void onTakeOutDeliveryManSuccessful();
        void onTakeOutDeliveryManFailure();
        void onListDeliveryMenSuccessful(ArrayList<RepartidorEstablecimiento_Modelo> repartidorEstablecimiento_modelos, Boolean Existe_Repartidor_Establecimiento);
        void onListDeliveryMenFailure();
        void onSearchDeliveryManInfoSuccessful(ArrayList<Repartidor_Modelo> repartidor_modelos);
        void onSearchDeliveryManInfoFailure();
        void onGetEstablishmentInfoSuccessful(String Id_Establecimiento);
    }

    interface Presenter{
        void SaveDeliveryMan(DatabaseReference Database_Reference, RepartidorEstablecimiento_Modelo repartidorEstablecimiento_modelo);
        void SearchDeliveryMan(DatabaseReference Database_Reference, String Correo_Electronico_Repartidor);
        void SearchDeliveryManInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> repartidorEstablecimiento_modelos);
        void TakeOutDeliveryMan(DatabaseReference Database_Reference, String ID_Repartidor_Establecimiento, String ID_Establecimiento);
        void ListDeliveryMen(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetEstablishmentInfo(Context context);
    }

    interface Interactor{
        void performSaveDeliveryMan(DatabaseReference Database_Reference, RepartidorEstablecimiento_Modelo repartidorEstablecimiento_modelo);
        void performSearchDeliveryMan(DatabaseReference Database_Reference, String Correo_Electronico_Repartidor);
        void performSearchDeliveryManInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> repartidorEstablecimiento_modelos);
        void performTakeOutDeliveryMan(DatabaseReference Database_Reference, String ID_Repartidor_Establecimiento, String ID_Establecimiento);
        void performListDeliveryMen(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetEstablishmentInfo(Context context);
    }

    interface onOperationListener{
        void onSuccessSaveDeliveryMan();
        void onFailureSaveDeliveryMan();
        void onSuccessSearchDeliveryMan(Repartidor_Modelo repartidor_modelos, Boolean Existe_Repartidor);
        void onFailureSearchDeliveryMan();
        void onSuccessTakeOutDeliveryMan();
        void onFailureTakeOutDeliveryMan();
        void onSuccessListDeliveryMen(ArrayList<RepartidorEstablecimiento_Modelo> repartidorEstablecimiento_modelos, Boolean Existe_Repartidor_Establecimiento);
        void onFailureListDeliveryMen();
        void onSuccessSearchDeliveryManInfo(ArrayList<Repartidor_Modelo> repartidor_modelos);
        void onFailureSearchDeliveryManInfo();
        void onSuccessGetEstablishmentInfo(String Id_Establecimiento);
    }
}

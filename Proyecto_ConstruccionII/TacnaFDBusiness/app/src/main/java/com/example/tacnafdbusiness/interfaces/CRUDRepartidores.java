package com.example.tacnafdbusiness.interfaces;

import android.content.Context;
import android.net.Uri;

import com.example.tacnafdbusiness.modelo.RepartidorEstablecimiento_Modelo;
import com.example.tacnafdbusiness.modelo.Repartidor_Modelo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface CRUDRepartidores {

    interface View{
        void onSaveDeliveryManSuccessful();
        void onSaveDeliveryManFailure();
        void onSearchDeliveryManSuccessful(Repartidor_Modelo Repartidor, Boolean Existe_Repartidor);
        void onSearchDeliveryManFailure();
        void onTakeOutDeliveryManSuccessful();
        void onTakeOutDeliveryManFailure();
        void onListDeliveryMenSuccessful(ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento, Boolean Existe_Repartidor_Establecimiento);
        void onListDeliveryMenFailure();
        void onSearchDeliveryManInfoSuccessful(ArrayList<Repartidor_Modelo> Repartidores);
        void onSearchDeliveryManInfoFailure();
        void onGetEstablishmentInfoSuccessful(String ID_Establecimiento);
    }

    interface Presenter{
        void SaveDeliveryMan(DatabaseReference Database_Reference, RepartidorEstablecimiento_Modelo Repartidor_Establecimiento);
        void SearchDeliveryMan(DatabaseReference Database_Reference, String Correo_Electronico_Repartidor);
        void SearchDeliveryManInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento);
        void TakeOutDeliveryMan(DatabaseReference Database_Reference, String ID_Repartidor_Establecimiento, String ID_Establecimiento);
        void ListDeliveryMen(DatabaseReference Database_Reference, String ID_Establecimiento);
        void GetEstablishmentInfo(Context Contexto);
    }

    interface Interactor{
        void performSaveDeliveryMan(DatabaseReference Database_Reference, RepartidorEstablecimiento_Modelo Repartidor_Establecimiento);
        void performSearchDeliveryMan(DatabaseReference Database_Reference, String Correo_Electronico_Repartidor);
        void performSearchDeliveryManInfo(DatabaseReference Database_Reference, ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento);
        void performTakeOutDeliveryMan(DatabaseReference Database_Reference, String ID_Repartidor_Establecimiento, String ID_Establecimiento);
        void performListDeliveryMen(DatabaseReference Database_Reference, String ID_Establecimiento);
        void performGetEstablishmentInfo(Context Contexto);
    }

    interface onOperationListener{
        void onSuccessSaveDeliveryMan();
        void onFailureSaveDeliveryMan();
        void onSuccessSearchDeliveryMan(Repartidor_Modelo Repartidor, Boolean Existe_Repartidor);
        void onFailureSearchDeliveryMan();
        void onSuccessTakeOutDeliveryMan();
        void onFailureTakeOutDeliveryMan();
        void onSuccessListDeliveryMen(ArrayList<RepartidorEstablecimiento_Modelo> Repartidores_Establecimiento, Boolean Existe_Repartidor_Establecimiento);
        void onFailureListDeliveryMen();
        void onSuccessSearchDeliveryManInfo(ArrayList<Repartidor_Modelo> Repartidores);
        void onFailureSearchDeliveryManInfo();
        void onSuccessGetEstablishmentInfo(String ID_Establecimiento);
    }
}

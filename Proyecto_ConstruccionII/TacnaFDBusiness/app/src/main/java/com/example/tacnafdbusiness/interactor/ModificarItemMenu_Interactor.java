package com.example.tacnafdbusiness.interactor;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.ModificarItemMenu;
import com.example.tacnafdbusiness.modelo.ItemMenu_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class ModificarItemMenu_Interactor implements ModificarItemMenu.Interactor {

    private ModificarItemMenu.onOperationListener mListener;
    private ValueEventListener valueEventListener;

    public ModificarItemMenu_Interactor(ModificarItemMenu.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performUpdateItemMenuData(DatabaseReference Database_Reference, ItemMenu_Modelo Item_Menu) {

        Database_Reference.child(Item_Menu.getID_Item_Menu()).setValue(Item_Menu).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    mListener.onSuccessUpdateItemMenuData();
                }
                else
                {
                    mListener.onFailureUpdateItemMenuData();
                }
            }
        });

    }

    @Override
    public void performUpdateItemMenuImage(StorageReference Storage_Reference, final DatabaseReference Database_Reference, final String Url_Imagen_Actual, String ID_Establecimiento, final String ID_Item_Menu, Uri Imagen_Uri) {

        final StorageReference filePath = Storage_Reference.child(ID_Establecimiento).child("ItemMenu").child(Imagen_Uri.getLastPathSegment());

        filePath.putFile(Imagen_Uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                mListener.onFailureUpdateItemMenuImage();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {

                        Database_Reference.child(ID_Item_Menu).child("url_Imagen").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    mListener.onSuccessUpdateItemMenuImage(uri.toString());
                                }
                                else
                                {
                                    mListener.onFailureUpdateItemMenuImage();
                                }
                            }
                        });

                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(Url_Imagen_Actual);
                        reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                    }
                });

            }
        });

    }

    @Override
    public void performGetItemMenuData(final DatabaseReference Database_Reference, final String ID_Item_Menu) {

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ItemMenu_Modelo Item_Menu = snapshot.getValue(ItemMenu_Modelo.class);
                mListener.onSuccessGetItemMenuData(Item_Menu);

                Database_Reference.child(ID_Item_Menu).removeEventListener(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetItemMenuData();
            }
        };
        Database_Reference.child(ID_Item_Menu).addValueEventListener(valueEventListener);
    }
}

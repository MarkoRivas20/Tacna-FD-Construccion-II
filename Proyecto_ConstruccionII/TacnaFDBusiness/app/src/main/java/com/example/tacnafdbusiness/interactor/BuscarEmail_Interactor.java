package com.example.tacnafdbusiness.interactor;

import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.BuscarEmail;
import com.example.tacnafdbusiness.interfaces.Login;
import com.example.tacnafdbusiness.modelo.Usuario_Modelo;
import com.example.tacnafdbusiness.presentador.BuscarEmail_Presentador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BuscarEmail_Interactor implements BuscarEmail.Interactor {

    Session Sesion;

    private String Correo = "no.reply.tacnafyd@gmail.com";
    private String Clave  = "Tacna2018.";

    private BuscarEmail.onOperationListener mListener;

    public BuscarEmail_Interactor(BuscarEmail.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performSearchEmail(DatabaseReference reference, final String correo_electronico, final int codigo) {

        Query query=reference.orderByChild("correo_Electronico").equalTo(correo_electronico);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean booleano = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    booleano = true;

                    performSendEmail(correo_electronico,codigo);

                    mListener.onSuccess();

                }

                if(!booleano){
                    mListener.onNotFoundEMail();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailure();
            }
        });

    }

    @Override
    public void performSendEmail(String correo_electronico, int codigo) {



        String Mensaje = "Recientemente ha solicitado restablecer la contraseña de la cuenta asociada con esta dirección de correo electrónico. " +
                "\n Introduzca este código en página de restablecimiento de contraseñas. \n " + codigo;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        try{

            Sesion = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Correo, Clave);
                }
            });

            if (Sesion != null)
            {
                Message message = new MimeMessage(Sesion);
                message.setFrom(new InternetAddress(Correo));
                message.setSubject("Recuperación de contraseñas");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo_electronico));
                message.setContent(Mensaje,"text/html; charset=utf-8");

                Transport.send(message);

            }
            else
            {

            }



        }catch (Exception e){
            mListener.onFailure();
            Log.e("Error", e.toString());
        }

    }
}

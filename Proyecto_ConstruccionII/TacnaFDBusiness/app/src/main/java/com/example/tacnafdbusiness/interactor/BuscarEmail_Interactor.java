package com.example.tacnafdbusiness.interactor;

import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tacnafdbusiness.interfaces.BuscarEmail;
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
    private String Clave  = "Aqui_Contrasena";

    private BuscarEmail.onOperationListener mListener;

    public BuscarEmail_Interactor(BuscarEmail.onOperationListener mListener) {
        this.mListener = mListener;
    }

    /*Buscar si existe un usuario con el correo electronico enviado*/

    @Override
    public void performSearchEmail(DatabaseReference Database_Reference, final String Correo_Electronico, final int Codigo) {

        Query query=Database_Reference.orderByChild("correo_Electronico").equalTo(Correo_Electronico);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean booleano = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    booleano = true;

                    performSendEmail(Correo_Electronico,Codigo);

                    mListener.onSuccess();

                }

                if(!booleano)
                {
                    mListener.onNotFoundEMail();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailure();
            }
        });

    }

    /*Manda el codigo de recuperacion al correo electronico*/

    @Override
    public void performSendEmail(String Correo_Electronico, int Codigo) {

        String Mensaje = "Recientemente ha solicitado restablecer la contraseña de la cuenta asociada con esta dirección de correo electrónico. " +
                "\n Introduzca este código en página de restablecimiento de contraseñas. \n " + Codigo;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /*configuraciones para el envio del correo*/

        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        /*iniciar sesion con el correo de la aplicacion*/

        try{

            Sesion = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Correo, Clave);
                }
            });

            /*Aqui se envia el correo*/

            if (Sesion != null)
            {
                Message message = new MimeMessage(Sesion);
                message.setFrom(new InternetAddress(Correo));
                message.setSubject("Recuperación de contraseñas");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Correo_Electronico));
                message.setContent(Mensaje,"text/html; charset=utf-8");

                Transport.send(message);

            }



        }catch (Exception e){
            mListener.onFailure();
            Log.e("Error", e.toString());
        }

    }
}

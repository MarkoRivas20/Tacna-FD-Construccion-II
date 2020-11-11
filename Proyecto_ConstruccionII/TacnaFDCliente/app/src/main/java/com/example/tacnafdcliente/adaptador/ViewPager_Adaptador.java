package com.example.tacnafdcliente.adaptador;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

public class ViewPager_Adaptador extends PagerAdapter {

    private Context Contexto;
    private String[] Imagenes_Urls;

    public ViewPager_Adaptador(Context Contexto, String[] Imagenes_Urls){
        this.Contexto = Contexto;
        this.Imagenes_Urls = Imagenes_Urls;
    }

    @Override
    public int getCount() {
        return Imagenes_Urls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(Contexto);
        Picasso.get().load(Imagenes_Urls[position]).fit().centerCrop().into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

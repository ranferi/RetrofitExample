package com.ranferi.ssrsi.helper;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.model.Imagene;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

import static com.ranferi.ssrsi.api.APIUrl.BASE_URL;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private Integer[] images = {
            R.drawable.bar_generic,
            R.drawable.restaurant_generic
    };
    private List<Imagene> mImagenes;

    public ViewPagerAdapter(Context context, List<Imagene> c) {
        this.context = context;
        mImagenes = c;
    }

    @Override
    public int getCount() {
        if ((mImagenes == null || mImagenes.isEmpty())) {
            return images.length;
        } else {
            return mImagenes.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        String[] imagesURL = new String[10];
        if ((mImagenes == null || mImagenes.isEmpty())) {
            imageView.setImageResource(images[position]);
        } else {
            for (int i = 0; i < mImagenes.size(); i++) {
                imagesURL[i] = BASE_URL + "sitios/img/" + mImagenes.get(i).getImagen();
            }
            Picasso.get()
                    .load(imagesURL[position])
                    .fit()
                    .into(imageView);
        }

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}

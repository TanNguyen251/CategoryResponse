package com.example.categoryresponse.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.categoryresponse.R
import com.example.categoryresponse.configs.EndpointsConfig
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_image_adapter.view.*

class ImagePagerAdapter(var mContext: Context, var imageList: ArrayList<String>): PagerAdapter() {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var imageView = LayoutInflater.from(mContext).inflate(R.layout.category_image_adapter,container,false)
        Picasso.get().load(EndpointsConfig.getImageUrl(imageList[position])).into(imageView.category_image_show)
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
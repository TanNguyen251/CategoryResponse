package com.example.categoryresponse.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.categoryresponse.fragments.SubCategoryFragment
import com.example.categoryresponse.models.SubCategory

class SubCategoryPagerAdapter(var fm: FragmentManager): FragmentPagerAdapter(fm) {
    private var mFragmentList: ArrayList<Fragment> = ArrayList()
    private var mTitleList: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return mTitleList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }

    fun addFragment(subCategory: SubCategory){
        mFragmentList.add(SubCategoryFragment.newInstance(subCategory.catId,subCategory.subId))
        mTitleList.add(subCategory.subName)
    }
}
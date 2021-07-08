package com.example.categoryresponse.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.activities.ProductDetailActivity
import com.example.categoryresponse.adapters.ProductAdapter
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.models.Product
import com.example.categoryresponse.models.ProductResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_sub_category.*
import kotlinx.android.synthetic.main.fragment_sub_category.view.*
import org.json.JSONObject


class SubCategoryFragment : Fragment(), ProductAdapter.OnAdapterListener {
    private var catId: Int? = null
    private var subId: Int? = null
    private var mList: ArrayList<Product> = ArrayList()
    private var productList: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            catId = it.getInt(KeyConfig.CATEGORY_ID)
            subId = it.getInt(KeyConfig.SUBCATEGORY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_sub_category, container, false)
        init(view)
        return view
    }

    private fun init(view: View){
        var jsonObject = JSONObject()
        var requestQueue = Volley.newRequestQueue(this.context)
        var request = JsonObjectRequest(
            Request.Method.GET,
            EndpointsConfig.getProductUrl(),
            jsonObject,
            {
                mList.clear()
                productList.clear()
                var gson = Gson()
                var productResponse = gson.fromJson(it.toString(), ProductResponse::class.java)
                productList = productResponse.data as ArrayList<Product>
                for (i in productList){
                    if (i.subId == this.subId){
                        mList.add(i)
                    }
                }
                var productAdapter = ProductAdapter(this.requireContext(), mList)
                productAdapter.setOnAdapterListener(this)
                fragment_sub_category_recycler_view.adapter = productAdapter
                fragment_sub_category_recycler_view.layoutManager = LinearLayoutManager(this.requireContext())
            },
            {

            }
        )
        requestQueue.add(request)
    }

    companion object {

        @JvmStatic
        fun newInstance(catId: Int, subId:Int) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(KeyConfig.CATEGORY_ID, catId)
                    putInt(KeyConfig.SUBCATEGORY_ID,subId)
                }
            }
    }

    override fun onItemClick(product: Product) {
        var intent = Intent(activity, ProductDetailActivity::class.java)
        intent.putExtra(KeyConfig.PRODUCT, product)
        startActivity(intent)
    }
}
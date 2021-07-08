package com.example.categoryresponse.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.activities.AddressActivity
import com.example.categoryresponse.activities.CheckoutActivity
import com.example.categoryresponse.activities.PaymentActivity
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.models.Address
import kotlinx.android.synthetic.main.row_address_adapter.view.*

class AddressAdapter(var mContext: Context, var mList: ArrayList<Address>): RecyclerView.Adapter<AddressAdapter.MyAddressViewHolder>() {
    inner class MyAddressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(address: Address){
            itemView.row_address_type.text = address.type
            itemView.row_address_house.text = "${address.houseNo} "
            itemView.row_address_street.text = "${address.streetName},"
            itemView.row_address_city.text = "${address.city}, "
            itemView.row_address_pincode.text = address.pincode.toString()
            itemView.row_address_button_remove.setOnClickListener{
                var requestQueue = Volley.newRequestQueue(mContext)
                var request = JsonObjectRequest(
                    Request.Method.DELETE,
                    EndpointsConfig.getAddressDeleteUrl(address._id),
                    null,
                    {
                        Log.d("abcd","Address delete successfully")
                    },
                    {
                        Log.d("abcd","Address failed to delete")
                    }
                )
                requestQueue.add(request)
                (mContext as AddressActivity).finish()
                (mContext as AddressActivity).overridePendingTransition(0,0)
                mContext.startActivity(Intent(mContext, AddressActivity::class.java))
                (mContext as AddressActivity).overridePendingTransition(0,0)
            }
            itemView.setOnClickListener{
                var editor = mContext.getSharedPreferences(KeyConfig.MY_ADDRESS, Context.MODE_PRIVATE).edit()
                editor.putString(KeyConfig.ADDRESS_HOUSE, address.houseNo)
                editor.putString(KeyConfig.ADDRESS_STREET, address.streetName)
                editor.putString(KeyConfig.ADDRESS_CITY, address.city)
                editor.putInt(KeyConfig.ADDRESS_ZIP, address.pincode)
                editor.putString(KeyConfig.ADDRESS_USER, address.userId)
                editor.putString(KeyConfig.ADDRESS_TYPE, address.type)
                editor.putString(KeyConfig.ADDRESS_ID, address._id)
                editor.commit()
                mContext.startActivity(Intent(mContext, PaymentActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAddressViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_address_adapter, parent, false)
        return MyAddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAddressViewHolder, position: Int) {
        var address = mList[position]
        holder.bind(address)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
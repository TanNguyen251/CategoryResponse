package com.example.categoryresponse.databases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.categoryresponse.models.CheckoutProduct
import com.example.categoryresponse.models.Product

class DBHelper(var mContext: Context): SQLiteOpenHelper(mContext, DB_NAME, null, DB_VERSION) {
    companion object{
        const val DB_NAME = "product_db"
        const val DB_VERSION = 3
        const val TABLE_PRODUCT = "product"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_PRICE = "price"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_COUNT = "count"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = "create table $TABLE_PRODUCT ($COLUMN_ID char(50), $COLUMN_NAME char(50), $COLUMN_IMAGE char(50), $COLUMN_PRICE float, $COLUMN_MRP float, $COLUMN_COUNT int)"
        db?.execSQL(createTable)
        Log.d("abcd","Product Table create successfully")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var dropTable = "drop table $TABLE_PRODUCT"
        db?.execSQL(dropTable)
        onCreate(db)
        Log.d("abcd","Product Table update successfully")
    }

    fun addProduct(product: Product){
        var db = writableDatabase
        var contentValues = ContentValues()
        var count = containsProduct(product._id)
        if (count==0) {
            contentValues.put(COLUMN_ID, product._id)
            contentValues.put(COLUMN_NAME, product.productName)
            contentValues.put(COLUMN_IMAGE, product.image)
            contentValues.put(COLUMN_PRICE, product.price)
            contentValues.put(COLUMN_MRP, product.mrp)
            contentValues.put(COLUMN_COUNT, 1)
            db.insert(TABLE_PRODUCT, null, contentValues)
        } else {
            increaseCountProduct(product._id, count)
        }

        Log.d("abcd","${product.productName} is successfully added")
    }
    fun increaseCountProduct(id: String, count: Int){
        var db = writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COLUMN_COUNT, count + 1)
        var whereClause = "$COLUMN_ID = ?"
        var whereArg = arrayOf(id)
        db.update(TABLE_PRODUCT, contentValues, whereClause, whereArg)
    }

    fun decreaseCountProduct(id: String, count: Int){
        if( (count-1)==0){
            deleteProduct(id)
        } else {
            var db = writableDatabase
            var contentValues = ContentValues()
            contentValues.put(COLUMN_COUNT, count - 1)
            var whereClause = "$COLUMN_ID = ?"
            var whereArg = arrayOf(id)
            db.update(TABLE_PRODUCT, contentValues, whereClause, whereArg)
        }
    }

    fun deleteProduct(id: String){
        var db = writableDatabase
        var whereClause = "$COLUMN_ID = ?"
        var whereArg = arrayOf(id)
        db.delete(TABLE_PRODUCT, whereClause, whereArg)
        Log.d("abcd", "Product is successfully deleted")
    }

    fun deleteAllProduct(){
        var db = writableDatabase
        db.delete(TABLE_PRODUCT, null, null)
        Log.d("abcd","All products are removed")
    }

    fun containsProduct(id: String): Int {
        var db = readableDatabase
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_COUNT
        )
        var cursor = db.query(TABLE_PRODUCT, columns, null, null, null, null, null)
        if (cursor!=null && cursor.moveToFirst()){
            do {
                var checkId = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                if (checkId == id) {
                    return cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT))
                }
            } while (cursor.moveToNext())
        }
        return 0
    }

    fun readProduct():ArrayList<CheckoutProduct>{
        var db = readableDatabase
        var productList : ArrayList<CheckoutProduct> = ArrayList()
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_IMAGE,
            COLUMN_PRICE,
            COLUMN_MRP,
            COLUMN_COUNT
        )
        var cursor = db.query(TABLE_PRODUCT, columns, null, null, null, null, null)
        if (cursor!=null && cursor.moveToFirst()){
            do {
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var price = cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE))
                var mrp = cursor.getFloat(cursor.getColumnIndex(COLUMN_MRP))
                var count = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT))
                var checkoutProduct = CheckoutProduct(id, name, image, price, mrp, count )
                productList.add(checkoutProduct)
            } while (cursor.moveToNext())
        }
        return productList
    }

}
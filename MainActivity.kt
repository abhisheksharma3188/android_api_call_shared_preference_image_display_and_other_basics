package com.example.kotlintest2

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    var num1:Int=0
    var num2:Int=0
    var resultNum:Int=0
    lateinit var n1EditText : EditText
    lateinit var n2EditText : EditText
    lateinit var resTextView : TextView
    lateinit var addButton : Button
    lateinit var otherActivityButton : Button
    lateinit var externalLinkButton : Button
    lateinit var hitApiButton : Button
    lateinit var apiResponseTextView : TextView
    lateinit var saveToSharedPrefernceButton: Button
    lateinit var sharedPrefenceTextView: TextView
    lateinit var imageView: ImageView
    var imageUrl: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        n1EditText =findViewById(R.id.number1EditText)
        n2EditText=findViewById(R.id.number2EditText)
        resTextView=findViewById(R.id.resultTextView)
        addButton= findViewById(R.id.addButton)
        otherActivityButton=findViewById(R.id.otherActivityButton)
        externalLinkButton=findViewById(R.id.externalLinkButton)
        hitApiButton=findViewById(R.id.hitApiButton)
        apiResponseTextView=findViewById(R.id.apiResponseTextView)
        saveToSharedPrefernceButton=findViewById(R.id.saveToSharedPreferenceButton)
        sharedPrefenceTextView=findViewById(R.id.sharedPrefenceTextView)
        imageView=findViewById(R.id.imageView)

        addButton.setOnClickListener {
            addFunction()
        }


        otherActivityButton.setOnClickListener {
            var intent = Intent(this,OtherActivity::class.java)
            intent.putExtra("DataFromMainActivity","This is data from Main Activity")
            startActivity(intent)
        }


        externalLinkButton.setOnClickListener {
            var openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data= Uri.parse("https://www.highdip.com/")
            startActivity(openURL)
        }


        hitApiButton.setOnClickListener {
            try {
                num1 = n1EditText.text.toString().toInt()
                num2 = n2EditText.text.toString().toInt()
            }
            catch(e: Exception){
                num1=0
                num2=0
            }
            sendPostRequest(num1,num2)
        }

        saveToSharedPrefernceButton.setOnClickListener {
            var data: String = n1EditText.text.toString()
            sharedPrefenceTextView.text=data
            var sharedPrefernces=getSharedPreferences("mySharedPrefernce", Context.MODE_PRIVATE)
            var editor=sharedPrefernces.edit()
            editor.apply {
                putString("mySharedPreferenceDataKey",data)
            }.apply()
            Toast.makeText(this,"Data Saved Successfully",Toast.LENGTH_SHORT).show()
        }

        imageUrl="https://highdip.com/android-test-api/testImage1.jpg"
        Glide.with(this).load(imageUrl).into(imageView)

        loadDataFromSharedPreference()


    }
    fun addFunction(){
        try {
            num1 = n1EditText.text.toString().toInt()
            num2 = n2EditText.text.toString().toInt()
        }
        catch(e: Exception){
            num1=0
            num2=0
        }
        resultNum=num1+num2
        resTextView.text=resultNum.toString()
        Toast.makeText(this,"Addition Successful",Toast.LENGTH_SHORT).show()
    }

    fun sendPostRequest(n1:Int,n2:Int){
        //resultNum=n1-n2
        apiResponseTextView.text= ""
        /*Code ready to be written*/
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.highdip.com/android-test-api/api.php"

        // making a string request to update our data and
        // passing method as PUT. to update our data.
        val request: StringRequest =
            object : StringRequest(Request.Method.POST, url, object : Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    // inside on response method we are
                    // setting our edit text to empty.
                    apiResponseTextView.setText("")

                    try {
                        // on below line we are extracting data from our json object
                        // and passing our response to our json object.
                        val jsonArray = JSONArray(response)
                        apiResponseTextView.setText(jsonArray.getJSONObject(0).getString("name"))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    // displaying toast message on response failure.
                    Log.e("tag", "error is " + error!!.message)
                    Toast.makeText(this@MainActivity, "Fail to update data..", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
                override fun getParams(): Map<String, String>? {
                    // below line we are creating a map for storing
                    // our values in key and value pair.
                    val params: MutableMap<String, String> = HashMap()
                    // on below line we are passing our key
                    // and value pair to our parameters.
                    params["number1"] = n1.toString()
                    params["number2"] = n2.toString()
                    // at last we are
                    // returning our params.
                    return params
                }
            }
        // below line is to make
        // a json object request.
        queue.add(request)
    }

    fun loadDataFromSharedPreference(){
        var sharedPrefernces=getSharedPreferences("mySharedPrefernce", Context.MODE_PRIVATE)
        var savedData:String=sharedPrefernces.getString("mySharedPreferenceDataKey",null).toString()
        sharedPrefenceTextView.text=savedData
    }
}

package com.example.testphpmysql0801

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.FrameStats
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testphpmysql0801.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.reflect.KClass


class MainActivity : AppCompatActivity() {

    //edittext 및 spinner
    private var editTextArtistName: EditText? = null
    private var spinnerGenre: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater) // <------ 추가1
        setContentView(binding.root)


        //XML에서 가져 오기
        editTextArtistName = findViewById<EditText>(R.id.editTextArtistName)
        spinnerGenre = findViewById<Spinner>(R.id.spinnerGenre)

        //버튼에 클릭 리스너 추가
    //    findViewById(R.id.buttonAddArtist).setOnClickListener { addArtist() }
        buttonAddArtist.setOnClickListener { addArtist() }

            //Toast.makeText(applicationContext, "addArtist()", Toast.LENGTH_LONG).show()

        // 두 번째 버튼 클릭
        // 모든 아티스트를 표시하기 위해 활동 열기
        //이 활동이 없으므로 오류가 발생하므로 앱을 실행하려면이 부분을 제거하십시오.
//        findViewById<Spinner>(R.id.buttonViewArtist).setOnClickListener {
        buttonViewArtist.setOnClickListener {
            val intent = Intent(this, ViewArtistsActivity::class.java)
            startActivity(intent)
            Log.d("xxyy","로그출력 => intent = $intent")
        }
    }

    //데이터베이스에 새 레코드 추가
    private fun addArtist() {
        //레코드 값 가져 오기

        val name = editTextArtistName?.text.toString()
        val genre = spinnerGenre?.selectedItem.toString()
        //Toast.makeText(applicationContext, name, Toast.LENGTH_LONG).show()
        /*-----------------------------------------------------------------------
        [--안드로이드 코틀린으로 Volley 라이브러리 사용방법!--]
        Http 라이브러리인 Volley 라이브러리를 사용하는 방법에 대해 알아볼텐데요.
        그 중 StringRequest를 사용하는 방법에 대해 알아보겠습니다.

        먼저 build.radle(Module:app) 의 dependencies { }
        implementation 'com.android.volley:volley:1.1.0'

        사용방법은 어렵지 않습니다.
        Response.Listener 와 Response.ErrorListener 만 사용하신다면,
        object : 를 빼주셔도 됩니다.
        근데 Request 할 때 키와 값을 같이 보내고 그에 따른 Response를 받아야 한다면
        object : 를 넣어줘야 합니다.


        val request = object : StringRequest(Request.Method.POST, "url",
            Response.Listener {
                //TODO
            }, Response.ErrorListener {
                //TODO
            }) {
            // request 시 key, value 보낼 때
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                return params
            }
        }
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
         ------------------------------------------------------------------------*/



        //발리 문자열 요청 생성
        //Toast.makeText(applicationContext, EndPoints.URL_ADD_ARTIST, Toast.LENGTH_LONG).show()
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_ADD_ARTIST,
            Response.Listener<String> { response ->
                // try {}에서 실행한 코드중에오류가 발생하면 catch{}에서 행당 에러에 대한 오류 처리를 합니다.
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, "1111111111", Toast.LENGTH_LONG).show()
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    Toast.makeText(applicationContext, "222222222", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
                        //Toast.makeText(applicationContext, "33333333333", Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                    }
            }) {



            //@Throws(vararg exceptionClasses: KClass<out Throwable>)
            //jvm메소드로 컴파일 될 때 함수에서 선언해야하는 예외를 나타냅니다.

            //      vararg val exceptionClasses: Array<out KClass<out Throwable>>
            //      함수가 던질 수있는 검사 된 예외 클래스 목록.


            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                Log.d("xxyy","로그출력 => method = Log.d")
                val params = HashMap<String, String>()
                params.put("name", name)
                params.put("genre", genre)
                Log.d("xxyy","로그출력 => params = $params")
                return params
            }

        }
        Log.d("xxyy","로그출력 => stringRequest = $stringRequest")
        //대기열에 요청 추가
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}
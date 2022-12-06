package kr.ac.kumoh.s20190703.prof.w1301volleywithrecyclerview

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class SongViewModel(application: Application) : AndroidViewModel(application) {
    data class Song(var mcode: Int, var mname :String, var mgenre :String, var m_sname : String) //내 서버 네임으로 변경했음
    //코틀린에서 class 는 스트럭스라고 생각하면됨 default로 public으로 지정하면된다!

    companion object {
        const val QUEUE_TAG = "SongVolleyRequest"
    }

    private val songs = ArrayList<Song>()
    private val _list = MutableLiveData<ArrayList<Song>>()
    val list : LiveData<ArrayList<Song>>
        get() = _list

    private val queue : RequestQueue

    init {
        _list.value = songs
        queue = Volley.newRequestQueue(getApplication()) //이 getApplication을 위해 발리를 썼다.
    }

    fun requestSong() {
        val request = JsonArrayRequest(

            Request.Method.GET,
            "https://databasemf.run.goorm.io/",
            null,
            {
//                Toast.makeText(getApplication(),
//            it.toString(),
//            Toast.LENGTH_LONG).show()
            songs.clear()
                parseJson(it)
                _list.value = songs
            },//성공햇을때
            {
                Toast.makeText(getApplication(),
                    it.toString(),
                    Toast.LENGTH_LONG).show()

            } //실패햇을때 함수
        ) //중요 JSONArray냐 jsonArray냐 발리니까 지금은 소문자 json
        request.tag = QUEUE_TAG
        queue.add(request)
    }

    private fun parseJson(items: JSONArray){
        for (i in 0 until items.length()) {
            val item = items[i] as JSONObject
            val mcode = item.getInt("mcode")
            val mname = item.getString("mname")
            val mgenre = item.getString("mgenre")
            val m_sname = item.getString("m_sname")

            songs.add(Song(mcode, mname, mgenre , m_sname))
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }
}
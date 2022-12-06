package kr.ac.kumoh.s20190703.prof.w1301volleywithrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.s20190703.prof.w1301volleywithrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var model : SongViewModel
    private val songAdapter = SongAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        model = ViewModelProvider(this)[SongViewModel::class.java]

        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = songAdapter

        model.list.observe(this){
            songAdapter.notifyItemRangeInserted(0,
            songAdapter.itemCount)
        }
        model.requestSong()
    }
    inner class SongAdapter : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txTitle = itemView.findViewById<TextView>(android.R.id.text1)
            val txSinger = itemView.findViewById<TextView>(android.R.id.text2)
            //text1은 타이틀에 넣고 text2에는 가수가 들어가도록 만들어봐라
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_2, //여기 부분만 바꿔주면 된다
           //android를 떼고 simple 부분을 xml로 만들어야되는걸 해야된다
            parent,
            false)
            return ViewHolder((view))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.txTitle.text = model.list.value?.get(position)?.mname //이런식으로
            holder.txSinger.text = model.list.value?.get(position)?.m_sname
        }

        override fun getItemCount() = model.list.value?.size ?:0

        //onbind랑 count는 싹다 바꿔야되는데 onCreateView 함수는 inflate 인자만 바꾸면 된다
    }
}
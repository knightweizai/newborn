package com.zaozhuang.newborn.ui.activity

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxj.xpopup.XPopup
import com.squareup.okhttp.ConnectionPool
import com.zaozhuang.newborn.R
import com.zaozhuang.newborn.adapter.BabyItemAdapter
import com.zaozhuang.newborn.adapter.VideoItemAdapter
import com.zaozhuang.newborn.data.InputEntity
import com.zaozhuang.newborn.db.entity.Baby
import com.zaozhuang.newborn.popupwindow.SelBabyPopupWindow
import com.zaozhuang.newborn.ui.base.BaseActivity

class VideoListActivity : BaseActivity() {


    var mBackView: View? = null
    var mTitleView: TextView? = null
    var recyclerView: RecyclerView? = null

    var adapter: VideoItemAdapter? = null

    var list: ArrayList<InputEntity>? = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.activity_video_list
    }

    override fun initView() {

        mBackView = findViewById(R.id.common_title_bar_back_image)
        mTitleView = findViewById(R.id.common_title_bar_title_text)
        recyclerView = findViewById(R.id.recyclerView)

        mTitleView?.text = "育儿视频"
        adapter = VideoItemAdapter(
            this, list
        ) { position: Int, entity: InputEntity ->
            JUMPER.videoPlayer(entity.key, entity.value).startActivity(this)
        }

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = adapter

        genData()
    }

    private fun genData() {
        list?.add(InputEntity("1新生儿臀部护理", R.raw.xinshengrentunbuhuli.toString()))
        list?.add(InputEntity("2新生儿喂药", R.raw.xinshengrenweiyao.toString()))
        list?.add(InputEntity("3新生儿睡眠", R.raw.xinshengrenshuimian.toString()))
        list?.add(InputEntity("4新生儿呛奶", R.raw.xinshengrenqiangnai.toString()))
        list?.add(InputEntity("5新生儿沐浴", R.raw.xinshengermuyu.toString()))
        list?.add(InputEntity("6新生儿呕吐的观察", R.raw.xinshengeroutuguancha.toString()))
        list?.add(InputEntity("7新生儿拍嗝", R.raw.xinshengerpaige.toString()))
        list?.add(InputEntity("8新生儿脐部护理", R.raw.xinshengerjibuhuli.toString()))
        list?.add(InputEntity("9新生儿面色观察", R.raw.xinshengermianseguancha.toString()))
        list?.add(InputEntity("10新生儿呼吸的观察", R.raw.huxideguangcha.toString()))
        list?.add(InputEntity("11新生儿腹胀的观察和护理", R.raw.fuzhangdeguangchahechuli.toString()))
        list?.add(InputEntity("12新生儿抚触", R.raw.xinshengerfuchu.toString()))
        list?.add(InputEntity("13更换衣物", R.raw.genghuanyiwu.toString()))
        list?.add(InputEntity("14奶粉配置", R.raw.naifenpeizhi.toString()))
        list?.add(InputEntity("15奶具的消毒", R.raw.naijudexiaodu.toString()))
        list?.add(InputEntity("16袋鼠式护理", R.raw.daishushihuli.toString()))
        list?.add(InputEntity("17母乳喂养", R.raw.muyuweiyang.toString()))
        list?.add(InputEntity("18新生儿大、小便观察", R.raw.daxiaobianguancha.toString()))
        list?.add(InputEntity("19m3u8", R.raw.nihaoqingyang.toString()))

        adapter?.notifyDataSetChanged()


    }


    override fun initListener() {
        mBackView!!.setOnClickListener { v: View? -> finish() }
    }

}
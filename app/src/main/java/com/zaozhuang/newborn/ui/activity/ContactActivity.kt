package com.zaozhuang.newborn.ui.activity

import android.view.View
import android.widget.TextView
import com.zaozhuang.newborn.R
import com.zaozhuang.newborn.ui.base.BaseActivity

class ContactActivity : BaseActivity() {


    var mBackView: View? = null
    var mTitleView: TextView? = null
    var tv: TextView? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_contact
    }

    override fun initView() {

        mBackView = findViewById(R.id.common_title_bar_back_image)
        mTitleView = findViewById(R.id.common_title_bar_title_text)
        tv = findViewById(R.id.tv)

        tv?.text = genData()
        mTitleView?.text = "向下转诊"
    }

    private fun genData() : String {

        return   """
           枣庄市薛城区陶庄镇中心卫生院 枣庄市薛城区陶庄镇顺兴路东段 0632-4826166
           
           枣庄市薛城区周营镇中心卫生院 枣庄市薛城区周营镇政府驻地 0632--4711155
           
           枣庄市薛城区邹坞镇中心卫生院 枣庄市薛城区邹坞镇政府驻地 0632-4521120
           
           枣庄市薛城区常庄镇中心卫生院 枣庄市薛城区常庄镇金河驻地 0632-4487681
           
           枣庄市薛城区沙沟镇中心卫生院 枣庄市薛城区沙沟镇政府驻地 0632-4919898
           
           枣庄市薛城区兴仁社区卫生服务中心 枣庄高新区德圣路609号 15606328832
           
           枣庄市薛城区兴城社区卫生服务中心 枣庄高新区长白山路1855号 13963224060
           
           枣庄市市中区齐村镇卫生院 枣庄市市中区齐村镇政府驻地 0632-8023187
           
           枣庄市市中区税郭镇中心卫生院 枣庄市市中区税郭镇政府驻地 0632-3511120
             0632-3511323
             
           枣庄市市中区西王庄镇卫生院 枣庄市市中区西王庄镇政府驻地 0632-8023322
           
           枣庄市市中区永安镇中心卫生院 枣庄市市中区衡山路16号 0632-3090083
           
           枣庄市市中区孟庄镇卫生院 枣庄市市中区孟庄镇政府驻地 0632-7555719
           
           枣庄市市中区光明路街道社区卫生服务中心 枣庄市市中区解放南路29号 0632-3613155
             0632-3601869
             
           枣庄市市中区中心街街道社区卫生服务中心 枣庄市市中区解放路199号 13256329787
           
           枣庄市市中区龙山路街道社区卫生服务中心 枣庄市市中区青檀路122号 0632-3066992
           
           枣庄市市中区垎塔埠光明路街道社区卫生服务中心  枣庄市市中区垎塔埠街道电厂馨苑小区61号楼往南50米 0632-8076295
           
           枣庄市市中区文化路街道社区卫生服务中心 枣庄市市中区龙兴路17号 0632-3695107
           
           枣庄市市中区矿区街道社区卫生服务中心 枣庄市市中区长青路5号 0632-7921253
        """.trimIndent()
    }

    override fun initListener() {
        mBackView!!.setOnClickListener { v: View? -> finish() }
    }

}
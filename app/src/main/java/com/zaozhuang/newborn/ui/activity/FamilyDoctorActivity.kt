package com.zaozhuang.newborn.ui.activity

import android.view.View
import android.widget.TextView
import com.zaozhuang.newborn.R
import com.zaozhuang.newborn.ui.base.BaseActivity

class FamilyDoctorActivity : BaseActivity() {


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
        mTitleView?.text = "家庭医生"
    }

    private fun genData() : String {

        return   """
           衡山路卫生室王强永  15006761070
        """.trimIndent()
    }

    override fun initListener() {
        mBackView!!.setOnClickListener { v: View? -> finish() }
    }

}
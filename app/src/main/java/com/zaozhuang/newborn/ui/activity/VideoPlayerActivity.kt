package com.zaozhuang.newborn.ui.activity

import android.content.res.Configuration
import android.view.View
import android.view.View.GONE
import android.widget.MediaController
import android.widget.TextView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.model.VideoOptionModel
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.zaozhuang.newborn.R
import com.zaozhuang.newborn.consts.JumperParam
import com.zaozhuang.newborn.ui.base.BaseActivity
import com.zaozhuang.newborn.util.FileUtils
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.io.FileOutputStream


class VideoPlayerActivity : BaseActivity() {


    var mBackView: View? = null
    var mTitleView: TextView? = null
    lateinit var player: StandardGSYVideoPlayer

    var path: String? = null
    var title: String? = null

    lateinit var mediaController: MediaController

    override fun getLayoutId(): Int {
        return R.layout.activity_video_player
    }

    override fun initView() {

        mBackView = findViewById(R.id.common_title_bar_back_image)
        mTitleView = findViewById(R.id.common_title_bar_title_text)
        player = findViewById(R.id.player)

        title = intent.getStringExtra(JumperParam.VIDEO_TITLE)
        path = intent.getStringExtra(JumperParam.VIDEO_PATH)

        mTitleView?.text = title


        initVideoView()


    }


    private fun initVideoView() {
//        player.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", true, title)

//        val url = "android.resource://$packageName/$path"
//        GSYVideoManager.instance().enableRawPlay(this)

        val url = "https://liverecord.ccwb.cn/qukan/user/1546848211090974/record/1569373796503947/record.m3u8"

        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption
            .setUrl(url)
            .setVideoTitle(title)
            .build(player)

        player.backButton.visibility = GONE

        player.fullscreenButton.setOnClickListener { v ->
            player.startWindowFullscreen(
                v?.context,
                false,
                true
            )
        }
    }

    fun save() {
        val `in` = resources.openRawResource(R.raw.xinshengrenshuimian)
        val out = FileOutputStream(FileUtils.getBaseSdDir() + "xinshengrenshuimian.mp4")
        val buff = ByteArray(1024)
        var read = 0
        try {
            while (`in`.read(buff).also { read = it } > 0) {
                out.write(buff, 0, read)
            }
        } finally {
            `in`.close()
            out.close()
        }

    }

    override fun initListener() {
        mBackView!!.setOnClickListener {  finish() }
    }

    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

}
package com.app.msgalarmclock.presentation.ui.ring

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.app.msgalarmclock.App
import com.app.msgalarmclock.R
import com.app.msgalarmclock.presentation.base.BaseActivity
import com.app.msgalarmclock.presentation.extension.injectViewModel
import com.app.msgalarmclock.presentation.extension.showToast
import com.app.msgalarmclock.presentation.model.AlarmModel
import com.app.msgalarmclock.presentation.receiver.AlarmBroadcastReceiver
import kotlinx.android.synthetic.main.activity_ring.*
import javax.inject.Inject

class RingActivity : BaseActivity() {

    override val layoutId = R.layout.activity_ring

    @Inject
    lateinit var viewModel: RingViewModel

    private lateinit var player: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent?.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = injectViewModel(viewModelFactory)

        intent.getBundleExtra(AlarmBroadcastReceiver.KEY_BUNDLE_ALARM)?.apply {
            (getSerializable(AlarmBroadcastReceiver.KEY_ALARM) as? AlarmModel)?.let {
                viewModel.init(it.id)
            }
        }

        viewModel.alarmInfo.observe(this) {
            textAlarm.text = it.text
            buttonPause.text = getString(R.string.pause_alarm, it.pauseDuration)
        }

        viewModel.notifyContact.observe(this) { phoneNumber ->
            SmsManager.getDefault().sendTextMessage(
                phoneNumber,
                null,
                getString(R.string.sms_text_message),
                null,
                null
            )
            showToast(R.string.sms_send_success)
        }

        viewModel.finish.observe(this) {
            finish()
        }

        containerAlarm.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                super.onTransitionCompleted(motionLayout, currentId)
                viewModel.dismissAlarm()
            }
        })

        buttonPause.setOnClickListener {
            viewModel.pauseAlarm()
        }

        animateAlarm()
        playSound()
    }

    override fun onDestroy() {
        player.stop()
        super.onDestroy()
    }

    private fun animateAlarm() {
        ObjectAnimator.ofFloat(imageAlarm, View.ROTATION, 0f, -10f, 0f, 10f, 0f).apply {
            repeatCount = ValueAnimator.INFINITE
            duration = ANIM_DURATION
            start()
        }
    }

    private fun playSound() {
        player = MediaPlayer.create(this, R.raw.alarm_sound).apply {
            isLooping = true
            start()
        }
    }

    companion object {
        private const val ANIM_DURATION = 400L
    }
}
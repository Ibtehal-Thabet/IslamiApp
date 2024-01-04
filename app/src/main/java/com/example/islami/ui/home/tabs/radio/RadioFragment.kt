package com.example.islami.ui.home.tabs.radio

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.islami.R
import com.example.islami.api.ApiManager
import com.example.islami.api.model.RadioResponse
import com.example.islami.api.model.RadiosItem
import com.example.islami.databinding.FragmentRadioBinding
import com.example.islami.service.ActionPlaying
import com.example.islami.service.PlayService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RadioFragment : Fragment(), ActionPlaying {
    lateinit var viewBinding: FragmentRadioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRadioBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        adapter.isDark =
            resources.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_NO
    }

    override fun onStart() {
        super.onStart()
        startService()
        bindService()
    }

    override fun onStop() {
        super.onStop()
        activity?.unbindService(connection)
    }

    lateinit var adapter: RadioAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private fun initViews() {
        adapter = RadioAdapter(null)
        viewBinding.recyclerView.adapter = adapter

        layoutManager = viewBinding.recyclerView.layoutManager as LinearLayoutManager

        adapter.onItemClickPlay =
            RadioAdapter.OnItemClickListener { position, item ->
                this.item = item
                playClicked()
            }

        adapter.onItemClickPlayNext =
            RadioAdapter.OnItemClickListener { position, item ->
                nextClicked()
            }

        adapter.onItemClickPlayPrevious =
            RadioAdapter.OnItemClickListener { position, item ->
                prevClicked()
            }

        getChannelsFromApi()
    }

    var name = ""
    var item: RadiosItem? = null
    var position = 0
    override fun playClicked() {
        if (!service.mediaPlayer.isPlaying) {
            startRadioService(this.item)

            service.isPlaying = true
            adapter.service.isPlaying = true

            if (adapter.isDark) {
                adapter.itemBinding.play.setImageResource(R.drawable.ic_pause_dark)
            } else {
                adapter.itemBinding.play.setImageResource(R.drawable.ic_pause)
            }

        } else if (service.mediaPlayer.isPlaying) {
            pauseService()

            service.isPlaying = false
            adapter.service.isPlaying = false

            if (adapter.isDark) {
                adapter.itemBinding.play.setImageResource(R.drawable.play_dark)
            } else {
                adapter.itemBinding.play.setImageResource(R.drawable.play)
            }
        }

        service.updateNotification()
    }

    override fun nextClicked() {
        if (layoutManager.findLastCompletelyVisibleItemPosition() < (adapter.itemCount - 1)) {

            position = layoutManager.findLastCompletelyVisibleItemPosition() + 1
            layoutManager.scrollToPosition(position)

            item = adapter.radioList?.get(position)
            name = item?.name ?: ""
            service.name = name

            service.isPlaying = false
            adapter.service.isPlaying = false
            service.updateNotification()
        }
        pauseService()
    }

    override fun prevClicked() {
        if (layoutManager.findLastCompletelyVisibleItemPosition() < (adapter.itemCount - 1) &&
            layoutManager.findLastCompletelyVisibleItemPosition() > 0
        ) {

            position = layoutManager.findLastCompletelyVisibleItemPosition() - 1
            layoutManager.scrollToPosition(position)

            item = adapter.radioList?.get(position)
            name = item?.name ?: ""
            service.name = name

            service.isPlaying = false
            adapter.service.isPlaying = false
            service.updateNotification()
        }
        pauseService()
    }


    lateinit var service: PlayService
    private fun startRadioService(item: RadiosItem?) {
        if (bound && item?.url != null && item.name != null) {
            service.startMediaPlayer(item.url, item.name)
            name = item.name
            service.createNotificationForMediaPlayer(item.name)
        }
    }

    private fun pauseService() {
        if (bound) {
            service.pauseMediaPlayer()
        }
    }

    private fun getChannelsFromApi() {
        Log.e("Api", "get Api")

        ApiManager.getWebService()
            .getRadio()
            .enqueue(object : Callback<RadioResponse> {
                override fun onResponse(
                    call: Call<RadioResponse>,
                    response: Response<RadioResponse>
                ) {
                    Log.e("response", response.body()?.radios.toString())
                    val channels = response.body()?.radios
                    adapter.bindChannel(channels)
                }

                override fun onFailure(call: Call<RadioResponse>, t: Throwable) {
                    Toast.makeText(activity, t.localizedMessage ?: "Error!", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun startService() {
        val intent = Intent(activity, PlayService::class.java)
        activity?.startService(intent)
    }

    private fun bindService() {
        val intent = Intent(activity, PlayService::class.java)
        activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    var bound = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(classNAme: ComponentName?, mBinder: IBinder?) {
            val binder = mBinder as PlayService.MyBinder
            service = binder.getServices()
            adapter.service.isPlaying = service.isPlaying
            bound = true
            service.setCallBack(this@RadioFragment)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            bound = false
        }
    }
}
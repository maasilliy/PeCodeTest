package com.example.pecodetest

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pecodetest.databinding.FragmentCounterBinding
import android.content.Context.NOTIFICATION_SERVICE
import android.R
import android.app.*
import android.graphics.Color
import android.os.Build
import android.provider.Settings.Global.putInt
import androidx.navigation.NavDeepLinkBuilder
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_counter.*


const val ARGUMENT_OBJECT = "object"

class CounterFragment : Fragment() {

    private lateinit var binding: FragmentCounterBinding

    private lateinit var adapter: CounterAdapter // counter adapter

    private lateinit var mainActivity: MainActivity

    private val CHANNEL_ID = "channelID"
    private val CHANNEL_NAME = "channelName"
    private  val NOTIFOCATION_ID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCounterBinding.inflate(layoutInflater, container, false)

        createNotificationChannel()

        mainActivity = (activity as MainActivity?)!!

        adapter = CounterAdapter(requireActivity())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ARGUMENT_OBJECT) }?.apply {
            if (getInt(ARGUMENT_OBJECT) == 1) binding.btnMinus.visibility = View.GONE
            else binding.btnMinus.visibility = View.VISIBLE
            binding.tvCounter.text = getInt(ARGUMENT_OBJECT).toString()
        }

        binding.btnPlus.setOnClickListener {
            arguments?.takeIf { it.containsKey(ARGUMENT_OBJECT) }?.apply {
                mainActivity.viewPager2.setCurrentItem(getItem(1), true)
            }
        }

        binding.btnMinus.setOnClickListener {
            arguments?.takeIf { it.containsKey(ARGUMENT_OBJECT) }?.apply {
                mainActivity.viewPager2.setCurrentItem(getItem(-1), true)
            }
        }

        binding.btnCreateNewNotification.setOnClickListener {
            arguments?.takeIf { it.containsKey(ARGUMENT_OBJECT) }?.apply {

                val intent = Intent(requireContext(), MainActivity::class.java)

                val pendingIntent = TaskStackBuilder.create(requireContext()).run {
                    addNextIntentWithParentStack(intent)
                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                }

                val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                    .setContentTitle("You are create a notification")
                    .setContentText("Notification ${getInt(ARGUMENT_OBJECT)}")
                    .setSmallIcon(R.drawable.ic_delete)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .build()

                val notificationManager = NotificationManagerCompat.from(requireContext())

                notificationManager.notify(NOTIFOCATION_ID, notification)
            }
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun getItem(i: Int): Int {
        return mainActivity.viewPager2.getCurrentItem() + i
    }
}
package com.example.pecodetest

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.pecodetest.databinding.ActivityMainBinding
import com.example.pecodetest.databinding.FragmentCounterBinding

class MainActivity : FragmentActivity() {

    private lateinit var adapter: CounterAdapter // counter adapter

    private lateinit var binding: ActivityMainBinding // view binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CounterAdapter(this)
        binding.viewPager2.adapter = adapter
    }
}
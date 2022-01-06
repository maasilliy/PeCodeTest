package com.example.pecodetest

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class CounterAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 100

    override fun createFragment(position: Int): Fragment {
        val counterFragment = CounterFragment()
        counterFragment.arguments = Bundle().apply {
            putInt(ARGUMENT_OBJECT, position + 1)
        }
        return counterFragment
    }
}
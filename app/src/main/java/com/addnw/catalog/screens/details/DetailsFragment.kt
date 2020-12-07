package com.addnw.catalog.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.addnw.catalog.databinding.FragmentDetailsBinding
import com.addnw.catalog.screens.Civilization
import com.addnw.catalog.screens.CivilizationViewModel
import com.addnw.catalog.screens.Region

class DetailsFragment() : Fragment() {
    companion object {
        const val LOG_KEY = "Details"
        const val PAGES = 3
    }

    private lateinit var civilization: Civilization
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding =  FragmentDetailsBinding.inflate(
                inflater,
                container,
                false
        )

        civilization = ViewModelProvider(this).get(CivilizationViewModel::class.java).getCurrent()

        viewPager = binding.detailsVP
        viewPager.adapter = ScreenSlidePagerAdapter(this, civilization)

        return binding.root
    }

    private inner class ScreenSlidePagerAdapter(val fa: DetailsFragment, val civilization: Civilization) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = DetailsFragment.PAGES

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return DetailsMainFragment(civilization)
                1 -> return DetailsGalleryFragment(civilization)
                2 -> return DetailsPerksFragment(civilization)
            }
            return DetailsMainFragment(civilization)
        }
    }
}
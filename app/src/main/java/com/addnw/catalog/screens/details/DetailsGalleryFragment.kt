package com.addnw.catalog.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.addnw.catalog.R
import com.addnw.catalog.screens.Civilization

class DetailsGalleryFragment(val civilization: Civilization) : Fragment() {
    companion object {
        const val LOG_KEY = "Details Gallery"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_details_galery, container, false)
}
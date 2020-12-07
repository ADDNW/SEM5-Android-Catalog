package com.addnw.catalog.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.addnw.catalog.R
import com.addnw.catalog.databinding.FragmentDetailsGaleryBinding
import com.addnw.catalog.databinding.FragmentDetailsPerksBinding
import com.addnw.catalog.screens.Civilization

class DetailsPerksFragment(val civilization: Civilization) : Fragment() {
    companion object {
        const val LOG_KEY = "Details Perks"
    }

    private lateinit var binding: FragmentDetailsPerksBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsPerksBinding.inflate(
            inflater,
            container,
            false
        )

        binding.apply {
            var text = ""
            for (trait in civilization.traits) {
                text += "• $trait\n"
            }
            perksList.text = text
        }

        return binding.root
    }

}
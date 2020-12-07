package com.addnw.catalog.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.addnw.catalog.R
import com.addnw.catalog.databinding.FragmentCatagogBinding
import com.addnw.catalog.databinding.FragmentDetailsMainBinding
import com.addnw.catalog.databinding.FragmentDetailsPerksBinding
import com.addnw.catalog.screens.Civilization

class DetailsMainFragment(val civilization: Civilization) : Fragment() {
    companion object {
        const val LOG_KEY = "Details Main"
    }

    private lateinit var binding: FragmentDetailsMainBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsMainBinding.inflate(
            inflater,
            container,
            false
        )

        binding.apply {
            val context = requireContext()
            civilizationIcon.setImageDrawable(
                ContextCompat.getDrawable(context, context.resources.getIdentifier(civilization.graphics[Civilization.ICON], "drawable", context.packageName))
            )
            civilizationName.text = civilization.name

            leaderIcon.setImageDrawable(
                ContextCompat.getDrawable(context, context.resources.getIdentifier(civilization.graphics[Civilization.ICON], "drawable", context.packageName))
            )
            leaderName.text = civilization.leader

            perkIcon1.setImageDrawable(
                ContextCompat.getDrawable(context, context.resources.getIdentifier(civilization.graphics[Civilization.ICON], "drawable", context.packageName))
            )
            perkIcon2.setImageDrawable(
                ContextCompat.getDrawable(context, context.resources.getIdentifier(civilization.graphics[Civilization.ICON], "drawable", context.packageName))
            )
        }

        return binding.root
    }
}
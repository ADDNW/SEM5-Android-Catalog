package com.addnw.catalog.screens.details

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.addnw.catalog.R
import com.addnw.catalog.databinding.FragmentDetailsGaleryBinding
import com.addnw.catalog.databinding.FragmentDetailsMainBinding
import com.addnw.catalog.screens.Civilization

class DetailsGalleryFragment(val civilization: Civilization) : Fragment() {
    companion object {
        const val LOG_KEY = "Details Gallery"
    }

    private lateinit var binding: FragmentDetailsGaleryBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsGaleryBinding.inflate(
            inflater,
            container,
            false
        )

        binding.apply {
            prepareSmallIcons(gallerySmall1, Civilization.ICON)
            prepareSmallIcons(gallerySmall2, Civilization.LEADER)
            prepareSmallIcons(gallerySmall3, Civilization.PERK1)
            prepareSmallIcons(gallerySmall4, Civilization.PERK2)
            val context = requireContext()
            galleryBig.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    context.resources.getIdentifier(
                        civilization.graphics[Civilization.ICON],
                        "drawable",
                        context.packageName
                    )
                )
            )
        }

        return binding.root
    }

    private fun prepareSmallIcons(icon: ImageButton, id: Int) {
        val context = requireContext()
        val image = ContextCompat.getDrawable(
            context,
            context.resources.getIdentifier(
                civilization.graphics[id],
                "drawable",
                context.packageName
            )
        )
        icon.setImageDrawable( image )
        icon.setOnClickListener {
            binding.galleryBig.setImageDrawable( image )
        }
    }
}
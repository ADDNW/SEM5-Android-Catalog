package com.addnw.catalog.screens.details

import androidx.fragment.app.Fragment
import com.addnw.catalog.screens.Civilization

class CatalogFragment(val civilization: Civilization) : Fragment() {
    companion object {
        const val LOG_KEY = "Details"
    }
}
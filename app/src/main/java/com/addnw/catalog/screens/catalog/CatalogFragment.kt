package com.addnw.catalog.screens.catalog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.addnw.catalog.R
import com.addnw.catalog.databinding.FragmentCatagogBinding
import com.addnw.catalog.screens.Civilization
import com.addnw.catalog.screens.CivilizationViewModel

class CatalogFragment : Fragment() {
    companion object {
        const val LOG_KEY = "Catalog"
    }

    private lateinit var binding: FragmentCatagogBinding

    private lateinit var viewModel: CivilizationViewModel

    private lateinit var civilizations: ArrayList<Civilization>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_KEY, "Created")
        viewModel = ViewModelProvider(this).get(CivilizationViewModel::class.java)

        binding = FragmentCatagogBinding.inflate(
            inflater,
            container,
            false
        )

        civilizations = viewModel.getCivilizationList()
        binding.rvCatalog.adapter = CatalogAdapter(civilizations)
        binding.rvCatalog.layoutManager = LinearLayoutManager(context)


        return binding.root
    }
}

class CatalogAdapter (private val civilizations: List<Civilization>): RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {
    companion object {
        const val LOG_KEY = "CatalogAdapter"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civilizationIcon = itemView.findViewById<ImageView>(R.id.CivilizationIcon)
        val civilizationName = itemView.findViewById<TextView>(R.id.CivilizationName)
        val civilizationRegion = itemView.findViewById<TextView>(R.id.CivilizationRegion)
        val isFavorite = itemView.findViewById<ImageButton>(R.id.favoriteStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val civilizationView = inflater.inflate(R.layout.catalog_row, parent, false)
        return ViewHolder(civilizationView)
    }

    override fun onBindViewHolder(holder: CatalogAdapter.ViewHolder, position: Int) {
        val entry = civilizations[position]
        //TODO add civilization-based texts and icons
        holder.civilizationName.text = "CIV_NAME"
        holder.civilizationRegion.text = "CIV_REGION"
        Log.d(LOG_KEY, "bind at $position")
    }

    override fun getItemCount(): Int {
        return civilizations.size
    }
}

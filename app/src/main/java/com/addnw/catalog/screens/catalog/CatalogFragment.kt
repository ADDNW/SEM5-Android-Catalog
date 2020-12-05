package com.addnw.catalog.screens.catalog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.addnw.catalog.R
import com.addnw.catalog.databinding.FragmentCatagogBinding
import com.addnw.catalog.screens.Civilization
import com.addnw.catalog.screens.CivilizationViewModel
import com.addnw.catalog.screens.Region

class CatalogFragment : Fragment() {
    companion object {
        const val LOG_KEY = "Catalog"
    }

    private lateinit var binding: FragmentCatagogBinding

    private lateinit var viewModel: CivilizationViewModel

    private lateinit var civilizations: MutableList<Civilization>

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
        binding.rvCatalog.adapter = CatalogAdapter(civilizations, viewModel)
        binding.rvCatalog.layoutManager = LinearLayoutManager(context)

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (binding.rvCatalog.adapter as CatalogAdapter).removeAt(viewHolder.adapterPosition)
            }
        }

        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.rvCatalog)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(LOG_KEY, "Options menu created")
        inflater.inflate(R.menu.category_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.category_all -> filter(null, false)
            R.id.category_favourite -> filter(null, true)
            R.id.category_europe -> filter(Region.EUROPE, false)
            R.id.category_africa -> filter(Region.AFRICA, false)
            R.id.category_america -> filter(Region.AMERICA, false)
            R.id.category_asia -> filter(Region.ASIA, false)
            R.id.category_indian -> filter(Region.INDIAN, false)
        }
        return true
    }

    private fun filter(option: Region?, byFavourite: Boolean) {
        if (byFavourite) {
            (binding.rvCatalog.adapter as CatalogAdapter).filterFavourite()
            Log.d(LOG_KEY, "Selected category: Favourite")
        } else {
            (binding.rvCatalog.adapter as CatalogAdapter).filter(option)
            Log.d(LOG_KEY, "Selected category: $option")
        }

    }

    private inner class CatalogAdapter (private var civilizations: MutableList<Civilization>, private val viewModel: CivilizationViewModel): RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {
        lateinit var context: Context

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val civilizationIcon: ImageView = itemView.findViewById<ImageView>(R.id.CivilizationIcon)
            val civilizationName: TextView = itemView.findViewById<TextView>(R.id.CivilizationName)
            val civilizationRegion: TextView = itemView.findViewById<TextView>(R.id.CivilizationRegion)
            val isFavorite: ImageButton = itemView.findViewById<ImageButton>(R.id.favoriteStar)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val inflater = LayoutInflater.from(context)
            val civilizationView = inflater.inflate(R.layout.catalog_row, parent, false)
            return ViewHolder(civilizationView)
        }

        override fun onBindViewHolder(holder: CatalogAdapter.ViewHolder, position: Int) {
            val entry = civilizations[position]
            holder.civilizationName.text = entry.name
            holder.civilizationRegion.text = entry.region.toString()
            holder.civilizationIcon.setImageDrawable(
                    ContextCompat.getDrawable(context, context.resources.getIdentifier(entry.graphics[0], "drawable", context.packageName))
            )

            if (entry.favourite) holder.isFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_black_24dp))
            else holder.isFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_border_black_24dp))
            holder.isFavorite.setOnClickListener { switchFavorite(position, holder) }
        }

        override fun getItemCount(): Int {
            return civilizations.size
        }

        fun removeAt(position: Int) {
            civilizations.removeAt(position)
            notifyItemRemoved(position)
            viewModel.removeCivilization(position)
        }

        private fun switchFavorite(position: Int, holder: CatalogAdapter.ViewHolder) {
            civilizations[position].favourite = viewModel.switchFavourite(position)
            if (civilizations[position].favourite) holder.isFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_black_24dp))
            else holder.isFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_border_black_24dp))
        }

        fun filter(region: Region?) {
            civilizations = viewModel.filter(region)
            notifyDataSetChanged()

        }

        fun filterFavourite() {
            civilizations = viewModel.filterFavourite()
            notifyDataSetChanged()
        }
    }

    private abstract inner class SwipeToDeleteCallback(private val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        private val deleteIcon: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_delete_sweep)!!
        private val background = ColorDrawable()

        override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            val itemView = viewHolder.itemView
            val backgroundCornerOffset = 20

            val iconMargin: Int = (itemView.height - deleteIcon.intrinsicHeight) / 2
            val iconTop: Int = itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
            val iconBottom: Int = iconTop + deleteIcon.intrinsicHeight

            if (dX < 0) {
                val iconLeft: Int = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background.setBounds(
                        itemView.right + dX.toInt() - backgroundCornerOffset,
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                )
            } else if (dX == 0f) {
                background.setBounds(0, 0, 0, 0)
            }

            background.color = context.getColor(R.color.catalog_delete_background)
            background.draw(c)
            deleteIcon.draw(c)
        }
    }

    inner class CatalogListener
}

package com.addnw.catalog.screens.catalog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (binding.rvCatalog.adapter as CatalogAdapter).removeAt(viewHolder.adapterPosition)
            }
        }

        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.rvCatalog)
        return binding.root
    }
}

class CatalogAdapter (private val civilizations: ArrayList<Civilization>): RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {
    companion object {
        const val LOG_KEY = "CatalogAdapter"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civilizationIcon: ImageView = itemView.findViewById<ImageView>(R.id.CivilizationIcon)
        val civilizationName: TextView = itemView.findViewById<TextView>(R.id.CivilizationName)
        val civilizationRegion: TextView = itemView.findViewById<TextView>(R.id.CivilizationRegion)
        val isFavorite: ImageButton = itemView.findViewById<ImageButton>(R.id.favoriteStar)
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

    fun removeAt(position: Int) {
        civilizations.removeAt(position)
        notifyItemRemoved(position)
    }
}

abstract class SwipeToDeleteCallback(private val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_delete_sweep)!!
    private val background = ColorDrawable()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

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
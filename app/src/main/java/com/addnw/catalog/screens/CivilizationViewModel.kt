package com.addnw.catalog.screens

import android.util.Log
import androidx.lifecycle.ViewModel

class CivilizationViewModel: ViewModel() {
    companion object {
        const val LOG_KEY = "CivilizationViewModel"
    }

    // active civilizations list
    private lateinit var civilizations: MutableList<Civilization>
    private lateinit var civilizationsFiltered: MutableList<Civilization>

    init {
        Log.d(LOG_KEY, "created")
        createList()
    }

    private fun createList() {
        civilizations = mutableListOf<Civilization>(
            Civilization("America", "Washington", Region.AMERICA,
                listOf("america","washington","b17", "minuteman"),
                listOf("B17 (replaces Bomber)", "Minuteman (replaces Musketman)", "All land military units have +1 sight", "50% discount when purchasing tiles")
            ),
            Civilization("Aztec", "Montezuma", Region.INDIAN,
                listOf("aztec", "montezuma", "jaguar", "floating_gardens"),
                listOf("Jagur (replaces Warrior)", "Floating gardens (replaces Water mill)", "Gains culture for the empire from each enemy unit killed")
            ),
            Civilization("China", "Wu Zetian", Region.ASIA,
                listOf("china", "wu_zetian", "chu_ko_nu", "paper_maker"),
                listOf("Chu-ko-nu (replaces Crossbwoman)", "Paper maker (replaces Library)", "The Great General combat bonus is doubled, and their spawn rate is increased by 50%")
            )
//            ,
//            Civilization("Egypt", "Ramesses II", Region.AFRICA,
//                listOf("egypt", "ramesses_ii", "war_chariot", "burial_tomb"),
//                listOf("War chariot (replaces Chariot archer)", "Burial tomb (replaces Temple)", "+20% production towards Wonder construction")
//            ),
//            Civilization("Ethiopia", "Haile Selassie", Region.AFRICA,
//                listOf("ethiopia", "haile_selassie", "mehal_sefari", "stele"),
//                listOf("Mehal Sefari (replaces Rifleman)", "Stele (replaces Monument)", "Combat bonus (+20%) when fighting units from a Civilization with more Cities than Ethiopia")
//            )
//            ,
//            Civilization("Greece", "Alexander", Region.EUROPE,
//                listOf(),
//                listOf()
//            ),
//            Civilization("Huns", "Attila", Region.ASIA,
//                listOf(),
//                listOf()
//            ),
//            Civilization("India", "Gandhi", Region.ASIA,
//                listOf(),
//                listOf()
//            ),
//            Civilization("Iroquois", "Hiawatha", Region.INDIAN,
//                listOf(),
//                listOf()
//            ),
//            Civilization("Russia", "Catherine", Region.EUROPE,
//                listOf(),
//                listOf()
//            ),
//            Civilization("Spanish", "Isabella", Region.EUROPE,
//                listOf(),
//                listOf()
//            ),
//            Civilization("Venice", "Enrico Dandolo", Region.EUROPE,
//                listOf(),
//                listOf()
//            )
        )
        civilizationsFiltered = civilizations.toMutableList()
        Log.d(LOG_KEY, "added ${civilizations.size} civilizations")
    }

    fun removeCivilization(index: Int) {
        val removed = civilizationsFiltered.removeAt(index)
        civilizations.remove(removed)
        Log.d(LOG_KEY, "removed: $removed")
    }

    fun switchFavourite(index: Int): Boolean {
        civilizationsFiltered[index].favourite = ! civilizationsFiltered[index].favourite
        val updated = civilizations.find { civ -> civ.id == civilizationsFiltered[index].id }
        Log.d(LOG_KEY, "new status of: ${updated?.name} is ${updated?.favourite}")
        return civilizationsFiltered[index].favourite
    }

    fun getCivilizationList(): MutableList<Civilization> {
        return civilizationsFiltered.toMutableList()
    }

    fun filter(region: Region?) : MutableList<Civilization> {
        civilizationsFiltered = if (region != null) {
            (civilizations.filter { civ -> civ.region == region }).toMutableList()
        } else civilizations.toMutableList()
        return civilizationsFiltered
    }

    fun filterFavourite() : MutableList<Civilization> {
        civilizationsFiltered = (civilizations.filter { civ -> civ.favourite }).toMutableList()
        return civilizationsFiltered
    }
}

class Civilization(val name: String, val leader: String, val region: Region, val graphics: List<String>, val traits: List<String>, val id: Int = ID++, var favourite: Boolean = false) {
    companion object {
        var ID = 0;
    }
}


enum class Region {
    EUROPE,
    INDIAN,
    AMERICA,
    AFRICA,
    ASIA
}
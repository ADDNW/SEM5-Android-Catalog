package com.addnw.catalog.screens

import android.util.Log
import androidx.lifecycle.ViewModel

class CivilizationViewModel: ViewModel() {
    companion object {
        const val LOG_KEY = "CivilizationViewModel"
    }

    // active pokemon list
    private lateinit var civilizations: MutableList<Civilization>

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
            Civilization("Aztec", "Montezuma", Region.AMERICA,
                listOf("aztec", "montezuma", "jaguar", "floating_gardens"),
                listOf("Jagur (replaces Warrior)", "Floating gardens (replaces Water mill)", "Gains culture for the empire from each enemy unit killed")
            ),
            Civilization("China", "Wu Zetian", Region.FAREAST,
                listOf("china", "wu_zetian", "chu_ko_nu", "paper_maker"),
                listOf("Chu-ko-nu (replaces Crossbwoman)", "Paper maker (replaces Library)", "The Great General combat bonus is doubled, and their spawn rate is increased by 50%")
            ),
            Civilization("Egypt", "Ramesses II", Region.AFRICA,
                listOf("egypt", "ramesses_ii", "war_chariot", "burial_tomb"),
                listOf("War chariot (replaces Chariot archer)", "Burial tomb (replaces Temple)", "+20% production towards Wonder construction")
            ),
            Civilization("Ethiopia", "Haile Selassie", Region.AFRICA,
                listOf("ethiopia", "haile_selassie", "mehal_sefari", "stele"),
                listOf("Mehal Sefari (replaces Rifleman)", "Stele (replaces Monument)", "Combat bonus (+20%) when fighting units from a Civilization with more Cities than Ethiopia")
            )
//            ,
//            Civilization("Greece", "Alexander", Region.EUROPE,
//                listOf(),
//                listOf()
//            ),
//            Civilization("Huns", "Attila", Region.FAREAST,
//                listOf(),
//                listOf()
//            ),
//            Civilization("India", "Gandhi", Region.FAREAST,
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
        Log.d(LOG_KEY, "added $civilizations.size civilizations")
    }

    fun removeCivilization(index: Int) {
        val removed = civilizations.removeAt(index)
        Log.d(LOG_KEY, "removed: $removed")
    }

    fun getCivilizationList(): ArrayList<Civilization> {
        return civilizations.toList() as ArrayList<Civilization>;
    }
}

class Civilization(val name: String, val leader: String, val region: Region, val graphics: List<String>, val traits: List<String>)

enum class Region {
    EUROPE,
    INDIAN,
    AMERICA,
    AFRICA,
    FAREAST,
    NEAREAST,
    OCEANIA
}
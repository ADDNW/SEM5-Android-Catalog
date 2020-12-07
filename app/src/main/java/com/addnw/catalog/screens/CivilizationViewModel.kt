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
    private var currentCivilization: Civilization

    init {
        Log.d(LOG_KEY, "created")
        createList()
        currentCivilization = civilizations[0]
    }

    private fun createList() {
        civilizations = mutableListOf<Civilization>(
            Civilization("America", "Washington", Region.AMERICA,
                listOf("america","washington","b17", "minuteman"),
                listOf("B17 (replaces Bomber)", "Minuteman (replaces Musketman)",
                    "All land military units have +1 sight",
                    "50% discount when purchasing tiles"
                )
            ),
            Civilization("Aztec", "Montezuma", Region.INDIAN,
                listOf("aztec", "montezuma", "jaguar", "aztec"),
                listOf("Jagur (replaces Warrior)", "Floating gardens (replaces Water mill)",
                    "Gains culture for the empire from each enemy unit killed"
                )
            ),
            Civilization("China", "Wu Zetian", Region.ASIA,
                listOf("china", "wu_zetian", "chu_ko_nu", "china"),
                listOf("Chu-ko-nu (replaces Crossbwoman)", "Paper maker (replaces Library)",
                    "The Great General combat bonus is doubled, and their spawn rate is increased by 50%"
                )
            ),
            Civilization("Egypt", "Ramesses II", Region.AFRICA,
                listOf("egypt", "ramesses_ii", "war_chariot", "egypt"),
                listOf("War chariot (replaces Chariot archer)", "Burial tomb (replaces Temple)",
                    "+20% production towards Wonder construction"
                )
            ),
            Civilization("Ethiopia", "Haile Selassie", Region.AFRICA,
                listOf("ethiopia", "haile_selassie", "mehal_sefari", "ethiopia"),
                listOf("Mehal Sefari (replaces Rifleman)", "Stele (replaces Monument)",
                    "Combat bonus (+20%) when fighting units from a Civilization with more Cities than Ethiopia"
                )
            )
            ,
            Civilization("Greece", "Alexander", Region.EUROPE,
                listOf("greece", "alexander", "companion_cavalry", "hoplite"),
                listOf("Companion cavalry (replaces Horseman)", "Hoplite (replaces Spearman)",
                    "City-State influence degrades at half and recovers at twice the normal rate",
                    "Hidden: Even if Greek units end their turn within neutral, unfriendly or hostile city-state borders, Greece will not lose influence with them and the units will heal as if they were on friendly territory."
                )
            ),
            Civilization("Huns", "Attila", Region.ASIA,
                listOf("huns", "attila", "battering_ram", "horse_archer"),
                listOf("Battering ram (replaces Spearman)", "Horse archer (replaces Chariot Archer)",
                    "Raze Cities at double speed",
                    "Borrow City names from other in-game Civ",
                    "Start with Animal Husbandry technology",
                    "+1 production per Pasture"
                )
            ),
            Civilization("India", "Gandhi", Region.ASIA,
                listOf("india", "gandhi", "war_elephant", "india"),
                listOf("War elephant (replaces Chariot archer)", "Mughal fort (replaces Castle)",
                    "Unhappiness from number of Cities doubled",
                    "Unhappiness from number of Citizens halved"
                )
            ),
            Civilization("Iroquois", "Hiawatha", Region.INDIAN,
                listOf("iroquois", "hiawatha", "mohawk_warrior", "iroquois"),
                listOf("Mohawk warrior (replaces Swordsman)", "Longhouse (replaces Workshop)",
                    "Units move through Forest and Jungle in friendly territory as if they were Roads",
                    "These tiles can be used to establish City Connections upon researching The Wheel",
                    "Caravans move along Forest and Jungle as if they were Roads"
                )
            ),
            Civilization("Russia", "Catherine", Region.EUROPE,
                listOf("russia", "catherine", "cossack", "russia"),
                listOf("Cossack (replaces Cavalry)", "Krepost (replaces Barracks)",
                    "Strategic Resources provide +1 production, and Horse, Iron and Uranium Resources provide double quantity"
                )
            ),
            Civilization("Spain", "Isabella", Region.EUROPE,
                listOf("spain", "isabella", "tercio", "conquistador"),
                listOf("Tercio (replaces Musketman)", "Conquistador (replaces Knight)",
                    "Gold bonus for discovering a Natural Wonder (bonus enhanced if first to discover it)",
                    "Culture, Happiness and tile yields from Natural Wonders doubled"
                )
            ),
            Civilization("Venice", "Enrico Dandolo", Region.EUROPE,
                listOf("venice", "enrico_dandolo", "merchant_of_venice", "great_galleass"),
                listOf("Merchant of Venice (replaces Great merchant)", "Great galleass (replaces Galleass)",
                    "Cannot gain settlers or annex cities",
                    "Double the normal number of trade routes available",
                    "A Merchant of Venice appears after researching Optics",
                    "May purchase in puppeted cities"
                )
            )
        )
        civilizationsFiltered = civilizations.toMutableList()
        Log.d(LOG_KEY, "added ${civilizations.size} civilizations")
    }

    fun removeCivilization(index: Int) {
        val removed = civilizationsFiltered.removeAt(index)
        civilizations.remove(removed)
        Log.d(LOG_KEY, "removed: ${removed.name}")
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

    fun setCurrent(position: Int) {
        currentCivilization = civilizationsFiltered[position]
    }

    fun getCurrent() : Civilization {
        return currentCivilization
    }
}

class Civilization(val name: String, val leader: String, val region: Region, val graphics: List<String>, val traits: List<String>, val id: Int = ID++, var favourite: Boolean = false) {
    companion object {
        var ID = 0

        // graphics indexes
        val ICON = 0
        val LEADER = 1
        val PERK1 = 2
        val PERK2 = 3
    }
}


enum class Region {
    EUROPE,
    INDIAN,
    AMERICA,
    AFRICA,
    ASIA
}
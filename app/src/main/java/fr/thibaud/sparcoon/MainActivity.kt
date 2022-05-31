package fr.thibaud.sparcoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.thibaud.sparcoon.fragments.AddVehiculeFragment
import fr.thibaud.sparcoon.fragments.HomeFragment
import fr.thibaud.sparcoon.fragments.VehiculeListFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Charger la page d'accueil par défaut
        loadFragment(HomeFragment(this))

        //importer la bottomnavigationview
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this))
                    return@setOnItemSelectedListener true
                }
                R.id.vehicule_page -> {
                    loadFragment(VehiculeListFragment(this))
                    return@setOnItemSelectedListener true
                }
                R.id.add_vehicule_page -> {
                    loadFragment(AddVehiculeFragment(this))
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
    }

        private fun loadFragment(fragment: Fragment) {
            //Charger le vehiculeRepository
            val repo = VehiculeRepository()

            //Mettre à jour la liste de véhicules
            repo.updateData {
                //Injecter le fragment dans la boite (fragment_container) après que la liste de véhicule est chargée
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
}


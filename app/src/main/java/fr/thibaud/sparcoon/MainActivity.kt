package fr.thibaud.sparcoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.thibaud.sparcoon.fragments.HomeFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Charger le vehiculeRepository
        val repo=VehiculeRepository()

        //Mettre à jour la liste de véhicules
        repo.updateData{
            //Injecter le fragment dans la boite (fragment_container) après que la liste de véhicule est chargée
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HomeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }
}

        //importer la bottomnavigationview
        /*val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this))
                    return@setOnItemSelectedListener true
                }
                R.id.vehicule_page -> {
                    loadFragment(VehiculeFragment(this))
                    return@setOnItemSelectedListener true
                }
                else -> {false}
            }
        }*/
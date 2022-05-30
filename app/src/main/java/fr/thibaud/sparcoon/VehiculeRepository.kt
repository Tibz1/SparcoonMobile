package fr.thibaud.sparcoon

import com.google.firebase.FirebaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.databaseRef

class VehiculeRepository {


    object Singleton {
        //Se connecter à la référence "vehicules"
        val databaseRef = FirebaseDatabase.getInstance().getReference("vehicules")

        //créer une liste qui va contenir les véhicules
        val vehiculeList = arrayListOf<VehiculeModel>()
    }

    fun updateData(){
        //absorber les données depuis la databaseRef -> liste de véhicules
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //récolter la liste

            }

            override fun onCancelled(p0: DatabaseError) {}

        })
    }

}
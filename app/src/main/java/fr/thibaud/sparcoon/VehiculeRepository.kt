package fr.thibaud.sparcoon

import com.google.firebase.FirebaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.databaseRef
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.vehiculeList

class VehiculeRepository {


    object Singleton {
        //Se connecter à la référence "vehicules"
        val databaseRef = FirebaseDatabase.getInstance().getReference("vehicules")

        //créer une liste qui va contenir les véhicules
        val vehiculeList = arrayListOf<VehiculeModel>()
    }

    fun updateData(callback: () -> Unit){
        //absorber les données depuis la databaseRef -> liste de véhicules
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //Retirer les anciens véhicules
                vehiculeList.clear()
                //récolter la liste
                for (ds in snapshot.children){
                    //Construire un objet plante
                    val vehicule = ds.getValue(VehiculeModel::class.java)

                    //Vérifier que le véhicule n'est pas null
                    if (vehicule != null){
                        //Ajouter le véhicule à la liste
                        vehiculeList.add(vehicule)
                    }
                }
                //Actionner le callback après que la liste est remplie
                callback()


            }

            override fun onCancelled(p0: DatabaseError) {}

        })
    }

    //Mettre à jour un objet vehicule en bdd
    fun updateVehicule (vehicule :VehiculeModel){
    databaseRef.child(vehicule.id.toString()).setValue(vehicule)
    }

}
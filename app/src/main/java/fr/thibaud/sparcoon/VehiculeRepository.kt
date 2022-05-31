package fr.thibaud.sparcoon

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.databaseRef
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.downloadUri
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.storageReference
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.vehiculeList
import java.util.*

class VehiculeRepository {


    object Singleton {
        //Donner le lien pour accéder au bucket
        private val BUCKET_URL: String = "gs://sparcoon.appspot.com"

        //Se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //Se connecter à la référence "vehicules"
        val databaseRef = FirebaseDatabase.getInstance().getReference("vehicules")

        //créer une liste qui va contenir les véhicules
        val vehiculeList = arrayListOf<VehiculeModel>()

        //Contenir le lien de l'image courante
        var downloadUri: Uri? = null
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

    //Céer une fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit){
        //Vérifier que le fichier n'est pas null
        if(file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            //Démarrer la tâche d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->

                //Si il y a eu un problème lors de l'envoi du fichier
                if(!task.isSuccessful) {
                    task.exception?.let { throw it }
                }

                return@Continuation ref.downloadUrl

            }).addOnCompleteListener { task ->
                //Vérifier si tout a bien fonctionné
                if(task.isSuccessful){
                    //Récupérer l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }
    }

    //Mettre à jour un objet vehicule en bdd
    fun updateVehicule (vehicule :VehiculeModel){
        databaseRef.child(vehicule.id).setValue(vehicule)
    }

    //Insérer un nouveau véhicule en bdd
    fun insertVehicule (vehicule :VehiculeModel){
        databaseRef.child(vehicule.id).setValue(vehicule)
    }

    //Supprimer un véhicule de la base
    fun deleteVehicule(vehicule: VehiculeModel) {
        databaseRef.child(vehicule.id).removeValue()
    }

}
package fr.thibaud.sparcoon.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.widget.ImageView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.thibaud.sparcoon.*

class VehiculeAdapter(
    val context: MainActivity,
    private val vehiculeList: List<VehiculeModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<VehiculeAdapter.ViewHolder>() {

    //Boite pour ranger tous les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //le ? signifie que le champ peut être nul donc on tente de le récupérer et si on ne peut pas on laisse la données du modèle
        val vehiculeImage: ImageView = view.findViewById(R.id.image_item)
        val vehiculeName:TextView? = view.findViewById(R.id.name_item)
        val vehiculeImmat:TextView? = view.findViewById(R.id.immat_item)
        val starIcon: ImageView = view.findViewById(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Récupérer les informations du véhicule à la position
        val currentVehicule = vehiculeList[position]

        //Récupérer le repository
        val repo = VehiculeRepository()

        //Utiliser Glide pour récupérer l'image à partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentVehicule.imageUrl)).into(holder.vehiculeImage)

        //Mettre à jour nom du véhicule
        holder.vehiculeName?.text = currentVehicule.name

        //Mettre à jour immatriculation du véhicule
        holder.vehiculeImmat?.text = currentVehicule.immat

        //Vérifier si le véhicule est liké
        if(currentVehicule.liked){
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        //Ajouter une intéraction sur l'étoile
        holder.starIcon.setOnClickListener{
            //Inverser si le bouton est like ou non
            currentVehicule.liked = !currentVehicule.liked
            //Mettre à jour l'objet vehicule
            repo.updateVehicule(currentVehicule)
        }

        //Nouvelle interaction lors du click sur une plante
        holder.itemView.setOnClickListener {
            //Afficher la popuo
            VehiculePopup(this, currentVehicule).show()
        }

    }

    override fun getItemCount(): Int {
        return vehiculeList.size
    }
}
package localdomain.nader.covtunapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import localdomain.nader.covtunapp.mongoDb.Covoiturage;
import localdomain.nader.covtunapp.mongoDb.Utilisateur;
import localdomain.nader.covtunmockup.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{

    private ArrayList<Covoiturage>  resultSet = new ArrayList<>();
    private Context context ;
    private Utilisateur utilisateur;

    public RecyclerViewAdapter(Context context ,ArrayList<Covoiturage> resultSet, Utilisateur u) {
        this.context = context;
        this.resultSet = resultSet;
        this.utilisateur = u;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.date.setText("Date départ: "+resultSet.get(position).getDateDepart());
        holder.villeDepart.setText("Ville de départ: "+resultSet.get(position).getTrajet().getVilleDepart());
        holder.villeArrivee.setText("Ville d'arrivée: "+resultSet.get(position).getTrajet().getVilleArrivee());
        holder.nbPlacesRestantes.setText("Nb Places Restantes: "+(resultSet.get(position).getVoiture().getNbPlaces()-resultSet.get(position).getNbReservations()));
        holder.prix.setText("Prix en dinars: "+resultSet.get(position).getPrix().toString());

        holder.pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,CovoiturageDetails.class);
                intent.putExtra("idUtilisateur", utilisateur);
                intent.putExtra("covoiturage", resultSet.get(position));
                context.startActivity(intent);
            }
        });

    }

    public void setData(ArrayList<Covoiturage> listeCovNew) {
        this.resultSet = listeCovNew;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return resultSet.size();
    }

    public ArrayList<Covoiturage> getResultSet() {
        return resultSet;
    }

    public void setResultSet(ArrayList<Covoiturage> resultSet) {
        this.resultSet = resultSet;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView date, villeDepart,villeArrivee,nbPlacesRestantes,prix;
        public LinearLayout pl;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.searchItemDate);
            villeDepart = itemView.findViewById(R.id.searchItemVilleDepart);
            villeArrivee = itemView.findViewById(R.id.searchItemVilleArrivee);
            nbPlacesRestantes = itemView.findViewById(R.id.searchItemNbPlacesRestantes);
            prix = itemView.findViewById(R.id.searchItemPrix);

            pl = itemView.findViewById(R.id.parent_layout_item);


        }

        public TextView getDate() {
            return date;
        }

        public void setDate(TextView date) {
            this.date = date;
        }

        public TextView getVilleDepart() {
            return villeDepart;
        }

        public void setVilleDepart(TextView villeDepart) {
            this.villeDepart = villeDepart;
        }

        public TextView getVilleArrivee() {
            return villeArrivee;
        }

        public void setVilleArrivee(TextView villeArrivee) {
            this.villeArrivee = villeArrivee;
        }

        public TextView getNbPlacesRestantes() {
            return nbPlacesRestantes;
        }

        public void setNbPlacesRestantes(TextView nbPlacesRestantes) {
            this.nbPlacesRestantes = nbPlacesRestantes;
        }

        public TextView getPrix() {
            return prix;
        }

        public void setPrix(TextView prix) {
            this.prix = prix;
        }

        public LinearLayout getPl() {
            return pl;
        }

        public void setPl(LinearLayout pl) {
            this.pl = pl;
        }
    }

}

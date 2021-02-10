package com.example.gestion_contact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MonAdapter extends BaseAdapter {


    Context con ;
    ArrayList<Contact> d ;
    ArrayList<Contact> d2;

    ContactFilter filter;


    MonAdapter(Context con, ArrayList<Contact> d)
{
    this.con=con;
    this.d=d;
    this.d2=d;

}

    @Override
    public int getCount() {
        return d.size();
    }

    @Override
    public Object getItem(int position) {
        return d.get(position);
    }

    @Override
    public long getItemId(int position) {
        return d.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf=LayoutInflater.from(con);
        LinearLayout l =(LinearLayout) inf.inflate(R.layout.view_contact,null);

        TextView tvnom =l.findViewById(R.id.tvn);
        TextView tvp =l.findViewById(R.id.tvp);
        TextView tvnum =l.findViewById(R.id.num);

        Contact c = d.get(position);
        tvnom.setText(c.nom);
        tvp.setText(c.prenom);
        tvnum.setText(c.numero);
        return l;
    }



public Filter getFilter() {

        if (filter == null){
            filter=new ContactFilter();
        }

        return filter;

}

class ContactFilter extends Filter{

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {

            constraint = constraint.toString().toUpperCase();
            ArrayList<Contact> FilteredArrList = new ArrayList<Contact>();


            for (int i = 0; i < d.size(); i++) {
                String data = d.get(i).nom;
                if (data.toUpperCase().contains(constraint)) {

                    Contact c= new Contact(d.get(i).getNom(),d.get(i).getPrenom(),d.get(i).getNumero());
                   // d.add(new Contact(d.get(i).nom, d.get(i).prenom));


                    FilteredArrList.add(c);
                }


            }


            // set the Filtered result to return
            results.count = FilteredArrList.size();
            results.values = FilteredArrList;

        } else {
            results.count = d.size();
            results.values = d;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        d2=(ArrayList<Contact>) results.values;
        notifyDataSetChanged();
    }
}

    public Filter getFilterC() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                d2 = (ArrayList<Contact>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Contact> FilteredArrList = new ArrayList<Contact>();

                if (d == null) {
                    d = new ArrayList<Contact>(d2); // saves the original data in mOriginalValues
                }
                /*******
                 If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 else does the Filtering and returns FilteredArrList(Filtered)
                 ********/
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = d.size();
                    results.values = d;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < d.size(); i++) {
                        String data = d.get(i).nom;
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Contact(d.get(i).nom,d.get(i).prenom));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

}





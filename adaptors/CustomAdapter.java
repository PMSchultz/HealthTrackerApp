package edu.cnm.deepdive.healthtracker.adaptors;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import edu.cnm.deepdive.healthtracker.entities.Allergy;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Allergy> implements OnClickListener{

  private ArrayList<Allergy> allergyData;


  public CustomAdapter(Context context, int resource) {
    super(context, resource);
  }


  @Override
  public void onClick(View view) {

  }
}

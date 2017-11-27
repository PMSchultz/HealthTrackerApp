package edu.cnm.deepdive.healthtracker.mainfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Allergy;
import edu.cnm.deepdive.healthtracker.entities.Medication;
import edu.cnm.deepdive.healthtracker.entities.OfficeVisit;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.util.List;

/**
 * A fragment which contains a list of a selected patient's medication records.
 */
public class ListMedicationFragment extends Fragment implements View.OnClickListener,
    AdapterView.OnItemClickListener{
/* a patient instance */
  private Patient patient = null;
  /* a medication instance */
  private Medication medication = null;

  /**
   * Required empty public constructor
   */
  public ListMedicationFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    int patientID;
    if (args != null && (patientID = args.getInt(MainActivity.PATIENT_ID_KEY, 0)) != 0) {
      try {
        patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
            .queryForId(patientID);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }

    }
  }

  /**
   * method to create a medication list
   * @param inflate
   */
  private void setupList(View inflate) {
    if (patient != null) {

      try {
        ListView chart = inflate
            .findViewById(R.id.content_list);//creating a reference to the chart contents
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Medications: " + patient.getName());
        OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
        Dao<Medication, Integer> dao = helper.getMedicationDao();
        QueryBuilder<Medication, Integer> builder = dao.queryBuilder();
        builder.where().eq("PATIENT_ID", patient.getId());
        builder.orderByRaw("CASE WHEN STOP_DATE IS NULL THEN 0 ELSE 1 END, START_DATE");
        //builder.orderBy("START_DATE", true).orderBy("STOP_DATE", true);
        List<Medication> visits = dao.query(builder.prepare());
        chart.setAdapter(new Adapter(getActivity(), R.layout.list_item, visits));
        chart.setOnItemClickListener(this);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_list, container, false);

    setupList(inflate);
    setupButtons(inflate);

    return inflate;
  }

  /**
   * method to evaluate what button was clicked and response
   * @param view
   */
  public void onClick(View view) {

    switch (view.getId()){
      case R.id.add_record:
        ((MainActivity)getActivity()).loadFragment(new MedicationFragment(), patient.getId(),true);
        break;
      case R.id.edit_record:
        Bundle args = new Bundle();
        args.putInt(MainActivity.PATIENT_ID_KEY, patient.getId());
        args.putInt(MedicationFragment.MEDICATION_ID_KEY, medication.getId());
        ((MainActivity)getActivity()).loadFragment(new MedicationFragment(), args,true);
        break;

    }

  }

  /**
   *set OnClickListener to buttons
   * @param rootView
   */
  private void setupButtons(View rootView) {
    Button addButton = rootView.findViewById(R.id.add_record);
    addButton.setOnClickListener(this);
    Button editButton = rootView.findViewById(R.id.edit_record);
    editButton.setOnClickListener(this);
    editButton.setEnabled(false);
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    medication = (Medication) adapterView.getItemAtPosition(i);
    Button editButton = getActivity().findViewById(R.id.edit_record);
    editButton.setEnabled(true);

  }
  /**
   * Custom adapter
   */
  private class Adapter extends ArrayAdapter<Medication> {
    /* layout to use for each item */
    private int resource;

    /**
     * Custom adapter to sort Medication items and add padding between current and discontinued Medication
     * @param context android context for displaying list
     * @param resource the layout to use for each item in list
     * @param objects Medication objects to be displayed in a list
     */
    public Adapter(Context context, int resource, List<Medication> objects) {
      super(context, resource, objects);
      this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      Medication item = getItem(position);
      TextView view = (TextView) ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resource, null);
      view.setText(Html.fromHtml(item.toString()));
      if (position < getCount() - 1){
        Medication nextItem = getItem(position + 1);
        if (item.getStopDate() == null && (nextItem.getStopDate() != null))  {

          view.setPadding(view.getPaddingStart(),view.getPaddingTop(),view.getPaddingEnd(),view.getPaddingBottom() + 40);
        }
      }
      return view;
    }
  }

}

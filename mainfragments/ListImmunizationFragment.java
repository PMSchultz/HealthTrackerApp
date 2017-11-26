package edu.cnm.deepdive.healthtracker.mainfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Immunization;
import edu.cnm.deepdive.healthtracker.entities.OfficeVisit;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.util.List;

/**
 * A fragment which contains a list of a selected patient's immunization records.
 */
public class ListImmunizationFragment extends Fragment implements View.OnClickListener,
    AdapterView.OnItemClickListener{
  /* a patient instance */
  private Patient patient = null;
  /* an immunization instance*/
  private Immunization immunization= null;

  /**
   * Required empty constructor
   */
  public ListImmunizationFragment() {
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
   * method to set OnClickListener to buttons
   * @param rootView
   */
  private void setupButtons(View rootView) {
    Button addButton = rootView.findViewById(R.id.add_record);
    addButton.setOnClickListener(this);
    Button editButton = rootView.findViewById(R.id.edit_record);
    editButton.setOnClickListener(this);
    editButton.setEnabled(false);
  }

  /**
   *
   * @param inflate
   */
  private void setupList(View inflate) {
    if (patient != null) {

      try {
        ListView chart = inflate
            .findViewById(R.id.content_list);//creating a reference to the chart contents
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Immunizations: " + patient.getName());
        OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
        Dao<Immunization, Integer> dao = helper.getImmunizationDao();
        QueryBuilder<Immunization, Integer> builder = dao.queryBuilder();
        builder.where().eq("PATIENT_ID", patient.getId());
        builder.orderBy("DATE", false);
        List<Immunization> visits = dao.query(builder.prepare());
        chart.setAdapter(new ArrayAdapter<Immunization>(getActivity(), R.layout.list_item,
            visits));
        chart.setOnItemClickListener(this);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.add_record:
        ((MainActivity)getActivity()).loadFragment(new ImmunizationFragment(), patient.getId(),true);
        break;
      case R.id.edit_record:
        Bundle args = new Bundle();
        args.putInt(MainActivity.PATIENT_ID_KEY, patient.getId());
        args.putInt(ImmunizationFragment.IMMUNIZATION_ID_KEY, immunization.getId());
        ((MainActivity)getActivity()).loadFragment(new ImmunizationFragment(), args,true);
        break;
    }
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    immunization = (Immunization) adapterView.getItemAtPosition(i);
    Button editButton = getActivity().findViewById(R.id.edit_record);
    editButton.setEnabled(true);

  }
}

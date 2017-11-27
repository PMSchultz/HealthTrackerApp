package edu.cnm.deepdive.healthtracker.mainfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Laboratory;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.util.List;

/**
 *
 *
 */
public class ListLabResultsFragment extends Fragment implements View.OnClickListener,
    AdapterView.OnItemClickListener {

  /*   a patient instance */
  private Patient patient = null;
  /* a laboratory instance */
  private Laboratory laboratory = null;

  /**
   * Required empty public constructor
   */
  public ListLabResultsFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
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

  private void setupList(View inflate) {
    if (patient != null) {

      try {
        ListView chart = inflate
            .findViewById(R.id.content_list);//creating a reference to the chart contents
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Lab tests: " + patient.getName());
        OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
        Dao<Laboratory, Integer> dao = helper.getLabDao();
        QueryBuilder<Laboratory, Integer> builder = dao.queryBuilder();
        builder.where().eq("PATIENT_ID", patient.getId());
        builder.orderBy("DATE", false);
        List<Laboratory> visits = dao.query(builder.prepare());
        chart.setAdapter(new Adapter(getActivity(), R.layout.list_item, visits));
        chart.setOnItemClickListener(this);
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

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.add_record:
        ((MainActivity) getActivity()).loadFragment(new LabResultsFragment(), patient.getId(), true);
        break;
      case R.id.edit_record:
        Bundle args = new Bundle();
        args.putInt(MainActivity.PATIENT_ID_KEY, patient.getId());
        args.putInt(LabResultsFragment.LABORATORY_ID_KEY, laboratory.getId());
        ((MainActivity) getActivity()).loadFragment(new LabResultsFragment(), args, true);
        break;
  }


  }
  /**
   * Set onClickListener to buttons
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
    laboratory = (Laboratory) adapterView.getItemAtPosition(i);
    Button editButton = getActivity().findViewById(R.id.edit_record);
    editButton.setEnabled(true);

  }

  /**
   * Custom adapter
   */
  private class Adapter extends ArrayAdapter<Laboratory> implements OnItemClickListener {

    /* layout to use for each item */
    private int resource;

    /**
     * Custom adapter to sort Lab items and add padding
     *
     * @param context android context for displaying list
     * @param resource the layout to use for each item in list
     * @param objects Laboratory objects to be displayed in a list
     */
    public Adapter(Context context, int resource, List<Laboratory> objects) {
      super(context, resource, objects);
      this.resource = resource;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
  }

}

package edu.cnm.deepdive.healthtracker.mainfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.OfficeVisit;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link  interface to handle interaction events. Use the factory method to create an instance of
 * this fragment.
 */
public class ListOfficeVisitFragment extends Fragment {


  private Patient patient = null;

  public ListOfficeVisitFragment() {
    // Required empty public constructor
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

  //TODO attach onClick Listeners to buttons
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_list, container, false);
    Button addButton = inflate.findViewById(R.id.add_record);
    if (patient != null) {

      try {
        ListView chart = inflate
            .findViewById(R.id.content_list);//creating a reference to the chart contents
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Office Visits: " + patient.getName());
        OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
        Dao<OfficeVisit, Integer> dao = helper.getOfficeVisitDao();
        QueryBuilder<OfficeVisit, Integer> builder = dao.queryBuilder();
        builder.where().eq("PATIENT_ID", patient.getId());
        builder.orderBy("DATE", false);
        List<OfficeVisit> visits = dao.query(builder.prepare());
        chart.setAdapter(new ArrayAdapter<OfficeVisit>(getActivity(), R.layout.list_item,
            visits));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return inflate;
  }


}

package edu.cnm.deepdive.healthtracker.loginfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.Toast;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A fragment used to create a patient
 */
public class CreatePatientFragment extends DialogFragment implements OnClickListener {

  /* Patient name */
  private EditText name;
  /* Patient date of birth*/
  private EditText dateOfBirth;

  /**
   * Required empty public constructor
   */
  public CreatePatientFragment() {

  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    getDialog().setTitle("Create Patient");
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_create_patient, container, false);
    name = view.findViewById(R.id.newPatientName);
    dateOfBirth = view.findViewById(R.id.patientDOB);
    view.findViewById(R.id.createPatient).setOnClickListener(this);
    view.findViewById(R.id.cancelPatient).setOnClickListener(this);
    return view;
  }


  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.createPatient) {
      try {
        OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
        Patient patient = new Patient();
        patient.setName(name.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

        patient.setDateOfBirth(dateFormat.parse(dateOfBirth.getText().toString()));
        helper.getPatientDao().create(patient);
        ((MainActivity) getActivity()).addItemsOnSpinner();
        dismiss();
      } catch (ParseException e) {
        e.printStackTrace();
        Toast.makeText(getActivity(), getString(R.string.dob_format_warning), Toast.LENGTH_LONG)
            .show();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }

    } else {
      dismiss();
    }
  }

}

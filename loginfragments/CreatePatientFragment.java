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
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the // *
 *  interface to handle interaction events. Use the {@link CreatePatientFragment#newInstance}
 * factory method to create an instance of this fragment.
 */
public class CreatePatientFragment extends DialogFragment implements OnClickListener {

  private EditText name;
  private EditText dateOfBirth;
  private OnFragmentInteractionListener mListener;

  public CreatePatientFragment() {
    // Required empty public constructor
  }

  public static CreatePatientFragment newInstance(int num) {

    CreatePatientFragment fragment = new CreatePatientFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
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

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }



  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
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
        ((MainActivity)getActivity()).addItemsOnSpinner();
        dismiss();
      } catch (ParseException e) {
        e.printStackTrace();
        Toast.makeText(getActivity(), "DOB format must be MM/DD/YY", Toast.LENGTH_LONG).show();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }

    }
    else {
      dismiss();
    }
  }

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity. <p> See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating with
   * Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {

    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}

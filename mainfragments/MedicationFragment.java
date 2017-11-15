package edu.cnm.deepdive.healthtracker.mainfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Hospitalization;
import edu.cnm.deepdive.healthtracker.entities.Medication;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * A simple {@linknterface to handle interaction events.
 * Use the {@linthod to create an instance of this fragment.
 */
public class MedicationFragment extends Fragment implements Button.OnClickListener {

  private EditText medicationName;
  private EditText dose;
  private EditText provider;
  private EditText note;
  private Button dateStarted;
  private Button dateEnded;



  public MedicationFragment() {
    // Required empty public constructor
  }

//  public static MedicationFragment newInstance(String param1, String param2) {
//    MedicationFragment fragment = new MedicationFragment();
//    Bundle args = new Bundle();
//    fragment.setArguments(args);
//    return fragment;
//  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_medication, container, false);
    Button addButton = view.findViewById(R.id.save_medication_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_medication_record);
    deleteButton.setOnClickListener(this);
    Button cancelButton = view.findViewById(R.id.cancel_medication_record);
    cancelButton.setOnClickListener(this);
    medicationName = view.findViewById(R.id.product);
    provider = view.findViewById(R.id.provider);
    note = view.findViewById(R.id.note);
    dose = view.findViewById(R.id.dose);
    dateStarted = view.findViewById(R.id.date_started);
    dateEnded = view.findViewById(R.id.date_ended);
    dateStarted.setOnClickListener(this);
    dateEnded.setOnClickListener(this);
    return view;
  }

//  // TODO: Rename method, update argument and hook method into UI event
//  public void onButtonPressed(Uri uri) {
//    if (mListener != null) {
//      mListener.onFragmentInteraction(uri);
//    }
//  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
//    if (context instanceof OnFragmentInteractionListener) {
//      mListener = (OnFragmentInteractionListener) context;
//    } else {
//      throw new RuntimeException(context.toString()
//          + " must implement OnFragmentInteractionListener");
//    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onClick(View view) {
    DatePickerFragment datePickerFragment;
    Bundle bundle;
    switch (view.getId()) {

      case R.id.date_started:
        datePickerFragment = new DatePickerFragment();
        bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
        break;
      case R.id.date_ended:
        datePickerFragment = new DatePickerFragment();
        bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
        break;
      case R.id.save_medication_record:
        try {
          OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
          Medication medication = new Medication();
          medication.setMedicationName(nullifyEmptyString(medicationName.getText().toString()));
          medication.setDose(nullifyEmptyString(dose.getText().toString()));
          medication.setProvider(nullifyEmptyString(provider.getText().toString()));
          medication.setNotes(nullifyEmptyString(note.getText().toString()));
          DateFormat format = DateFormat.getDateInstance();
          medication.setStartDate(format.parse(dateStarted.getText().toString()));
          medication.setStopDate(format.parse(dateEnded.getText().toString()));
          Bundle args = getArguments();
          int patientID = args.getInt(MainActivity.PATIENT_ID_KEY);
          Patient patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
              .queryForId(patientID);
          medication.setPatient(patient);
          helper.getMedicationDao().create(medication);

        } catch (SQLException e) {
          throw new RuntimeException(e);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
        getFragmentManager().popBackStack();
        break;
      case R.id.delete_medication_record:
        //TODO pop up message "Are you sure?"
        break;
      case R.id.cancel_medication_record:
        //TODO return to former screen
        break;

    }
  }

  public static String nullifyEmptyString(String string) {
    return (string.equals("") ? null : string);

  }

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity. <p> See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating with
   * Other Fragments</a> for more information.
   */
//  public interface OnFragmentInteractionListener {
//
//    // TODO: Update argument type and name
//    void onFragmentInteraction(Uri uri);
//  }
}

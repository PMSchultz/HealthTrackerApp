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
import edu.cnm.deepdive.healthtracker.entities.Immunization;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;

/**
 *
 * .
 */
public class ImmunizationFragment extends Fragment implements Button.OnClickListener {

  private AutoCompleteTextView vaccine;
  private EditText provider;
  private EditText note;
  private Button dateAdministered;


  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

//  private OnFragmentInteractionListener mListener;

  public ImmunizationFragment() {
    // Required empty public constructor
  }



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_immunization, container, false);
    view.findViewById(R.id.date_administered).setOnClickListener(this);
    Button addButton = view.findViewById(R.id.save_immunization_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_immunization_record);
    deleteButton.setOnClickListener(this);
    Button cancelButton = view.findViewById(R.id.cancel_immunization_record);
    cancelButton.setOnClickListener(this);
    dateAdministered = view.findViewById(R.id.date_administered);
    dateAdministered.setOnClickListener(this);
    vaccine= view.findViewById(R.id.vaccine);
    provider = view.findViewById(R.id.provider);
    note = view.findViewById(R.id.note);

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
  }


  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.date_administered:
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
        break;

      case R.id.save_immunization_record:
        try {
          OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
          Immunization immunization = new Immunization();
          immunization.setVaccine(nullifyEmptyString(vaccine.getText().toString()));
          immunization.setProvider(nullifyEmptyString(provider.getText().toString()));
          immunization.setNotes(nullifyEmptyString(note.getText().toString()));
          DateFormat format = DateFormat.getDateInstance();

          immunization.setDate(format.parse(dateAdministered.getText().toString()));
          Bundle args = getArguments();
          int patientID = args.getInt(MainActivity.PATIENT_ID_KEY);
          Patient patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
              .queryForId(patientID);
          immunization.setPatient(patient);
          helper.getImmunizationDao().create(immunization);

        } catch (SQLException e) {
          throw new RuntimeException(e);
        } catch (ParseException e){
          throw new RuntimeException(e);
        }

        getFragmentManager().popBackStack();
        break;

      case R.id.delete_immunization_record:
        //TODO pop up message "Are you sure?"
        break;
      case R.id.cancel_immunization_record:
        //TODO return to former screen
        break;
    }
  }
  public static String nullifyEmptyString(String string){
    return  (string.equals("") ? null: string);

  }
}




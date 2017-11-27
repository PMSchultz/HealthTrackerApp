package edu.cnm.deepdive.healthtracker.mainfragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Allergy;
import edu.cnm.deepdive.healthtracker.entities.OfficeVisit;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.entities.Radiology;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;

/**
 *
 */
public class RadiologyFragment extends Fragment implements Button.OnClickListener,
    OnItemSelectedListener {

  /* ID from Radiology entity*/
  public static final String RADIOLOGY_ID_KEY = "radiology_id";
  /* a radiology instance */
  private Radiology radiology = null;
  /*Patient selected */
  private Patient patient = null;
  /* Spinner which uses radiologyTypes String[]*/
  private Spinner spinner;
  /* User input of radiology exam name */
  private EditText examName;
  /* physician/specialist ordering exam*/
  private EditText provider;
  /* exam date */
  private Button examDate;
  /* note about exam*/
  private EditText note;
  /* facility name*/
  private EditText facility;
  /* body part imaged */
  private EditText bodyPart;

  /* Array list of radiology exam types used on spinner */
  private String[] radiologyTypes;
  /* */


  public RadiologyFragment() {
    // Required empty public constructor
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int patientId;
    int radiologyId;
    if (getArguments() != null
        && (patientId = getArguments().getInt(MainActivity.PATIENT_ID_KEY, 0)) != 0) {
      try {

        patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
            .queryForId(patientId);
        radiologyId = getArguments().getInt(RADIOLOGY_ID_KEY, 0);
        if (radiologyId > 0) {
          radiology = ((OrmInteraction) getActivity()).getHelper().getRadiologyDao()
              .queryForId(radiologyId);
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }

    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_radiology, container, false);
    radiologyTypes = getResources().getStringArray(R.array.radiology_types);
    spinner = view.findViewById(R.id.radiology_exam_spinner);
    Button addButton = view.findViewById(R.id.save_radiology_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_radiology_record);
    deleteButton.setOnClickListener(this);
    if (radiology == null) {
      deleteButton.setEnabled(false);
    } else {
      deleteButton.setOnClickListener(this);
    }
    Button cancelButton = view.findViewById(R.id.cancel_radiology_record);
    cancelButton.setOnClickListener(this);
    spinner.setOnItemSelectedListener(this);
    examDate = view.findViewById(R.id.exam_date);
    examDate.setOnClickListener(this);
    note = view.findViewById(R.id.note);
    provider = view.findViewById(R.id.provider);
    facility = view.findViewById(R.id.facility);
    bodyPart = view.findViewById(R.id.body_part);
    examName = view.findViewById(R.id.exam_name);
    if (radiology != null) {
      spinner.setSelection(Arrays.asList(radiologyTypes).indexOf(radiology.getRadiologyType()));
      examName.setText(emptyNullString(radiology.getRadiologyExamName()));
      facility.setText(emptyNullString(radiology.getFacility()));
      bodyPart.setText(emptyNullString(radiology.getBodyPartImaged()));
      provider.setText(emptyNullString(radiology.getProvider()));
      note.setText(emptyNullString(radiology.getNotes()));
      examDate.setText(DateFormat.getDateInstance().format(radiology.getExamDate()));
    }
    return view;
  }



  @Override
  public void onClick(View view) {
    DatePickerFragment datePickerFragment;
    Bundle bundle;
    switch (view.getId()) {
      case R.id.exam_date:
        datePickerFragment = new DatePickerFragment();
        bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
        break;
      case R.id.save_radiology_record:
        try {
          OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
          if (radiology == null) {
            radiology = new Radiology();
          }
          radiology.setRadiologyType(spinner.getSelectedItem().toString());
          radiology.setRadiologyExamName(nullifyEmptyString(examName.getText().toString()));
          radiology.setBodyPartImaged(nullifyEmptyString(bodyPart.getText().toString()));
          radiology.setFacility(nullifyEmptyString(facility.getText().toString()));
          radiology.setProvider(nullifyEmptyString(provider.getText().toString()));
          radiology.setNotes(nullifyEmptyString(note.getText().toString()));
          DateFormat format = DateFormat.getDateInstance();
          if (examDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.appt_date_required), Toast.LENGTH_LONG)
                .show();
            break;
          }

          try {
            radiology.setExamDate(format.parse(examDate.getText().toString()));

          } catch (ParseException e) {

           //do nothing
          }
          if (radiology.getFacility() == null || radiology.getBodyPartImaged() == null) {
            Toast.makeText(getContext(), "Body part imaged and facility fields are required",
                Toast.LENGTH_LONG).show();
            break;
          }

          radiology.setPatient(patient);
          if (radiology.getId() != 0) {
            helper.getRadiologyDao().update(radiology);
          } else {
            helper.getRadiologyDao().create(radiology);
          }
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        getFragmentManager().popBackStack();
        break;

      case R.id.delete_radiology_record:
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setMessage(getString(R.string.delete_toast)).setTitle("");
        builder.setPositiveButton(getString(R.string.delete_record), new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
            try {
              helper.getRadiologyDao().delete(radiology);
            } catch (SQLException e) {
              Toast.makeText(getContext(), getString(R.string.unable_delete), Toast.LENGTH_SHORT);
            }
            getActivity().getSupportFragmentManager().popBackStack();
          }
        });

        builder.setNegativeButton(getString(R.string.cancel), new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            //User clicked the Cancel Button
          }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        break;
      case R.id.cancel_radiology_record:
        getActivity().getSupportFragmentManager().popBackStack();
        break;

    }
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }

  /**
   * method to set empty string to null
   *
   * @param string the string that is being evaluated
   * @return if string is empty return null, else return the string
   */
  public static String nullifyEmptyString(String string) {
    return (string.equals("") ? null : string);

  }

  /**
   * method to evaluate if string is null
   *
   * @param string the string that is being evaluated
   * @return if the string is null return empty string, else return the string
   */
  public static String emptyNullString(String string) {

    return (string == null) ? "" : string;
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


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

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Immunization;
import edu.cnm.deepdive.healthtracker.entities.Laboratory;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;

/**
 *
 *
 */
public class LabResultsFragment extends Fragment implements Button.OnClickListener  {

  /* ID from Laboratory Entity*/
  public static final String LABORATORY_ID_KEY = "laboratoryId";
  /*The facility who performed lab work*/
  private EditText labName;
  /* The provider who ordered the lab work*/
  private EditText provider;
  /* a note about the labwork*/
  private EditText note;
  /* The date the labwork was performed*/
  private Button labworkDate;
  /* a laboratory instance*/
  private Laboratory laboratory = null;
  /* a patient instance*/
  private Patient patient = null;


  /**
   * Required empty public constructor
   */
  public LabResultsFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int patientId;
    int laboratoryId;
    if (getArguments() != null
        && (patientId = getArguments().getInt(MainActivity.PATIENT_ID_KEY, 0)) != 0) {
      try {

        patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
            .queryForId(patientId);
        laboratoryId = getArguments().getInt(LABORATORY_ID_KEY, 0);
        if (laboratoryId > 0) {
          laboratory = ((OrmInteraction) getActivity()).getHelper().getLabDao()
              .queryForId(laboratoryId);
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_lab_results, container, false);
    labworkDate = view.findViewById(R.id.labwork_date);
    labworkDate.setOnClickListener(this);
    Button addButton = view.findViewById(R.id.save_labwork_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_labwork_record);
    deleteButton.setOnClickListener(this);
    if (laboratory == null){
      deleteButton.setEnabled(false);
    }else {
      deleteButton.setOnClickListener(this);
    }
    Button cancelButton = view.findViewById(R.id.cancel_labwork_record);
    cancelButton.setOnClickListener(this);
    labName = view.findViewById(R.id.lab_facility);
    provider = view.findViewById(R.id.provider);
    note = view.findViewById(R.id.note);
    if (laboratory!= null) {
      labName.setText(emptyNullString(laboratory.getLabName()));
      labworkDate.setText(DateFormat.getDateInstance().format(laboratory.getLabworkDate()));
      provider.setText(emptyNullString(laboratory.getProvider()));
      note.setText(emptyNullString(laboratory.getNotes()));

    }else {
     // facility.requestFocus();
    }
    return view;
  }


  /**
   * method to set empty string to null
   * @param string the string that is being evaluated
   * @return if string is empty return null, else return the string
   */
  public static String nullifyEmptyString(String string) {
    return (string.equals("") ? null : string);
  }

  /**
   * method to evaluate if string is null
   * @param string the string that is being evaluated
   * @return if the string is null return empty string, else return the string
   */
  public static String emptyNullString(String string) {
    return (string == null) ? "" : string;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.labwork_date:
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
        break;

      case R.id.save_labwork_record:
        try {
          OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
          if (laboratory == null) {
           laboratory = new Laboratory();
          }
          laboratory.setLabName(nullifyEmptyString(labName.getText().toString()));
          laboratory.setProvider(nullifyEmptyString(provider.getText().toString()));
          laboratory.setNotes(nullifyEmptyString(note.getText().toString()));
          DateFormat format = DateFormat.getDateInstance();
          try {
            laboratory.setLabworkDate(format.parse(labworkDate.getText().toString()));
          } catch (ParseException e){
            Toast.makeText(getContext(), R.string.appt_date_required, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            break;
          }
          Bundle args = getArguments();
          int patientID = args.getInt(MainActivity.PATIENT_ID_KEY);
          Patient patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
              .queryForId(patientID);

          laboratory.setPatient(patient);
          helper.getLabDao().createOrUpdate(laboratory);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        getFragmentManager().popBackStack();
        break;

      case R.id.delete_labwork_record:
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setMessage(R.string.delete_toast).setTitle("");
        builder.setPositiveButton(R.string.delete_record, new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
            try {
              helper.getLabDao().delete(laboratory);
            } catch (SQLException e) {
              Toast.makeText(getContext(), R.string.unable_delete, Toast.LENGTH_LONG);
            }
            getActivity().getSupportFragmentManager().popBackStack();
          }
        });
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            //User clicked the Cancel Button
          }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        break;
      case R.id.cancel_labwork_record:
        getActivity().getSupportFragmentManager().popBackStack();
        break;
    }
  }
}

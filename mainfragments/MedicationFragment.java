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
import edu.cnm.deepdive.healthtracker.entities.Hospitalization;
import edu.cnm.deepdive.healthtracker.entities.Medication;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * A fragment subclass which allows patients to create, edit and delete a medication record.
 */
public class MedicationFragment extends Fragment implements Button.OnClickListener {

  /* ID for Medication entity*/
  public static final String MEDICATION_ID_KEY = "medicationId";
  /* The medication name  */
  private EditText medicationName;
  /* The dosage of medication */
  private EditText dose;
  /* The provider who prescribed the medication */
  private EditText provider;
  /* notes pertaining to medication */
  private EditText note;
  /* The date medication was started */
  private Button dateStarted;
  /* The date medication was stopped */
  private Button dateEnded;
  /* a patient instance */
  private Patient patient = null;
  /* a medication instance */
  private Medication medication = null;

  /**
   * Required empty public constructor
   */
  public MedicationFragment() {

  }


  @Override
  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    int patientId;
    int medicationId;
    if (getArguments() != null
        && (patientId = getArguments().getInt(MainActivity.PATIENT_ID_KEY, 0)) != 0) {
      try {

        patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
            .queryForId(patientId);
        medicationId = getArguments().getInt(MEDICATION_ID_KEY, 0);
        if (medicationId > 0) {
          medication = ((OrmInteraction) getActivity()).getHelper().getMedicationDao()
              .queryForId(medicationId);
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
    View view = inflater.inflate(R.layout.fragment_medication, container, false);
    Button addButton = view.findViewById(R.id.save_medication_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_medication_record);
    if (medication == null) {
      deleteButton.setEnabled(false);
    } else {
      deleteButton.setOnClickListener(this);
    }

    Button cancelButton = view.findViewById(R.id.cancel_medication_record);
    cancelButton.setOnClickListener(this);
    medicationName = view.findViewById(R.id.product);
    InputFilter[] oldFilters = medicationName.getFilters();
    InputFilter[] newFilters = new InputFilter[oldFilters.length + 1];
    System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
    newFilters[oldFilters.length] = new InputFilter.AllCaps();
    medicationName.setFilters(newFilters);

    provider = view.findViewById(R.id.provider);
    note = view.findViewById(R.id.note);
    dose = view.findViewById(R.id.dose);
    dateStarted = view.findViewById(R.id.date_started);
    dateEnded = view.findViewById(R.id.date_ended);
    dateStarted.setOnClickListener(this);
    dateEnded.setOnClickListener(this);
    if (medication != null) {
      medicationName.setText(emptyNullString(medication.getMedicationName()));
      provider.setText(emptyNullString(medication.getProvider()));
      note.setText(emptyNullString(medication.getNotes()));
      dose.setText(emptyNullString(medication.getDose()));
      dateStarted.setText(DateFormat.getDateInstance().format(medication.getStartDate()));
      if (medication.getStopDate() != null) {
        dateEnded.setText(DateFormat.getDateInstance().format(medication.getStopDate()));
      }


    } else {
      medicationName.requestFocus();
    }

    return view;

  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
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
          if (medication == null) {
            medication = new Medication();
          }
          medication.setMedicationName(nullifyEmptyString(medicationName.getText().toString()));
          medication.setDose(nullifyEmptyString(dose.getText().toString()));
          medication.setProvider(nullifyEmptyString(provider.getText().toString()));
          medication.setNotes(nullifyEmptyString(note.getText().toString()));
          DateFormat format = DateFormat.getDateInstance();
          try {
            medication.setStartDate(format.parse(dateStarted.getText().toString()));
          } catch (ParseException e) {
            Toast.makeText(getContext(), "Start date is a required field", Toast.LENGTH_LONG)
                .show();
            e.printStackTrace();
            break;
          }

          try {
            medication.setStopDate(format.parse(dateEnded.getText().toString()));
            if (medication.getStartDate().compareTo(medication.getStopDate()) > 0) {
              Toast.makeText(getContext(), "End date must be after start date", Toast.LENGTH_LONG)
                  .show();
              break;
            }
          } catch (ParseException e) {
            e.printStackTrace();
          }
          if (medication.getMedicationName() == null || medication.getDose() == null
              || medication.getProvider() == null) {
            Toast.makeText(getContext(),
                "Required input includes Medication Name, Dose, and Prescribing Physician",
                Toast.LENGTH_LONG).show();
            break;
          }
          Bundle args = getArguments();
          int patientID = args.getInt(MainActivity.PATIENT_ID_KEY);
          Patient patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
              .queryForId(patientID);
          //check to see if medication and start date are the same to avoid duplicate entries
          QueryBuilder queryBuilder = helper.getMedicationDao().queryBuilder();
          queryBuilder.where().eq("NAME", medication.getMedicationName()).and()
              .eq("START_DATE", medication.getStartDate()).and()
              //.eq("STOP_DATE", medication.getStopDate()).and()
              .eq("PROVIDER", medication.getProvider()).and()
              .eq("PATIENT_ID", patient).and()
              .eq("DOSE", medication.getDose());
          if (helper.getMedicationDao().query(queryBuilder.prepare()).size() > 0) {
            Toast.makeText(getContext(), "This record is already in patient's chart",
                Toast.LENGTH_LONG).show();
            return;
          }
          medication.setPatient(patient);
          if (medication.getId() != 0) {
            helper.getMedicationDao().update(medication);
          } else {
            helper.getMedicationDao().create(medication);
          }
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        getFragmentManager().popBackStack();
        break;
      case R.id.delete_medication_record:
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setMessage("Permanently delete this record?").setTitle("");
        builder.setPositiveButton("DELETE RECORD", new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
            try {
              helper.getMedicationDao().delete(medication);
            } catch (SQLException e) {
              Toast.makeText(getContext(), "Unable to delete", Toast.LENGTH_SHORT).show();
            }
            getActivity().getSupportFragmentManager().popBackStack();
          }
        });
        builder.setNegativeButton("CANCEL", new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            //User clicked the Cancel Button

          }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        break;
      case R.id.cancel_medication_record:
        getActivity().getSupportFragmentManager().popBackStack();
        break;

    }
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


}

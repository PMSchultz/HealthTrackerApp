package edu.cnm.deepdive.healthtracker.mainfragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
 *
 */
public class ImmunizationFragment extends Fragment implements Button.OnClickListener {

  public static final String IMMUNIZATION_ID_KEY = "immunizationId";

  private AutoCompleteTextView vaccine;
  private EditText provider;
  private EditText note;
  private Button dateAdministered;
  private Immunization immunization = null;
  private Patient patient = null;


  public ImmunizationFragment() {
    // Required empty public constructor
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int patientId;
    int immunizationId;
    if (getArguments() != null
        && (patientId = getArguments().getInt(MainActivity.PATIENT_ID_KEY, 0)) != 0) {
      try {

        patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
            .queryForId(patientId);
        immunizationId = getArguments().getInt(IMMUNIZATION_ID_KEY, 0);
        if (immunizationId > 0) {
          immunization = ((OrmInteraction) getActivity()).getHelper().getImmunizationDao()
              .queryForId(immunizationId);
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
    View view = inflater.inflate(R.layout.fragment_immunization, container, false);
    view.findViewById(R.id.date_administered).setOnClickListener(this);
    Button addButton = view.findViewById(R.id.save_immunization_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_immunization_record);
    deleteButton.setOnClickListener(this);
    if (immunization == null){
      deleteButton.setEnabled(false);
    }else {
      deleteButton.setOnClickListener(this);
    }
    Button cancelButton = view.findViewById(R.id.cancel_immunization_record);
    cancelButton.setOnClickListener(this);
    dateAdministered = view.findViewById(R.id.date_administered);
    dateAdministered.setOnClickListener(this);
    vaccine = view.findViewById(R.id.vaccine);
    provider = view.findViewById(R.id.provider);
    note = view.findViewById(R.id.note);
    if (immunization != null) {
      vaccine.setText(emptyNullString(immunization.getVaccine()));
      dateAdministered.setText(DateFormat.getDateInstance().format(immunization.getDate()));
      provider.setText(emptyNullString(immunization.getProvider()));
      note.setText(emptyNullString(immunization.getNotes()));

    }
    return view;
  }

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
          if (immunization == null) {
            immunization = new Immunization();
          }
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
          if (immunization.getId() != 0) {
            helper.getImmunizationDao().update(immunization);
          } else {
            helper.getImmunizationDao().create(immunization);
          }
        } catch (SQLException e) {
          throw new RuntimeException(e);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }

        getFragmentManager().popBackStack();
        break;

      case R.id.delete_immunization_record:
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setMessage("Permanently delete this record?").setTitle("");
        builder.setPositiveButton("DELETE RECORD", new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
            try {
              helper.getImmunizationDao().delete(immunization);
            } catch (SQLException e) {
              Toast.makeText(getContext(), "Unable to delete", Toast.LENGTH_SHORT);
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
      case R.id.cancel_immunization_record:
        getActivity().getSupportFragmentManager().popBackStack();
        break;
    }
  }

  public static String nullifyEmptyString(String string) {
    return (string.equals("") ? null : string);

  }

  public static String emptyNullString(String string) {
    return (string == null) ? "" : string;
  }

}




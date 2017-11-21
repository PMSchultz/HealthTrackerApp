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

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Allergy;
import edu.cnm.deepdive.healthtracker.entities.Hospitalization;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;

/**
 *
 */
public class HospitalizationFragment extends Fragment implements Button.OnClickListener {


  public static final String HOSPITALIZATION_ID_KEY = "hospitalizationId";

  private EditText reason;
  private EditText provider;
  private Button admitDate;
  private Button dischargeDate;
  private EditText note;
  private EditText hospital;
  private Patient patient = null;
  private Hospitalization hospitalization = null;


  public HospitalizationFragment() {
    // Required empty public constructor
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int patientId;
    int hospitalizationId;
    if (getArguments() != null
        && (patientId = getArguments().getInt(MainActivity.PATIENT_ID_KEY, 0)) != 0) {
      try {

        patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
            .queryForId(patientId);
        hospitalizationId = getArguments().getInt(HOSPITALIZATION_ID_KEY, 0);
        if (hospitalizationId > 0) {
          hospitalization = ((OrmInteraction) getActivity()).getHelper().getHospitalizationDao()
              .queryForId(hospitalizationId);
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
    View view = inflater.inflate(R.layout.fragment_hospitalization, container, false);
    Button addButton = view.findViewById(R.id.save_hospital_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_hospital_record);
    deleteButton.setOnClickListener(this);
    if (hospitalization == null) {
      deleteButton.setEnabled(false);
    } else {
      deleteButton.setOnClickListener(this);
    }
    Button cancelButton = view.findViewById(R.id.cancel_hospital_record);
    cancelButton.setOnClickListener(this);
    reason = view.findViewById(R.id.reason);
    provider = view.findViewById(R.id.provider);
    note = view.findViewById(R.id.note);
    hospital = view.findViewById(R.id.hospital);
    admitDate = view.findViewById(R.id.admit_date);
    dischargeDate = view.findViewById(R.id.discharge_date);
    admitDate.setOnClickListener(this);
    dischargeDate.setOnClickListener(this);
    if (hospitalization != null) {
      hospital.setText(emptyNullString(hospitalization.getHospital()));
      reason.setText(emptyNullString(hospitalization.getReason()));
      provider.setText(emptyNullString(hospitalization.getProvider()));
      note.setText(emptyNullString(hospitalization.getNotes()));
      admitDate.setText(DateFormat.getDateInstance().format(hospitalization.getAdmitDate()));
      if (hospitalization.getDischargeDate() != null) {
        dischargeDate.setText(DateFormat.getDateInstance()
            .format(hospitalization.getDischargeDate()));
      }
    }
    return view;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }


  @Override
  public void onClick(View view) {
    DatePickerFragment datePickerFragment;
    Bundle bundle;
    switch (view.getId()) {

      case R.id.admit_date:
        datePickerFragment = new DatePickerFragment();
        bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
        break;
      case R.id.discharge_date:
        datePickerFragment = new DatePickerFragment();
        bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
        break;
      case R.id.save_hospital_record:
        try {
          OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
          if (hospitalization == null) {
            hospitalization = new Hospitalization();
          }
          hospitalization.setReason(nullifyEmptyString(reason.getText().toString()));
          hospitalization.setHospital(nullifyEmptyString(hospital.getText().toString()));
          hospitalization.setProvider(nullifyEmptyString(provider.getText().toString()));
          hospitalization.setNotes(nullifyEmptyString(note.getText().toString()));
          DateFormat format = DateFormat.getDateInstance();
          try {
            hospitalization.setAdmitDate(format.parse(admitDate.getText().toString()));
            if (hospitalization.getAdmitDate().toString().trim().isEmpty()){

            }
          }catch (ParseException e) {
            Toast.makeText(getContext(), "Admit date is required", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            break;
          }

          try {
            hospitalization.setDischargeDate(format.parse(dischargeDate.getText().toString()));
            if (hospitalization.getAdmitDate().compareTo(hospitalization.getDischargeDate()) > 0){
              Toast.makeText(getContext(), "Discharge date must be after Admit date", Toast.LENGTH_LONG).show();
              break;
            }
          } catch (ParseException e) {
            e.printStackTrace();
          }
          if(hospitalization.getAdmitDate() == null || hospitalization.getReason() == null || hospitalization.getHospital() == null){
            Toast.makeText(getContext(), "Required input includes reason, hospital and admit date", Toast.LENGTH_LONG).show();
            break;
          }
          Bundle args = getArguments();

          int patientID = args.getInt(MainActivity.PATIENT_ID_KEY);
          Patient patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
              .queryForId(patientID);
          hospitalization.setPatient(patient);
          if (hospitalization.getId() != 0) {
            helper.getHospitalizationDao().update(hospitalization);
          } else {
            helper.getHospitalizationDao().create(hospitalization);
          }
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }

        getFragmentManager().popBackStack();
        break;
      case R.id.delete_hospital_record:
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setMessage("Permanently delete this record?").setTitle("");
        builder.setPositiveButton("DELETE RECORD", new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
            try {
              helper.getHospitalizationDao().delete(hospitalization);
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
      case R.id.cancel_hospital_record:
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



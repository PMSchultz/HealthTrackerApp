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
import edu.cnm.deepdive.healthtracker.entities.Hospitalization;
import edu.cnm.deepdive.healthtracker.entities.OfficeVisit;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * A fragment subclass which allows patients to create, edit and delete an office visit record
 */
public class OfficeVisitFragment extends Fragment implements Button.OnClickListener {

  /* ID for Office Visit from entity*/
  public static final String OFFICE_VISIT_ID_KEY = "officeVisitId";
  /* User input of the reason for the office visit */
  private EditText reason;
  /* The date of the office visit*/
  private Button visitDate;
  /* The name of the provider seen during the office visit */
  private EditText provider;
  /* height of the patient */
  private EditText height;
  /* weight of the patient */
  private EditText weight;
  /* blood pressure taken during office visit */
  private EditText bloodPressure;
  /* User input of any notes regarding office visit */
  private EditText note;
  /*  an instance of office visit*/
  private OfficeVisit officeVisit = null;
  /*  an instance of the patient*/
  private Patient patient = null;

  /**
   * Required empty public constructor
   */
  public OfficeVisitFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int patientId;
    int officeVisitId;
    if (getArguments() != null
        && (patientId = getArguments().getInt(MainActivity.PATIENT_ID_KEY, 0)) != 0) {
      try {

        patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
            .queryForId(patientId);
        officeVisitId = getArguments().getInt(OFFICE_VISIT_ID_KEY, 0);
        if (officeVisitId > 0) {
          officeVisit = ((OrmInteraction) getActivity()).getHelper().getOfficeVisitDao()
              .queryForId(officeVisitId);
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_office_visit, container, false);
    Button addButton = view.findViewById(R.id.save_office_visit_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_office_visit_record);
    deleteButton.setOnClickListener(this);
    if (officeVisit == null) {
      deleteButton.setEnabled(false);
    } else {
      deleteButton.setOnClickListener(this);
    }
    Button cancelButton = view.findViewById(R.id.cancel_office_visit_record);
    cancelButton.setOnClickListener(this);
    reason = view.findViewById(R.id.reason);
    provider = view.findViewById(R.id.provider);
    note = view.findViewById(R.id.note);
    height = view.findViewById(R.id.height_input);
    weight = view.findViewById(R.id.weight_input);
    bloodPressure = view.findViewById(R.id.blood_pressure_input);
    visitDate = view.findViewById(R.id.visit_date);
    visitDate.setOnClickListener(this);
    if (officeVisit != null) {
      reason.setText(emptyNullString(officeVisit.getReason()));
      visitDate.setText(DateFormat.getDateInstance().format(officeVisit.getDate()));
      provider.setText(emptyNullString(officeVisit.getProvider()));
      height.setText(emptyNullString(officeVisit.getHeight()));
      weight.setText(emptyNullString(officeVisit.getWeight()));
      bloodPressure.setText(emptyNullString(officeVisit.getBloodPressure()));
      note.setText(emptyNullString(officeVisit.getNotes()));

    } else {
      reason.requestFocus();
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
      case R.id.visit_date:
        datePickerFragment = new DatePickerFragment();
        bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
        break;
      case R.id.save_office_visit_record:
        try {
          OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
          if (officeVisit == null) {
            officeVisit = new OfficeVisit();
          }
          officeVisit.setReason(nullifyEmptyString(reason.getText().toString()));
          officeVisit.setProvider(nullifyEmptyString(provider.getText().toString()));
          officeVisit.setHeight(nullifyEmptyString(height.getText().toString()));
          officeVisit.setWeight(nullifyEmptyString(weight.getText().toString()));
          officeVisit.setBloodPressure(nullifyEmptyString(bloodPressure.getText().toString()));
          officeVisit.setNotes(nullifyEmptyString(note.getText().toString()));
          DateFormat format = DateFormat.getDateInstance();
          try {
            officeVisit.setDate(format.parse(visitDate.getText().toString()));
            if (officeVisit.getDate().toString().trim().isEmpty()) {

            }
          } catch (ParseException e) {
            Toast.makeText(getContext(), getString(R.string.appt_date_required), Toast.LENGTH_LONG)
                .show();
            e.printStackTrace();
            break;
          }
          if (officeVisit.getReason() == null || officeVisit.getProvider() == null) {
            Toast.makeText(getContext(), getString(R.string.office_required_input),
                Toast.LENGTH_LONG).show();
            break;
          }
          Bundle args = getArguments();
          int patientID = args.getInt(MainActivity.PATIENT_ID_KEY);
          Patient patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
              .queryForId(patientID);
          officeVisit.setPatient(patient);
          if (officeVisit.getId() != 0) {
            helper.getOfficeVisitDao().update(officeVisit);
          } else {
            helper.getOfficeVisitDao().create(officeVisit);
          }
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        getFragmentManager().popBackStack();
        break;
      case R.id.delete_office_visit_record:
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setMessage(R.string.delete_toast).setTitle("");
        builder.setPositiveButton(R.string.delete_record, new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
            try {
              helper.getOfficeVisitDao().delete(officeVisit);
            } catch (SQLException e) {
              Toast.makeText(getContext(), R.string.unable_delete, Toast.LENGTH_SHORT);
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
      case R.id.cancel_office_visit_record:
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

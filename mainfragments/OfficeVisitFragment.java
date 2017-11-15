package edu.cnm.deepdive.healthtracker.mainfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
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
 *
 */
public class OfficeVisitFragment extends Fragment implements Button.OnClickListener {

  public static final String OFFICE_VISIT_ID_KEY = "officeVisitId";

  private EditText reason;
  private Button visitDate;
  private EditText provider;
  private EditText height;
  private EditText weight;
  private EditText bloodPressure;
  private EditText note;
  private OfficeVisit officeVisit = null;
  private Patient patient = null;


  public OfficeVisitFragment() {
    // Required empty public constructor
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
    Button cancelButton = view.findViewById(R.id.cancel_office_visit_record);
    cancelButton.setOnClickListener(this);
    reason = view.findViewById(R.id.reason);
    provider = view.findViewById(R.id.provider);
    note = view.findViewById(R.id.note);
    height = view.findViewById(R.id.height_input);
    weight = view.findViewById(R.id.weight_input);
    bloodPressure = view.findViewById(R.id.blood_pressure_input);
    visitDate= view.findViewById(R.id.visit_date);
    visitDate.setOnClickListener(this);
    if (officeVisit != null){
      reason.setText(emptyNullString(officeVisit.getReason()));
      visitDate.setText(DateFormat.getDateInstance().format(officeVisit.getDate()));
      provider.setText(emptyNullString(officeVisit.getProvider()));
      height.setText(emptyNullString(officeVisit.getHeight()));
      weight.setText(emptyNullString(officeVisit.getWeight()));
      bloodPressure.setText(emptyNullString(officeVisit.getBloodPressure()));
      note.setText(emptyNullString(officeVisit.getNotes()));

  }
    return view;
  }

  @Override
  public void onAttach(Context context)
    {
    super.onAttach(context);
  }


  @Override
  public void onClick(View view) {
    DatePickerFragment datePickerFragment;
    Bundle bundle;
    switch (view.getId()) {
      case R.id.save_office_visit_record:
        try {
          OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
          OfficeVisit officeVisit = new OfficeVisit();
          officeVisit.setReason(nullifyEmptyString(reason.getText().toString()));
          officeVisit.setProvider(nullifyEmptyString(provider.getText().toString()));
          officeVisit.setHeight(nullifyEmptyString(height.getText().toString()));
          officeVisit.setWeight(nullifyEmptyString(weight.getText().toString()));
          officeVisit.setBloodPressure(nullifyEmptyString(bloodPressure.getText().toString()));
          officeVisit.setNotes(nullifyEmptyString(note.getText().toString()));
          DateFormat format = DateFormat.getDateInstance();
          officeVisit.setDate(format.parse(visitDate.getText().toString()));
          Bundle args = getArguments();
          int patientID = args.getInt(MainActivity.PATIENT_ID_KEY);
          Patient patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
              .queryForId(patientID);
          officeVisit.setPatient(patient);
          helper.getOfficeVisitDao().create(officeVisit);

        } catch (SQLException e) {
          throw new RuntimeException(e);
        } catch (ParseException e){
          throw new RuntimeException(e);
        }
        getFragmentManager().popBackStack();
        break;
      case R.id.delete_office_visit_record:
        //TODO pop up message "Are you sure?"
        break;
      case R.id.cancel_office_visit_record:
        //TODO return to former screen
        break;
      case R.id.visit_date:
        datePickerFragment  = new DatePickerFragment();
        bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
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

package edu.cnm.deepdive.healthtracker.mainfragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * A fragment subclass which allows patients to create, edit and delete an allergy
 */
public class AllergyFragment extends Fragment implements Button.OnClickListener,
    OnItemSelectedListener {

  /* ID from Allergy entity*/
  public static final String ALLERGY_ID_KEY = "allergy_id";
  /* an allergy instance */
  private Allergy allergy = null;
  /*Patient selected */
  private Patient patient = null;
  /* Spinner which uses allergyTypes String[]*/
  private Spinner spinner;
  /* User input of allergy text */
  private EditText allergyText;
  /* Array list of allergy types used on spinner */
  private String[] allergyTypes;


  /**
   * Required empty public constructor
   */
  public AllergyFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int patientId;
    int allergyId;
    if (getArguments() != null
        && (patientId = getArguments().getInt(MainActivity.PATIENT_ID_KEY, 0)) != 0) {
      try {

        patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
            .queryForId(patientId);
        allergyId = getArguments().getInt(ALLERGY_ID_KEY, 0);
        if (allergyId > 0) {
          allergy = ((OrmInteraction) getActivity()).getHelper().getAllergyDao()
              .queryForId(allergyId);
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }

    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_allergy, container, false);
    allergyText = view.findViewById(R.id.allergy);
    InputFilter[] oldFilters = allergyText.getFilters();
    InputFilter[] newFilters = new InputFilter[oldFilters.length + 1];
    System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
    newFilters[oldFilters.length] = new InputFilter.AllCaps();
    allergyText.setFilters(newFilters);
    allergyTypes = getResources().getStringArray(R.array.allergy_types);
    spinner = view.findViewById(R.id.allergy_spinner);
    Button addButton = view.findViewById(R.id.save_allergy_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_allergy_record);
    deleteButton.setOnClickListener(this);
    if (allergy == null) {
      deleteButton.setEnabled(false);
    } else {
      deleteButton.setOnClickListener(this);
    }
    Button cancelButton = view.findViewById(R.id.cancel_allergy_record);
    cancelButton.setOnClickListener(this);
    spinner.setOnItemSelectedListener(this);
    if (allergy != null) {
      spinner.setSelection(Arrays.asList(allergyTypes).indexOf(allergy.getAllergyType()));
      allergyText.setText(allergy.getAllergyName());
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

    switch (view.getId()) {
      case R.id.save_allergy_record:
        try {
          OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
          if (allergy == null) {
            allergy = new Allergy();
          }
          allergy.setAllergyType(spinner.getSelectedItem().toString());
          if (allergyText.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "At least one allergy type must be entered",
                Toast.LENGTH_LONG).show();
            break;
          }
          Bundle args = getArguments();
          int patientID = args.getInt(MainActivity.PATIENT_ID_KEY);
          Patient patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
              .queryForId(patientID);
          allergy.setAllergyName(allergyText.getText().toString());
          QueryBuilder queryBuilder = helper.getAllergyDao().queryBuilder();
          queryBuilder.where().eq("ALLERGY_NAME", allergy.getAllergyName()).and()
              .eq("PATIENT_ID", patientID).and()
              .eq("ALLERGY_TYPE", allergy.getAllergyType());
          if (helper.getAllergyDao().query(queryBuilder.prepare()).size() > 0) {
            Toast.makeText(getContext(), getString(R.string.duplicate_allergy),
                Toast.LENGTH_LONG).show();
            break;
          }
          allergy.setPatient(patient);
          if (allergy.getId() != 0) {
            helper.getAllergyDao().update(allergy);
          } else {
            helper.getAllergyDao().create(allergy);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        getFragmentManager().popBackStack();
        break;
      case R.id.delete_allergy_record:
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setMessage("Permanently delete this record?").setTitle("");
        builder.setPositiveButton("DELETE RECORD", new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
            try {
              helper.getAllergyDao().delete(allergy);
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
      case R.id.cancel_allergy_record:
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

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    if (spinner.getSelectedItem().toString().equals("Latex")) {
      allergyText.setText("Latex");
      allergyText.setEnabled(false); //do not accept any user input in text field
    } else {
      allergyText.setEnabled(true);
      if (allergyText.getText().toString().equals("Latex")) {
        allergyText.setText("");
      }
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}

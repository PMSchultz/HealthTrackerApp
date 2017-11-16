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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import edu.cnm.deepdive.healthtracker.MainActivity;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Allergy;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper;
import edu.cnm.deepdive.healthtracker.helpers.OrmHelper.OrmInteraction;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@lerface to handle interaction events. Use the  factory method to create an instance of this
 * fragment.
 */
public class AllergyFragment extends Fragment implements Button.OnClickListener {

  public static final String ALLERGY_ID_KEY = "allergy_id";
  // the fragment initialization parameters,
  private EditText drugAllergy;
  private EditText foodAllergy;
  private EditText seasonalAllergy;
  private EditText animalAllergy;
  private CheckBox latexAllergy;
  private Allergy allergy = null;
  private Patient patient = null;

//  private OnFragmentInteractionListener mListener;

  public AllergyFragment() {
    // Required empty public constructor
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
    Button addButton = view.findViewById(R.id.save_allergy_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_allergy_record);
    deleteButton.setOnClickListener(this);
    if (allergy == null){
      deleteButton.setEnabled(false);
    }else {
      deleteButton.setOnClickListener(this);
    }
    Button cancelButton = view.findViewById(R.id.cancel_allergy_record);
    cancelButton.setOnClickListener(this);
    drugAllergy = view.findViewById(R.id.drug_allergy);
    foodAllergy = view.findViewById(R.id.food_allergy);
    seasonalAllergy = view.findViewById(R.id.seasonal_allergy);
    animalAllergy = view.findViewById(R.id.animal_allergy);
    latexAllergy = view.findViewById(R.id.latex_allergy);
    if (allergy != null) {
      drugAllergy.setText(emptyNullString(allergy.getMedAllergy()));
      foodAllergy.setText(emptyNullString(allergy.getFoodAllergy()));
      animalAllergy.setText(emptyNullString(allergy.getAnimalAllergy()));
      seasonalAllergy.setText(emptyNullString(allergy.getSeasonalAllergy()));
      latexAllergy.setChecked(allergy.getLatexAllergy());
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
          allergy.setMedAllergy(nullifyEmptyString(drugAllergy.getText().toString()));
          allergy.setFoodAllergy(nullifyEmptyString(foodAllergy.getText().toString()));
          allergy.setAnimalAllergy(nullifyEmptyString(animalAllergy.getText().toString()));
          allergy.setSeasonalAllergy(nullifyEmptyString(seasonalAllergy.getText().toString()));
          allergy.setLatexAllergy(latexAllergy.isChecked());
          Bundle args = getArguments();
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
        //TODO return to former screen
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

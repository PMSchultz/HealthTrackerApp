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
import android.widget.CheckBox;
import android.widget.EditText;
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
 * {@lerface to handle interaction events. Use
 * the  factory method to create an instance of this fragment.
 */
public class AllergyFragment extends Fragment implements Button.OnClickListener {

  // the fragment initialization parameters,
  private EditText drugAllergy;
  private EditText foodAllergy;
  private EditText seasonalAllergy;
  private EditText animalAllergy;
  private CheckBox latexAllergy;


//  private OnFragmentInteractionListener mListener;

  public AllergyFragment() {
    // Required empty public constructor
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    if (getArguments() != null) {
//
//    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_allergy, container, false);
    Button addButton = view.findViewById(R.id.save_allergy_record);
    addButton.setOnClickListener(this);
    Button deleteButton = view.findViewById(R.id.delete_allergy_record);
    deleteButton.setOnClickListener(this);
    Button cancelButton = view.findViewById(R.id.cancel_allergy_record);
    cancelButton.setOnClickListener(this);
    drugAllergy = view.findViewById(R.id.drug_allergy);
    foodAllergy = view.findViewById(R.id.food_allergy);
    seasonalAllergy = view.findViewById(R.id.seasonal_allergy);
    animalAllergy = view.findViewById(R.id.animal_allergy);
    latexAllergy = view.findViewById(R.id.latex_allergy);
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
  public void onDetach() {
    super.onDetach();
    //mListener = null;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.save_allergy_record:
        try {
          OrmHelper helper = ((OrmInteraction) getActivity()).getHelper();
          Allergy allergy = new Allergy();
          allergy.setMedAllergy(nullifyEmptyString(drugAllergy.getText().toString()));
          allergy.setFoodAllergy(nullifyEmptyString(foodAllergy.getText().toString()));
          allergy.setAnimalAllergy(nullifyEmptyString(animalAllergy.getText().toString()));
          allergy.setSeasonalAllergy(nullifyEmptyString(seasonalAllergy.getText().toString()));
          allergy.setLatexAllergy(latexAllergy.isChecked());
          Bundle args = getArguments();
          int patientID = args.getInt(MainActivity.PATIENT_ID_KEY);
          Patient patient = ((OrmInteraction) getActivity()).getHelper().getPatientDao()
                  .queryForId(patientID);
          allergy.setPatient(patient);
          helper.getAllergyDao().create(allergy);

          //put allergy input into patient chart
        } catch (SQLException e) {
          e.printStackTrace();
        }
        getFragmentManager().popBackStack();
        break;
      case R.id.delete_allergy_record:
        //TODO pop up message "Are you sure?"
        break;
      case R.id.cancel_allergy_record:
        //TODO return to former screen
        break;

    }
  }

  public static String nullifyEmptyString(String string){
    return  (string.equals("") ? null: string);

  }
  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity. <p> See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating with
   * Other Fragments</a> for more information.
   */
//  public interface OnFragmentInteractionListener {
//
//    // TODO: Update argument type and name
//    void onFragmentInteraction(Uri uri);
//  }
}

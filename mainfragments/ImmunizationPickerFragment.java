package edu.cnm.deepdive.healthtracker.mainfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import edu.cnm.deepdive.healthtracker.R;
import edu.cnm.deepdive.healthtracker.entities.Allergy;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link ImmunizationPickerFragment.OnFragmentInteractionListener} interface to handle interaction
 * events. Use the {@link ImmunizationPickerFragment#newInstance} factory method to create an
 * instance of this fragment.
 */
public class ImmunizationPickerFragment extends Fragment implements OnItemSelectedListener,
    OnClickListener{




  public ImmunizationPickerFragment() {
    // Required empty public constructor
  }



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_immunization_picker,
        container, false);

    return view;
    // Inflate the layout for this fragment

  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {

    }






  @Override
  public void onClick(View view) {



//    switch(spinner.getSelectedItemPosition()){
//      case 0:
//        allergy.setLatexAllergy(true);
//
//        break;
//      case 1:
//        allergy.setMedAllergy(allergyType.getText().toString());
//        break;
//      case 2:
//        allergy.setFoodAllergy(allergyType.getText().toString());
//        break;
//      case 3:
//        allergy.setSeasonalAllergy(allergyType.getText().toString());
//        break;
//        case 4:
//        allergy.setAnimalAllergy(allergyType.getText().toString());
//        break;
//
//    }
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

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

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


  /**
   * Required empty public constructor
   */
  public ImmunizationPickerFragment() {

  }


  /**
   *
   * @param savedInstanceState
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    }

  /**
   *
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_immunization_picker,
        container, false);
    return view;
  }

  /**
   *
   * @param uri
   */
  public void onButtonPressed(Uri uri) {

    }


  /**
   *
   * @param view
   */
  @Override
  public void onClick(View view) {

  }

  /**
   *
   * @param adapterView
   * @param view
   * @param i
   * @param l
   */
  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


  }

  /**
   *
   * @param adapterView
   */
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

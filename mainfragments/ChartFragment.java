package edu.cnm.deepdive.healthtracker.mainfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;
import edu.cnm.deepdive.healthtracker.R;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link  interface to handle interaction events. Use the {@link ChartFragment#newInstance} factory
 * method to create an instance of this fragment.
 */
public class ChartFragment extends Fragment implements Button.OnClickListener {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  //private OnFragmentInteractionListener mListener;

  public ChartFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment ChartFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ChartFragment newInstance(String param1, String param2) {
    ChartFragment fragment = new ChartFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }


  //TODO attach onClick Listeners to buttons
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_chart, container, false);
    Button addButton = inflate.findViewById(R.id.add_chart_record);
    addButton.setOnClickListener(this);
    return inflate;
  }

//  // TODO: Rename method, update argument and hook method into UI event
//  public void onButtonPressed(Uri uri) {
//    if (mListener != null) {
//      mListener.onFragmentInteraction(uri);
//    }
//  }

//  @Override
//  public void onAttach(Context context) {
//    super.onAttach(context);
//    if (context instanceof OnFragmentInteractionListener) {
//      mListener = (OnFragmentInteractionListener) context;
//    } else {
//      throw new RuntimeException(context.toString()
//          + " must implement OnFragmentInteractionListener");
//    }
//  }
//
//  @Override
//  public void onDetach() {
//    super.onDetach();
//    mListener = null;
//  }

  @Override
  public void onClick(View view) {
    int navigationClicked = getArguments().getInt("Record Type");
    FragmentManager fragmentManager = getFragmentManager();
    switch (view.getId()) {
      case R.id.add_chart_record:
        switch (navigationClicked) {
          case R.id.nav_medications:
            fragmentManager.beginTransaction().replace(R.id.content_panel, new MedicationFragment())
                .commit();
            break;
          case R.id.nav_immunizations:
            fragmentManager.beginTransaction()
                .replace(R.id.content_panel, new ImmunizationFragment())
                .commit();
            break;
          case R.id.nav_hospitalizations:
            fragmentManager.beginTransaction()
                .replace(R.id.content_panel, new HospitalizationFragment())
                .commit();
            break;
          case R.id.nav_allergies:
            fragmentManager.beginTransaction().replace(R.id.content_panel, new AllergyFragment())
                .commit();
            break;
          case R.id.nav_office_visits:
            fragmentManager.beginTransaction()
                .replace(R.id.content_panel, new OfficeVisitFragment())
                .commit();
            break;
          case R.id.nav_lab_results:
            fragmentManager.beginTransaction().replace(R.id.content_panel, new LabFragment())
                .commit();
            break;
          case R.id.nav_imaging_results:
            fragmentManager.beginTransaction().replace(R.id.content_panel, new ImagingFragment())
                .commit();
            break;
          //TODO repeat for all cases
        }
        //TODO pop up add chart record
      case R.id.edit_chart_record:
        //TODO pop up message "Are you sure?"
      case R.id.cancel_chart_record:
        //TODO return to former screen
    }
  }

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity. <p> See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating with
   * Other Fragments</a> for more information.
   */
//  public OnFragmentInteractionListener {
//
//    // TODO: Update argument type and name
//    void onFragmentInteraction(Uri uri);
//  }
//}
}

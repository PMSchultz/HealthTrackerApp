package edu.cnm.deepdive.healthtracker.mainfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import edu.cnm.deepdive.healthtracker.R;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@linkto handle interaction
 * events. Use the {@link ImmunizationFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class ImmunizationFragment extends Fragment implements Button.OnClickListener {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

//  private OnFragmentInteractionListener mListener;

  public ImmunizationFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment ImmunizationFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ImmunizationFragment newInstance(String param1, String param2) {
    ImmunizationFragment fragment = new ImmunizationFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_immunization, container, false);
    inflate.findViewById(R.id.date_administered).setOnClickListener(this);
    return inflate;
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

//  @Override
//  public void onDetach() {
//    super.onDetach();
//    mListener = null;
//  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.save_immunization_record:
        //TODO pop up add chart record
        break;
      case R.id.delete_immunization_record:
        //TODO pop up message "Are you sure?"
        break;
      case R.id.cancel_immunization_record:
        //TODO return to former screen
        break;
      case R.id.date_administered:

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getFragmentManager(), "datePicker");
        break;
    }
  }
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


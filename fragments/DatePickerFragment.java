package edu.cnm.deepdive.healthtracker.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import edu.cnm.deepdive.healthtracker.R;
import java.util.Calendar;


/**
 *
 *
 *
 */

public class DatePickerFragment extends DialogFragment implements
    DatePickerDialog.OnDateSetListener {

  public static final String DATE_PICKER_FIELD_ID = "button_id";
  public static final String DATE_PICKER_TAG = "date_picker";
private int fieldId;

  public Dialog onCreateDialog(Bundle savedInstanceState) {
    //use the current date as the default date
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    fieldId = getArguments().getInt(DATE_PICKER_FIELD_ID);

    //create a new instance of DatePickerDialog and return it
    return new DatePickerDialog(getActivity(), this,year, month, day);
  }

  @Override
  public void onStart() {
    super.onStart();

  }


  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {

    ((EditText)getActivity().findViewById(fieldId)).setText((month + 1) + "/" + day + "/" + year);
  }

  public static void showDialog(AppCompatActivity context, View view) {
    DatePickerFragment datePickerFragment = new DatePickerFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());

    datePickerFragment.setArguments(bundle);
    datePickerFragment.show(context.getSupportFragmentManager(), DATE_PICKER_TAG);
  }

}

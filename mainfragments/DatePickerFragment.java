package edu.cnm.deepdive.healthtracker.mainfragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import java.text.DateFormat;
import java.util.Calendar;


/**
 * DatePickerFragment is a fragment that is used for all instances that require a
 * date to be entered by the user. The fragment is attached to buttons
 */

public class DatePickerFragment extends DialogFragment implements
    DatePickerDialog.OnDateSetListener {

  /* sets DatePicker to button */
  public static final String DATE_PICKER_FIELD_ID = "button_id";
  /*   */
  public static final String DATE_PICKER_TAG = "date_picker";
  /*  */
  private int fieldId;

  /**
   * Uses the current date as the default date
   *
   * @return creates a new instance of DatePickerDialog defaulting to the current date
   */
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    fieldId = getArguments().getInt(DATE_PICKER_FIELD_ID);

    return new DatePickerDialog(getActivity(), this, year, month, day);
  }


  @Override
  public void onStart() {
    super.onStart();
  }


  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    DateFormat format = DateFormat.getDateInstance();

    ((Button) getActivity().findViewById(fieldId)).setText(format.format(calendar.getTime()));
  }

  /**
   *
   * @param context
   * @param view
   */
  public static void showDialog(AppCompatActivity context, View view) {
    DatePickerFragment datePickerFragment = new DatePickerFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(DatePickerFragment.DATE_PICKER_FIELD_ID, view.getId());

    datePickerFragment.setArguments(bundle);
    datePickerFragment.show(context.getSupportFragmentManager(), DATE_PICKER_TAG);
  }
}

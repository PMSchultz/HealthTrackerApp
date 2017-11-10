package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;

public class Imaging {

  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;

}

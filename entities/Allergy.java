package edu.cnm.deepdive.healthtracker.entities;

import android.text.Html;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

@DatabaseTable(tableName = "ALLERGY")
/**
 * Creates ALLERGY entity
 */
public class Allergy {

  /* Links database table name to Allergy class and provides ID key*/
  @DatabaseField(columnName = "ALLERGY_ID", generatedId = true)
  private int id;
  /* Links patient ID to patient instance*/
  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, uniqueCombo = true, foreign = true,
      foreignAutoRefresh = true)
  private Patient patient;

  /* Required allergy type input */
  @DatabaseField(columnName = "ALLERGY_TYPE", canBeNull = false, uniqueCombo = false)
  private String allergyType;
  /* User input into editText field of allergy name and checks database for duplicates*/
  @DatabaseField(columnName = "ALLERGY_NAME", uniqueCombo = true)
  private String allergyName;
  /* User input into editText field of notes */
  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  /* returns Allergy ID*/
  public int getId() {
    return id;
  }

  /* Getter for patient */
  public Patient getPatient() {
    return patient;
  }

  /* Setter for patient */
  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  /* Getter for notes*/
  public String getNotes() {
    return notes;
  }

  /* Setter for notes*/
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /* Getter for allergy type*/
  public String getAllergyType() {
    return allergyType;
  }

  /* Setter for allergy type*/
  public void setAllergyType(String allergyType) {
    this.allergyType = allergyType;
  }

  /* Getter for allergy name */
  public String getAllergyName() {
    return allergyName;
  }

  /* Setter for allergy name*/
  public void setAllergyName(String allergyName) {
    this.allergyName = allergyName;
  }

  @Override
  public String toString() {
    return "<b>" + allergyType + "</b> : " + allergyName;

  }
}


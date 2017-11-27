package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "MEDICATION")
/**
 * Creates MEDICATION entity
 */
public class Medication {

  /* Links database table name to Medication class with medication id key*/
  @DatabaseField(columnName = "MEDICATION_ID", generatedId = true)
  private int id;
  /* Name of medication*/
  @DatabaseField(columnName = "NAME", canBeNull = false)
  private String medicationName;
  /* Medication dose*/
  @DatabaseField(columnName = "DOSE", canBeNull = false)
  private String dose;


  /* Links patient ID to medication record*/
  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;
  /* Date that medication was started*/
  @DatabaseField(columnName = "START_DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date startDate;
  /* Date that medication was stopped*/
  @DatabaseField(columnName = "STOP_DATE",
      format = "yyyy-MM-dd", canBeNull = true)
  private Date stopDate;

  /* The physician/provider who prescribed the medication*/
  @DatabaseField(columnName = "PROVIDER", canBeNull = true)
  private String provider;
  /* Notes regarding medication */
  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;


  /* Getter for medication id key*/
  public int getId() {
    return id;
  }

  /* Getter for medication name*/
  public String getMedicationName() {
    return medicationName;
  }

  /* Setter for medication name*/
  public void setMedicationName(String medicationName) {
    this.medicationName = medicationName;
  }

  /* Getter for dose of medication*/
  public String getDose() {
    return dose;
  }

  /* Setter for dose of medication*/
  public void setDose(String dose) {
    this.dose = dose;
  }

  /* Getter for date medication started*/
  public Date getStartDate() {
    return startDate;
  }

  /* Setter for date medication started*/
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  /* Getter for date medication stopped*/
  public Date getStopDate() {
    return stopDate;
  }

  /* Setter for date medication stopped*/
  public void setStopDate(Date stopDate) {
    this.stopDate = stopDate;
  }

  /* Getter for prescribing provider*/
  public String getProvider() {
    return provider;
  }

  /* Setter for prescribing provider*/
  public void setProvider(String provider) {
    this.provider = provider;
  }

  /* Getter for notes regarding medication*/
  public String getNotes() {
    return notes;
  }

  /* Setter for notes regarding medication*/
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /* Getter for patient associated with medication record*/
  public Patient getPatient() {
    return patient;
  }

  /* Setter for patient associated with medication record*/
  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @Override
  public String toString() {
    DateFormat format = new SimpleDateFormat("M/d/yy");
    return "<b>" + medicationName + "</b>" + "    " + ((dose != null) ? (dose) : "")
        + "   Start date: " + format
        .format(startDate)
        + ((stopDate != null) ? ("    Stop date: " + format.format(stopDate)) : "");
  }
}

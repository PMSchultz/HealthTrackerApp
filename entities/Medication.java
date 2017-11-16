package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "MEDICATION")

public class Medication {

  @DatabaseField(columnName = "MEDICATION_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "NAME", canBeNull = false)
  private String medicationName;

  @DatabaseField(columnName = "DOSE", canBeNull = false)
  private String dose;



  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;

  @DatabaseField(columnName = "START_DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date startDate;

  @DatabaseField(columnName = "STOP_DATE",
      format = "yyyy-MM-dd", canBeNull = true)
  private Date stopDate;


  @DatabaseField(columnName = "PROVIDER", canBeNull = true)
  private String provider;

  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;



  public int getId() {
    return id;
  }

  public String getMedicationName() {
    return medicationName;
  }

  public void setMedicationName(String medicationName) {
    this.medicationName = medicationName;
  }

  public String getDose() {
    return dose;
  }

  public void setDose(String dose) {
    this.dose = dose;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getStopDate() {
    return stopDate;
  }

  public void setStopDate(Date stopDate) {
    this.stopDate = stopDate;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @Override
  public String toString() {
    DateFormat format = new SimpleDateFormat("M/d/yy");
    return medicationName +  " : " + "Start date: " + format.format(startDate)
    + ((stopDate != null) ? ("    Stop date: " + format.format(stopDate)) : "") ;
  }
}

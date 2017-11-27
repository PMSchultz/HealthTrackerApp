package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "IMMUNIZATION")
/**
 * Creates IMMUNIZATION entity
 */
public class Immunization {

  /* Links database table name to Immunization class with immunization id key*/
  @DatabaseField(columnName = "IMMUNIZATION_ID", generatedId = true)
  private int id;
  /* Links Patient ID to immunization record*/
  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true, uniqueCombo = true)
  private Patient patient;
  /* Date of immunization */
  @DatabaseField(columnName = "DATE",
      format = "yyyy-MM-dd", canBeNull = false, uniqueCombo = true)
  private Date date;
  /* Name of vaccine*/
  @DatabaseField(columnName = "VACCINE", canBeNull = false, uniqueCombo = true)
  private String vaccine;
  /* Name of physician/facility providing immunization*/
  @DatabaseField(columnName = "PROVIDER", canBeNull = true)
  private String provider;
  /* Notes regarding immunization*/
  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  /* Getter for immunization id*/
  public int getId() {
    return id;
  }

  /* Getter for date of immunization*/
  public Date getDate() {
    return date;
  }

  /* Setter for date of immunization*/
  public void setDate(Date date) {
    this.date = date;
  }

  /* Getter for vaccine name*/
  public String getVaccine() {
    return vaccine;
  }

  /* Setter for vaccine name*/
  public void setVaccine(String vaccine) {
    this.vaccine = vaccine;
  }

  /* Getter for provider/facility providing immunization*/
  public String getProvider() {
    return provider;
  }

  /* Setter for provider/facility providing immunization*/
  public void setProvider(String provider) {
    this.provider = provider;
  }

  /* Getter for notes*/
  public String getNotes() {
    return notes;
  }

  /* Setter for notes*/
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /* Getter providing patient associated with immunization*/
  public Patient getPatient() {
    return patient;
  }

  /* Setter for patient associated with immunization*/
  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @Override
  public String toString() {
    DateFormat format = new SimpleDateFormat("M/d/yy");
    return format.format(date) + ": " + vaccine + " " +
        ((provider != null) ? ("(" + provider + ")") : "");
  }
}

package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "IMMUNIZATION")

public class Immunization {

  //Links database table name to IMMUNIZATION class
  @DatabaseField(columnName = "IMMUNIZATION_ID", generatedId = true)
  private int id;
  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;

  @DatabaseField(columnName = "DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date date;

  @DatabaseField(columnName = "VACCINE", canBeNull = false)
  private String vaccine;

  @DatabaseField(columnName = "PROVIDER", canBeNull = true)
  private String provider;

  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  public int getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getVaccine() {
    return vaccine;
  }

  public void setVaccine(String vaccine) {
    this.vaccine = vaccine;
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
    return format.format(date) +": " + vaccine +" " +
        ((provider != null) ?  ("(" + provider + ")") : "");
  }
}

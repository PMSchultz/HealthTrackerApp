package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@DatabaseTable(tableName = "OFFICE_VISIT")
/**
 * Creates OFFICE_VISIT entity
 */
public class OfficeVisit {

  /*Links database table name to OfficeVisit class with office visit id key*/
  @DatabaseField(columnName = "OFFICE_VISIT_ID", generatedId = true)
  private int id;
  /* Links patient of office visit record*/
  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;
  /* Required user input of date of office visit*/
  @DatabaseField(columnName = "DATE", columnDefinition = "DATE OF OFFICE VISIT",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date date;
  /* Required user input of reason for office visit*/
  @DatabaseField(columnName = "REASON", canBeNull = false)
  private String reason;
  /* Required user input of name of provider*/
  @DatabaseField(columnName = "PROVIDER", canBeNull = false)
  private String provider;
  /* User input of height*/
  @DatabaseField(columnName = "HEIGHT", canBeNull = true)
  private String height;
  /* User input of weight*/
  @DatabaseField(columnName = "WEIGHT", canBeNull = true)
  private String weight;
  /* User input of blood pressure*/
  @DatabaseField(columnName = "BLOOD_PRESSURE", canBeNull = true)
  private String bloodPressure;
  /* User input of notes*/
  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  /* Getter for office visit id key*/
  public int getId() {
    return id;
  }

  /* Getter for reason for office visit*/
  public String getReason() {
    return reason;
  }

  /* Setter for reason for office visit*/
  public void setReason(String reason) {
    this.reason = reason;
  }

  /* Getter for date of office visit*/
  public Date getDate() {
    return date;
  }

  /* Setter for date of office visit*/
  public void setDate(Date date) {
    this.date = date;
  }

  /* Getter for provider */
  public String getProvider() {
    return provider;
  }

  /* Setter for provider*/
  public void setProvider(String provider) {
    this.provider = provider;
  }

  /* Getter for height*/
  public String getHeight() {
    return height;
  }

  /* Setter for height*/
  public void setHeight(String height) {
    this.height = height;
  }

  /* Getter for weight*/
  public String getWeight() {
    return weight;
  }

  /* Setter for weight*/
  public void setWeight(String weight) {
    this.weight = weight;
  }

  /* Getter for blood pressure*/
  public String getBloodPressure() {
    return bloodPressure;
  }

  /* Setter for blood pressure*/
  public void setBloodPressure(String bloodPressure) {
    this.bloodPressure = bloodPressure;
  }

  /* Getter for notes*/
  public String getNotes() {
    return notes;
  }

  /* Setter for notes*/
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /* Getter for patient associated with office visit record*/
  public Patient getPatient() {
    return patient;
  }

  /* Setter for patient associated with office visit record*/
  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @Override
  public String toString() {
    DateFormat format = new SimpleDateFormat("M/d/yy");
    return format.format(date) +
        ": " + reason +
        " (" + provider + ")";
  }
}

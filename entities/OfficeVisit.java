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

public class OfficeVisit {

  //Links database table name to OFFICE VISIT class
  @DatabaseField(columnName = "OFFICE_VISIT_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;

  @DatabaseField(columnName = "DATE", columnDefinition = "DATE OF OFFICE VISIT",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date date;

  @DatabaseField(columnName = "REASON", canBeNull = false)
  private String reason;

  @DatabaseField(columnName = "PROVIDER", canBeNull = false)
  private String provider;

  @DatabaseField(columnName = "HEIGHT", canBeNull = true)
  private String height;

  @DatabaseField(columnName = "WEIGHT", canBeNull = true)
  private String weight;

  @DatabaseField(columnName = "BLOOD_PRESSURE", canBeNull = true)
  private String bloodPressure;

  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;


  public int getId() {

    return id;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getBloodPressure() {
    return bloodPressure;
  }

  public void setBloodPressure(String bloodPressure) {
    this.bloodPressure = bloodPressure;
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
    return format.format(date) +
        ": " + reason +
        " (" + provider + ")";
  }
}

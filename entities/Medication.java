package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

@DatabaseTable(tableName = "MEDICATION")

public class Medication {

  @DatabaseField(columnName = "MEDICATION_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "MEDICATION", canBeNull = false)
  private String medicationName;


  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;

  @DatabaseField(columnName = "DATE", columnDefinition = "START_DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date startDate;

  @DatabaseField(columnName = "DATE", columnDefinition = "STOP_DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date stopDate;


  @DatabaseField(columnName = "PROVIDER", canBeNull = false)
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
    return "Medication{" +
        "id=" + id +
        ", patient=" + patient +
        ", startDate=" + startDate +
        ", stopDate=" + stopDate +
        ", provider='" + provider + '\'' +
        ", notes='" + notes + '\'' +
        '}';
  }
}

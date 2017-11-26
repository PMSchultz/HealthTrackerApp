package edu.cnm.deepdive.healthtracker.entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "HOSPITALIZATION")
/**
 * Creates HOSPITALIZATION entity
 */
public class Hospitalization {


  /* Links database table name to Hospitalization class with hospitalization id key */
  @DatabaseField(columnName = "HOSPITALIZATION_ID", generatedId = true)
  private int id;
  /* Required user input of hospital/facility name*/
  @DatabaseField(columnName = "HOSPITAL", canBeNull = false)
  private String hospital;
  /* Links hospitalization record to patient ID*/
  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;
  /* Required user input of admission to hospital date*/
  @DatabaseField(columnName = "ADMIT_DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date admitDate;
  /* User input of discharge date*/
  @DatabaseField(columnName = "DISCHARGE_DATE",
      format = "yyyy-MM-dd", canBeNull = true)
  private Date dischargeDate;
  /* Required user input of reason for hospitalization*/
  @DatabaseField(columnName = "REASON", canBeNull = false)
  private String reason;
  /* User input of attending physician during hospitalization*/
  @DatabaseField(columnName = "PROVIDER", canBeNull = true)
  private String provider;
  /* User input of notes about hospitalization*/
  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  /* Getter for hospitalization id*/
  public int getId() {
    return id;
  }

  /* Getter for hospital name*/
  public String getHospital() {
    return hospital;
  }

  /* Setter for hospital name*/
  public void setHospital(String hospital) {
    this.hospital = hospital;
  }

  /* Getter for admit date*/
  public Date getAdmitDate() {
    return admitDate;
  }

  /* Setter for admit date*/
  public void setAdmitDate(Date admitDate) {
    this.admitDate = admitDate;
  }

  /* Getter for discharge date*/
  public Date getDischargeDate() {
    return dischargeDate;
  }

  /* Setter for discharge date*/
  public void setDischargeDate(Date dischargeDate) {
    this.dischargeDate = dischargeDate;
  }

  /* Getter for reason for hospitalization*/
  public String getReason() {
    return reason;
  }

  /* Setter for reason for hospitalization*/
  public void setReason(String reason) {
    this.reason = reason;
  }

  /* Getter for attending physician during hospitalization*/
  public String getProvider() {
    return provider;
  }

  /* Setter for attending physician during hospitalization*/
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

  /* Getter for patient id*/
  public Patient getPatient() {
    return patient;
  }

  /* Setter for patient id*/
  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @Override
  public String toString() {
    DateFormat format = new SimpleDateFormat("M/d/yy");
    return format.format(admitDate) + ": " + hospital + ":  Reason: " + reason + " : "
        + ((dischargeDate != null) ? ("Discharge date: " + format.format(dischargeDate)) : "");
  }
}

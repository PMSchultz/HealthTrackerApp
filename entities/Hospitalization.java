package edu.cnm.deepdive.healthtracker.entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "HOSPITALIZATION")

public class Hospitalization {

  //Links database table name to HOSPITALIZATION class
  @DatabaseField(columnName = "HOSPITALIZATION_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "HOSPITAL", canBeNull = false)
  private String hospital;

  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;

  @DatabaseField(columnName = "ADMIT_DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date admitDate;

  @DatabaseField(columnName = "DISCHARGE_DATE",
      format = "yyyy-MM-dd", canBeNull = true)
  private Date dischargeDate;

  @DatabaseField(columnName = "REASON", canBeNull = false)
  private String reason;

  @DatabaseField(columnName = "PROVIDER", canBeNull = true)
  private String provider;

  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  public int getId() {
    return id;
  }

  public String getHospital() {
    return hospital;
  }

  public void setHospital(String hospital) {
    this.hospital = hospital;
  }

  public Date getAdmitDate() {
    return admitDate;
  }

  public void setAdmitDate(Date admitDate) {
    this.admitDate = admitDate;
  }

  public Date getDischargeDate() {
    return dischargeDate;
  }

  public void setDischargeDate(Date dischargeDate) {
    this.dischargeDate = dischargeDate;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
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
    return format.format(admitDate) +  ": "  + hospital +  ":  Reason: " + reason +  " : "
        + ((dischargeDate != null) ? ("Discharge date: " + format.format(dischargeDate)) : "");
  }
}

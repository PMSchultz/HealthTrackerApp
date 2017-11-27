package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "LABORATORY")

public class Laboratory {

  /* Links database table name to Laboratory class with laboratory id key*/
  @DatabaseField(columnName = "LABORATORY_ID", generatedId = true)
  private int id;
  /* Name of laboratory*/
  @DatabaseField(columnName = "NAME", canBeNull = false)
  private String labName;
  /* Links patient ID to laboratory record*/
  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Patient patient;
  /* Date that labwork was performed*/
  @DatabaseField(columnName = "DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date labworkDate;
  /* The physician/provider who ordered the labwork*/
  @DatabaseField(columnName = "PROVIDER", canBeNull = true)
  private String provider;
  /* Notes regarding labwork */
  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  public int getId() {
    return id;
  }

  public String getLabName() {
    return labName;
  }

  public void setLabName(String labName) {
    this.labName = labName;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public Date getLabworkDate() {
    return labworkDate;
  }

  public void setLabworkDate(Date labworkDate) {
    this.labworkDate = labworkDate;
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

  @Override
  public String toString() {
    DateFormat format = new SimpleDateFormat("M/d/yy");
    return format.format(labworkDate) + " :  " + labName;
  }
}

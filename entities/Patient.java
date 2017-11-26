package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.util.Date;

@DatabaseTable(tableName = "PATIENT")
/**
 * Creates PATIENT entity
 */
public class Patient {

  /* Links database table name to PATIENT class with patient id key*/
  @DatabaseField(columnName = "PATIENT_ID", generatedId = true)
  private int id;
  /* Required user input of patient name*/
  @DatabaseField(columnName = "NAME", canBeNull = false)
  private String name;
  /* User input of date of birth*/
  @DatabaseField(columnName = "DOB_DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date dateOfBirth;
  /* Automatic timestamp of when patient was created*/
  @DatabaseField(columnName = "CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
      format = "yyyy-MM-dd HH:mm:ss", canBeNull = false, readOnly = true)
  private Timestamp created;

  /* Getter for patient id key */
  public int getId() {
    return id;
  }

  /* Getter for patient name*/
  public String getName() {
    return name;
  }

  /* Setter for patient name*/
  public void setName(String name) {
    this.name = name;
  }

  /* Getter for date of birth*/
  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  /* Setter for date of birth*/
  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /* Getter for timestamp*/
  public Timestamp getCreated() {
    return created;
  }

  @Override
  public String toString() {
    return name;
  }
}
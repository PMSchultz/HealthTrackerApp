package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.util.Date;

@DatabaseTable(tableName = "PATIENT")

public class Patient {

  //Links database table name to PATIENT class
  @DatabaseField(columnName = "PATIENT_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "NAME", canBeNull = false)
  private String name;

  @DatabaseField(columnName = "DATE", columnDefinition = "DATE_OF_BIRTH",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date dateOfBirth;

  @DatabaseField(columnName = "CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
      format = "yyyy-MM-dd HH:mm:ss", canBeNull = false, readOnly = true)
  private Timestamp created;


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Timestamp getCreated() {
    return created;
  }

  @Override
  public String toString() {
    return "Patient{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        ", created=" + created +
        '}';
  }
}
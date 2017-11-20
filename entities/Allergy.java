package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

@DatabaseTable(tableName = "ALLERGY")

public class Allergy {

  //Links database table name to Allergy class
  @DatabaseField(columnName = "ALLERGY_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, uniqueCombo = true, foreign = true,
      foreignAutoRefresh = true)
  private Patient patient;


  @DatabaseField(columnName = "ALLERGY_TYPE")
  private String allergyType;

  @DatabaseField(columnName = "ALLERGY_NAME", uniqueCombo = true) //checks database for duplicates
  private String allergyName; //What user enters into editText field

  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  public int getId() {
    return id;
  }


  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getAllergyType() {
    return allergyType;
  }

  public void setAllergyType(String allergyType) {
    this.allergyType = allergyType;
  }

  public String getAllergyName() {
    return allergyName;
  }

  public void setAllergyName(String allergyName) {
    this.allergyName = allergyName;
  }

  @Override
  public String toString() {
    return "Allergy{" +
        "allergyType='" + allergyType + '\'' +
        ", allergyName='" + allergyName + '\'' +
        '}';
  }
}


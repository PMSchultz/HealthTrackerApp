package edu.cnm.deepdive.healthtracker.entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "RADIOLOGY")
/**
 *
 */
public class Radiology {

  /* Links database table name to Radiology class and provides ID key*/
  @DatabaseField(columnName = "RADIOLOGY_ID", generatedId = true)
  private int id;

  /* Links patient ID to patient instance*/
  @DatabaseField(columnName = "PATIENT_ID", canBeNull = false, foreign = true,
      foreignAutoRefresh = true)
  private Patient patient;

  /* Required radiology type input */
  @DatabaseField(columnName = "RADIOLOGY_TYPE", canBeNull = false)
  private String radiologyType;

  /* User input into editText field of radiology exam name */
  @DatabaseField(columnName = "RADIOLOGY_EXAM_NAME", canBeNull = true)
  private String radiologyExamName;

  /* User input into editText field of body part imaged*/
  @DatabaseField(columnName = "BODY_PART_IMAGED", canBeNull = false)
  private String bodyPartImaged;

  @DatabaseField(columnName = "EXAM_DATE",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date examDate;

  /* Name of facility performing radiology exam*/
  @DatabaseField(columnName = "FACILITY", canBeNull = false)
  private String facility;

  /* Name of physician ordering radiology exam*/
  @DatabaseField(columnName = "PROVIDER", canBeNull = true)
  private String provider;

  /* User input into editText field of notes */
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

  public String getRadiologyType() {
    return radiologyType;
  }

  public void setRadiologyType(String radiologyType) {
    this.radiologyType = radiologyType;
  }

  public String getRadiologyExamName() {
    return radiologyExamName;
  }

  public void setRadiologyExamName(String radiologyExamName) {
    this.radiologyExamName = radiologyExamName;
  }

  public String getBodyPartImaged() {
    return bodyPartImaged;
  }

  public void setBodyPartImaged(String bodyPartImaged) {
    this.bodyPartImaged = bodyPartImaged;
  }

  public Date getExamDate() {
    return examDate;
  }

  public void setExamDate(Date examDate) {
    this.examDate = examDate;
  }

  public String getFacility() {
    return facility;
  }

  public void setFacility(String facility) {
    this.facility = facility;
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
    return format.format(examDate) + " : " + "<b>" + radiologyType + "</b> " +
        ((radiologyExamName != null) ? "(" + radiologyExamName  + ")" : "" )+
        " " + bodyPartImaged;
  }
}

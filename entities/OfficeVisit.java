package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@DatabaseTable(tableName = "OFFICE_VISIT")

public class OfficeVisit {

  //Links database table name to OFFICE VISIT class
  @DatabaseField(columnName = "OFFICE_VISIT_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "DATE", columnDefinition = "DATE OF OFFICE VISIT",
      format = "yyyy-MM-dd", canBeNull = false)
  private Date date;

  @DatabaseField(columnName = "REASON", canBeNull = true)
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

  public String getBlood_pressure() {
    return bloodPressure;
  }

  public void setBlood_pressure(String blood_pressure) {
    this.bloodPressure = blood_pressure;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public String toString() {
    return "OfficeVisit{" +
        "id=" + id +
        ", date=" + date +
        ", reason='" + reason + '\'' +
        ", provider='" + provider + '\'' +
        ", height='" + height + '\'' +
        ", weight='" + weight + '\'' +
        ", bloodPressure='" + bloodPressure + '\'' +
        ", notes='" + notes + '\'' +
        '}';
  }
//override toString method using a abstract map
//  @Override
//  public String toString() {
//    Map<String, Object> map = new HashMap<>();
//    map.put("id", id);
//    map.put("date", date);
//    map.put("reason", reason);
//    map.put("provider", provider);
//    map.put("height", height);
//    map.put("weight", weight);
//    map.put("blood_pressure", bloodPressure);
//    map.put("notes", notes);
//    return map.toString();
//  }

}

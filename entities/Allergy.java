package edu.cnm.deepdive.healthtracker.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

@DatabaseTable(tableName = "ALLERGY")

public class Allergy {

  //Links database table name to Allergy class
  @DatabaseField(columnName = "ALLERGY_ID", generatedId = true)
  private int id;

  @DatabaseField(columnName = "DRUG_ALLERGY", canBeNull = true)
  private String drugAllergy;

  @DatabaseField(columnName = "FOOD_ALLERGY", canBeNull = false)
  private String foodAllergy;

  @DatabaseField(columnName = "SEASONAL_ALLERGY", canBeNull = true)
  private String seasonalAllergy;

  @DatabaseField(columnName = "ANIMAL_ALLERGY", canBeNull = true)
  private String animalAllergy;

  @DatabaseField(columnName = "LATEX_ALLERGY", canBeNull = true)
  private String latexAllergy;

  @DatabaseField(columnName = "NOTES", canBeNull = true)
  private String notes;

  public int getId() {
    return id;
  }

  public String getDrugAllergy() {
    return drugAllergy;
  }

  public void setDrugAllergy(String drugAllergy) {
    this.drugAllergy = drugAllergy;
  }

  public String getFoodAllergy() {
    return foodAllergy;
  }

  public void setFoodAllergy(String foodAllergy) {
    this.foodAllergy = foodAllergy;
  }

  public String getSeasonalAllergy() {
    return seasonalAllergy;
  }

  public void setSeasonalAllergy(String seasonalAllergy) {
    this.seasonalAllergy = seasonalAllergy;
  }

  public String getAnimalAllergy() {
    return animalAllergy;
  }

  public void setAnimalAllergy(String animalAllergy) {
    this.animalAllergy = animalAllergy;
  }

  public String getLatexAllergy() {
    return latexAllergy;
  }

  public void setLatexAllergy(String latexAllergy) {
    this.latexAllergy = latexAllergy;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  //TODO generate toString
}

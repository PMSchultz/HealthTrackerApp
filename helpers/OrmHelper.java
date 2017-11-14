package edu.cnm.deepdive.healthtracker.helpers;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import edu.cnm.deepdive.healthtracker.entities.Allergy;
import edu.cnm.deepdive.healthtracker.entities.Hospitalization;
import edu.cnm.deepdive.healthtracker.entities.Immunization;
import edu.cnm.deepdive.healthtracker.entities.Medication;
import edu.cnm.deepdive.healthtracker.entities.OfficeVisit;
import edu.cnm.deepdive.healthtracker.entities.Patient;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class OrmHelper extends OrmLiteSqliteOpenHelper {

  //declare database name and version
  private static final String DATABASE_NAME = "patients.db";
  private static final int DATABASE_VERSION = 1;

  private Dao<Patient, Integer> patientDao = null;
  private Dao<Medication, Integer> medicationDao = null;
  private Dao<Immunization, Integer> immunizationDao = null;
  private Dao<Allergy, Integer> allergyDao = null;  //Should this take a Boolean?
  private Dao<Hospitalization, Integer> hospitalizationDao = null;
  private Dao<OfficeVisit, Integer> officeVisitDao = null;

  public OrmHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, Patient.class);
      TableUtils.createTable(connectionSource, Medication.class);
      TableUtils.createTable(connectionSource, Immunization.class);
      TableUtils.createTable(connectionSource, Allergy.class);
      TableUtils.createTable(connectionSource, OfficeVisit.class);
      TableUtils.createTable(connectionSource, Hospitalization.class);
      populateDatabase();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {
  }

  @Override
  public void close() {
    patientDao = null;
    super.close();
  }

  public interface OrmInteraction {

    OrmHelper getHelper();

  }

  //TODO onStart and onStop methods

  public synchronized Dao<Patient, Integer> getPatientDao() throws SQLException {
    if (patientDao == null) {
      patientDao = getDao(Patient.class);
    }
    return patientDao;
  }

  public synchronized Dao<Medication, Integer> getMedicationDao() throws SQLException {
    if (medicationDao == null) {
      medicationDao = getDao(Medication.class);
    }
    return medicationDao;
  }

  public synchronized Dao<Allergy, Integer> getAllergyDao() throws SQLException {
    if (allergyDao == null) {
      allergyDao = getDao(Allergy.class);
    }
    return allergyDao;
  }

  public synchronized Dao<Immunization, Integer> getImmunizationDao() throws SQLException {
    if (immunizationDao == null) {
      immunizationDao = getDao(Immunization.class);
    }
    return immunizationDao;
  }

  public synchronized Dao<Hospitalization, Integer> getHospitalizationDao() throws SQLException {
    if (hospitalizationDao == null) {
      hospitalizationDao = getDao(Hospitalization.class);
    }
    return hospitalizationDao;
  }

  public synchronized Dao<OfficeVisit, Integer> getOfficeVisitDao() throws SQLException {
    if (officeVisitDao == null) {
      officeVisitDao = getDao(OfficeVisit.class);
    }
    return officeVisitDao;
  }

  private void populateDatabase() throws SQLException {
    Calendar calendar = Calendar.getInstance(); //Calendar is a helper class for dates
    {
      Patient patient = new Patient();
      patient.setName("Mickey Mouse");
      calendar.set(1933, 8, 3);
      patient.setDateOfBirth(calendar.getTime());
      getPatientDao().create(patient);

      Medication medication = new Medication();
      medication.setPatient(patient);
      medication.setMedicationName("Amoxicillin");
      calendar.set(2017, 10, 7);
      medication.setStartDate(new Date());
      getMedicationDao().create(medication);

      medication = new Medication();
      medication.setPatient(patient);
      medication.setMedicationName("Penicillin");
      calendar.set(2017, 10, 2);
      medication.setStartDate(new Date());
      getMedicationDao().create(medication);

      Allergy allergy = new Allergy();
      allergy.setPatient(patient);
      allergy.setLatexAllergy(true);
      getAllergyDao().create(allergy);

      allergy = new Allergy();
      allergy.setPatient(patient);
      allergy.setAnimalAllergy("cats");
      getAllergyDao().create(allergy);

      allergy = new Allergy();
      allergy.setPatient(patient);
      allergy.setAnimalAllergy("dogs");
      getAllergyDao().create(allergy);

      allergy = new Allergy();
      allergy.setPatient(patient);
      allergy.setFoodAllergy("peanut");
      getAllergyDao().create(allergy);

//      allergy = new Allergy();
//      allergy.setPatient(patient);      //TODO how to set medication allergy???
//      allergy.setMedAllergy(medication);
//      getAllergyDao().create(allergy);

      allergy = new Allergy();
      allergy.setPatient(patient);
      allergy.setLatexAllergy(true);
      getAllergyDao().create(allergy);

      Immunization immunization = new Immunization();
      immunization.setPatient(patient);
      immunization.setDate(new Date());
      immunization.setVaccine("Hepatitis B");
      getImmunizationDao().create(immunization);


      immunization.setDate(new Date());
      immunization.setVaccine("Flu shot");
      getImmunizationDao().create(immunization);


      Hospitalization hospitalization = new Hospitalization();
      hospitalization.setPatient(patient);
      calendar.set(2017, 7, 14);
      hospitalization.setAdmitDate(calendar.getTime());
      hospitalization.setHospital("Mercy General");
      hospitalization.setReason("Pneumonia");
      calendar.set(2017, 7, 20);
      hospitalization.setDischargeDate(calendar.getTime());
      getHospitalizationDao().create(hospitalization);

    }

    {
      Patient patient = new Patient();
      patient.setName("Donald Duck");
      calendar.set(1934, 01, 21);
      patient.setDateOfBirth(calendar.getTime());
      getPatientDao().create(patient);

      Medication medication = new Medication();
      medication.setPatient(patient);
      medication.setMedicationName("Gabapentin");
      calendar.set(2017, 8, 15);
      medication.setStartDate(calendar.getTime());
      calendar.add(Calendar.DAY_OF_MONTH, 30);
      medication.setStopDate(calendar.getTime());
      getMedicationDao().create(medication);

      Hospitalization hospitalization = new Hospitalization();
      hospitalization.setPatient(patient);
      calendar.set(2017, 6, 12);
      hospitalization.setAdmitDate(calendar.getTime());
      hospitalization.setHospital("Lovelace");
      hospitalization.setReason("Cardiac Arrest");
      calendar.set(2017, 6, 18);
      hospitalization.setDischargeDate(calendar.getTime());
      getHospitalizationDao().create(hospitalization);

      OfficeVisit officeVisit = new OfficeVisit();
      officeVisit.setPatient(patient);
      calendar.set(2017, 6, 20);
      officeVisit.setDate(calendar.getTime());
      officeVisit.setBloodPressure("180/100");
      officeVisit.setHeight("5'8");
      officeVisit.setWeight("296 pounds");
      officeVisit.setProvider("Dr. Mason");
      officeVisit.setReason("Cardiac event followup");
      getOfficeVisitDao().create(officeVisit);
    }
  }


}

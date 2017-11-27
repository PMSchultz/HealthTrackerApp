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

/**
 *Helper class to access database tools
 */
public class OrmHelper extends OrmLiteSqliteOpenHelper {

  /* Database name*/
  private static final String DATABASE_NAME = "patients.db";
  /* Database version*/
  private static final int DATABASE_VERSION = 1;

  /* create a reference of a patientDao */
  private Dao<Patient, Integer> patientDao = null;
  /* create a reference of a medicationDao*/
  private Dao<Medication, Integer> medicationDao = null;
  /* create a reference of an immunizationDao */
  private Dao<Immunization, Integer> immunizationDao = null;
  /* create a reference of an allergyDao*/
  private Dao<Allergy, Integer> allergyDao = null;
  /* create a reference of a hospitalizationDao*/
  private Dao<Hospitalization, Integer> hospitalizationDao = null;
  /* create a reference of an officeVisitDao */
  private Dao<OfficeVisit, Integer> officeVisitDao = null;

  /**
   * constructor
   *
   * @param context context to store this database in
   */
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
//      populateDatabase();
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

  /**
   * interface for activities that provide a helper
   */
  public interface OrmInteraction {

    OrmHelper getHelper();

  }

  /**
   * method to access a patient data access object
   *
   * @return the patient data access object
   * @throws SQLException if unable to get PatientDao
   */
  public synchronized Dao<Patient, Integer> getPatientDao() throws SQLException {
    if (patientDao == null) {
      patientDao = getDao(Patient.class);
    }
    return patientDao;
  }

  /**
   * method to access a medication data access object
   *
   * @return the medication data access object
   * @throws SQLException if unable to get Medication Dao
   */
  public synchronized Dao<Medication, Integer> getMedicationDao() throws SQLException {
    if (medicationDao == null) {
      medicationDao = getDao(Medication.class);
    }
    return medicationDao;
  }

  /**
   *
   * @return
   * @throws SQLException
   */
  public synchronized Dao<Allergy, Integer> getAllergyDao() throws SQLException {
    if (allergyDao == null) {
      allergyDao = getDao(Allergy.class);
    }
    return allergyDao;
  }

  /**
   *
   * @return
   * @throws SQLException
   */
  public synchronized Dao<Immunization, Integer> getImmunizationDao() throws SQLException {
    if (immunizationDao == null) {
      immunizationDao = getDao(Immunization.class);
    }
    return immunizationDao;
  }

  /**
   * @return hospitalization data access object
   */
  public synchronized Dao<Hospitalization, Integer> getHospitalizationDao() throws SQLException {
    if (hospitalizationDao == null) {
      hospitalizationDao = getDao(Hospitalization.class);
    }
    return hospitalizationDao;
  }

  /**
   *
   * @return
   * @throws SQLException
   */
  public synchronized Dao<OfficeVisit, Integer> getOfficeVisitDao() throws SQLException {
    if (officeVisitDao == null) {
      officeVisitDao = getDao(OfficeVisit.class);
    }
    return officeVisitDao;
  }
}


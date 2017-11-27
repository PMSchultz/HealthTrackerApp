# HealthTracker

## Overview
HealthTracker is a covenient app that keeps medical records for you and your family.  Easily create charts for yourself and individual family members.  Enter allergies, medications, immunizations, office visits, and hospitalizations and have that information available anytime. Can't remember how long it's been since you had a tetanus shot...find the date easily on HealthTracker.  The daycare or school needs a copy of your child's immunization record...the information you need is on your child's immunization chart on HealthTracker.  Perhaps you are on vacation and find yourself after hours in an Urgent Care or Emergency Room with no access to your doctor's office or medical records...you always have your chart with you with HealthTracker.  

## Docs
(https://pmschultz.github.io/HealthTrackerApp/)

## Purpose

HealthTracker is a personal health record (PHR), similar to the electronic health record (EHR) that your doctor might keep, except that you store your most important health information and have control over who can access it.

People change jobs, relocate, companies merge, others are sold, and employers look for the best healthcare deals.  It is estimated that 30% of patients switch insurers every year, voluntarily or involuntarily.  Even staying in the same network does not guarantee that your physician or physician group will remain a member of the network.  Higher levels of turnover among plans and physicians mean greater disruption of continuity of care and the possibility of error grows as patients have to fill out new forms and retell their histories each time they change doctors. Some patients have access to a patient portal through their employers, health care providers, or insurers. Unfortunately, many of these electronic health record systems are not compatible with sharing information with other systems. 

The importance of keeping your own personal health record has never been greater.  In the past, it was difficult to get a copy of your medical records.  The Health Insurance Portability and Accountability Act (HIPAA) affirms that patients have a right to review and receive a copy of their medical records. Your doctor must provide you with a copy of your medical records upon request within 30 days.  Keeping your medical records in your personal health chart assures that your medical history is available to health care providers  at any time.

## Version 1.0
The current version of HealthTracker features the following: 
* Create multiple patient charts and personalize each with the following medical information:
  * Medications: Medication name, dose, prescribing physician, start date, and stop date.
  * Immunizations:  Vaccine name, date administered, and facility providing vaccine.
  * Allergies:  Specific sections for latex, medication, seasonal, food, and animal allergies.
  * Hospitalizations: Reason for hospitalization, facility name, admission date, discharge date, and attending physician.
  * Ability to save, update and delete any patient record.
  * Ability to enter notes on any type of medical chart.
  * Personal health records reside on the device that they are installed on, safe and secure.


## Testing (Android emulators)

* Nexus 5X API 22 Phone
* Nexus 9 API 22 Tablet

## Aesthetic Improvements
* Add headings to medication list 

## Stretch Goals
* Immunizations - change existing user input method. Use a check item list of immunizations obtained from a database.  Store each immunization in a separate record for ease of future editing
* Medications - Use FDA current list of approved drugs in an AutoCompleteTextView.  Add over-the-counter/vitamin/herbal
supplement fields.
* Imaging Results- add an imaging results patient chart section
* Lab Results - add a lab results patient chart section
* Family History Section
* Blood Pressure Tracker - add an input page for blood pressures (perhaps adding a graph of results)
* Blood Glucose Tracker - add an input page for blood glucose (perhaps adding a graph of results)
* Ability to post pdf files to patient chart
* Login with email and password
* Add a FAQ section 
* Add ability to share/print/email/fax chart
* Add ability to display all medical charts or selected (more than one type) of medical record at once
* Add a private option for patient to keep chart items from showing in history
* Add a secure server so that medical charts are not lost if person changes devices, etc.
* Develop app for ios

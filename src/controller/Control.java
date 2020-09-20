/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Doctor;

/**
 *
 * @author TranVu
 */
public class Control {

    List<Doctor> docs;
    Scanner scan;

    public Control(List<Doctor> docs) {
        this.docs = docs;
        scan = new Scanner(System.in);
    }

    public Control() {
        scan = new Scanner(System.in);
    }

    public List<Doctor> getDocs() {
        return docs;
    }

    /**
     * Create new doctor
     */
    public void CreateDoctor() {
        Doctor doc = new Doctor();
        //set id
        int id = docs.size() > 0 ? docs.get(docs.size() - 1).getId() + 1 : 0;
        doc.setId(id);
        doc.setName(checkDoctorsName());
        doc.setDateOfBirth(checkDateBirth());
        doc.setSpecialization(checkSpecialization());
        doc.setAvailability(checkAvailability());
        doc.setEmail(checkEmail());
        doc.setMobile(checkMobile());

    }

    public void EditDoctor() {

        Doctor doc = findDoctorById();
        if (doc != null) {
            doc.setName(checkDoctorsName());
            doc.setDateOfBirth(checkDateBirth());
            doc.setSpecialization(checkSpecialization());
            doc.setAvailability(checkAvailability());
            doc.setEmail(checkEmail());
            doc.setMobile(checkMobile());
        }
    }

    public void DeleteDoctor() {
        Doctor doc = findDoctorById();
        if (doc != null) {
            docs.remove(doc);
        }
    }

    public void SearchDoctor() {
        boolean loop = true;
        while (loop) {
            System.out.println("Please choose the way search:\n"
                    + "1. By Id\n"
                    + "2. By Name\n"
                    + "3. Back\n"
                    + "Your choose: ");
            switch (scan.nextLine().trim()) {
                case "1":
                    Doctor doc = findDoctorById();
                    if (doc != null) {
                        System.out.println(doc.toString());
                    }
                    loop = false;
                    break;
                case "2":
                    List<Doctor> listDocs = findDoctorByName();
                    if (!listDocs.isEmpty()) {
                        listDocs.forEach((d) -> {
                            System.out.println(d.toString());
                        });
                    }
                    loop = false;
                    break;
                case "3":
                    loop = false;
                    break;
                default:
                    System.out.println("Please only input 1 - 3.");
                    break;
            }
        }
    }

    public void Sort() {
        Collections.sort(docs, (arg0, arg1) -> {
            return arg0.getDateOfBirth().compareToIgnoreCase(arg1.getDateOfBirth()); //To change body of generated lambdas, choose Tools | Templates.
        });
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %30s %50s %20s %30s %20s", "DOCTOR ID", "NAME", "DATE OF BIRTH", "AVAILABILITY", "EMAIL", "MOBILE");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        docs.stream().map((Doctor doc) -> {
            System.out.format("%10s %30s %50s %20s %30s %20s",
                    doc.getId(), doc.getName(), doc.getDateOfBirth(), doc.getAvailability(), doc.getEmail(), doc.getMobile());
            return doc;
        }).forEachOrdered((_item) -> {
            System.out.println();
        });
        System.out.println("-----------------------------------------------------------------------------");

    }

    /**
     * Check doctor's name
     *
     * @return A String that shorter than 51 characters
     */
    private String checkDoctorsName() {
        String name = "";
        while (true) {
            System.out.println("Please input Doctor's name: ");
            name = scan.nextLine().trim();
            if (name.length() <= 50) {
                break;
            }
            System.out.println("Doctor's name should be not longer than 50 character");
        }
        return name;
    }

    /**
     * Check date of birth input
     *
     * @return a String that in 'dd/MM/yyyy' format
     */
    private String checkDateBirth() {
        String returnValue = "";
        Pattern pattern = Pattern.compile("^[0-3]\\d\\/[01]\\d\\/\\d{4}$");

        while (true) {
            System.out.println("Please input date of birth: ");
            returnValue = scan.nextLine().trim();
            Matcher matcher = pattern.matcher(returnValue);
            if (matcher.find()) {
                break;
            }
            System.out.println("Date of birth should enter like this format 'dd/MM/yyyy'.");
        }
        return returnValue;
    }

    /**
     * check specialization input
     *
     * @return a String that no longer than 255 characters
     */
    private String checkSpecialization() {
        String name = "";
        while (true) {
            System.out.println("Please input specialization: ");
            name = scan.nextLine().trim();
            if (name.length() <= 255) {
                break;
            }
            System.out.println("Specialization should be not longer than 255 character");
        }
        return name;
    }

    /**
     * check Availability
     *
     * @return an integer that 0 for in vacation, 1 for available, 2 for busy in
     * emergency case, 3 for in diagnosing case.
     */
    private int checkAvailability() {
        int returnValue = 0;
        while (true) {
            System.out.println("Please input availability \n"
                    + "0 for in vacation\n"
                    + "1 for available\n"
                    + "2 for busy in emergency case\n"
                    + "3 for in diagnosing case\n"
                    + "Availability: ");
            returnValue = checkInputInt();
            if (returnValue < 4 && returnValue >= 0) {
                break;
            }
            System.out.println("Availability should around 0 - 4.");

        }
        return returnValue;
    }

    /**
     * check email
     *
     * @return a String in email format
     */
    private String checkEmail() {
        String returnValue = "";
        Pattern pattern = Pattern.compile("^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$");

        while (true) {
            System.out.println("Please input email: ");
            returnValue = scan.nextLine().trim();
            Matcher matcher = pattern.matcher(returnValue);
            if (matcher.find()) {
                break;
            }
            System.out.println("Not a valid email.");
        }
        return returnValue;
    }

    /**
     * check mobile
     *
     * @return a String of number conforming (000)-000-0000 format
     */
    private String checkMobile() {
        String returnValue = "";
        Pattern pattern = Pattern.compile("^\\(\\d{3}\\)\\-\\d{3}\\-\\d{4}$");

        while (true) {
            System.out.println("Please input mobile number: ");
            returnValue = scan.nextLine().trim();
            Matcher matcher = pattern.matcher(returnValue);
            if (matcher.find()) {
                break;
            }
            System.out.println("Not a valid mobile number. Should follow this format (000)-000-0000");
        }
        return returnValue;
    }

    /**
     * find a doctor by id
     *
     * @return
     */
    private Doctor findDoctorById() {
        Doctor d = null;
        while (true) {
            System.out.println("Please input Doctor's Id: ");
            int id = checkInputInt();

            for (Doctor doc : docs) {
                if (doc.getId() == id) {
                    d = doc;
                }
            }
            if (d != null) {
                break;
            }
            System.out.println("Not exist Doctor's Id. Continue or not? Y/N?");
            if (scan.nextLine().trim().toUpperCase().equals("N")) {
                break;
            }
        }
        return d;
    }

    /**
     * check a number input
     *
     * @return a number
     */
    private int checkInputInt() {
        int returnValue = 0;
        while (true) {
            try {
                returnValue = Integer.parseInt(scan.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Not a number. Please input again: ");
            }
        }
        return returnValue;
    }

    /**
     * find all doctor have same name
     *
     * @return
     */
    private List<Doctor> findDoctorByName() {
        List<Doctor> listDocs = new ArrayList<>();
        while (true) {
            String id = checkDoctorsName();

            docs.stream().filter((doc) -> (doc.getName().equals(id))).forEachOrdered((doc) -> {
                listDocs.add(doc);
            });
            if (!listDocs.isEmpty()) {
                break;
            }
            System.out.println("Not exist Doctor's Name. Continue or not? Y/N?");
            if (scan.nextLine().trim().toUpperCase().equals("N")) {
                break;
            }
        }
        return listDocs;
    }

}

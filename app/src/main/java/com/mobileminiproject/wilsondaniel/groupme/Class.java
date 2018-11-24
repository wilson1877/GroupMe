package com.mobileminiproject.wilsondaniel.groupme;

public class Class {

    static String classID, className, classDescription, lecturerID;

    public Class() {}

    public Class(String classID, String className, String classDescription, String lecturerID) {
        this.classID = classID;
        this.className = className;
        this.classDescription = classDescription;
        this.lecturerID = lecturerID;
    }

    public String getClassID() {
        return classID;
    }

    public String getClassName() { return className; }

    public String getClassDescription() { return classDescription; }

    public String getLecturerID() { return lecturerID; }
}

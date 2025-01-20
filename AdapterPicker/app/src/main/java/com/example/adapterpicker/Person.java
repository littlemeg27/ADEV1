package com.example.adapterpicker;

public class Person
{
    private String firstName;
    private String lastName;
    private String birthday;
    private int pictureResId;

    public Person(String firstName, String lastName, String birthday, int pictureResId)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.pictureResId = pictureResId;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getBirthday() { return birthday; }
    public int getPictureResId() { return pictureResId; }
}
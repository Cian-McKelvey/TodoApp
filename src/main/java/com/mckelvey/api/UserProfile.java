package com.mckelvey.api;

/*
Profile Class
Attributes:
    account: Reference to the associated Account userId
    firstName: The user's first name.
    lastName: The user's last name.
    profilePicture: URL or path to the user's profile picture - Store it in azure blob or something.
    location: The user's location (country).
*/

public class UserProfile {

    private String accountID;  // Should contain the UUID of the assosiated user account
    private String firstName, lastName;
    private String profilePictureUrl;
    private String location;

    public UserProfile(String accountID) {
        this.accountID = accountID;
        this.firstName = null;
        this.lastName = null;
        this.profilePictureUrl = null;
        this.location = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static UserProfile generateAssociatedProfile(UserAccount userAccount) {
        return new UserProfile(userAccount.getUserId());
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "accountID='" + accountID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    // Probably isnt needed but ill leave it for now
    public String toJsonString() {
        return "UserProfile{" +
                "accountID='" + accountID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

}

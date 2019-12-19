package loginPage;

public final class UserSession {
    private static UserSession instance;

    private String userId;
    private String firstName;
    private String lastName;

    public UserSession(){

    }

    public UserSession(String userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static void setInstance(UserSession instance){
        UserSession.instance = instance;
    }

    public static UserSession getInstance() {
        if(instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    public String getUserId() {
        return userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
}

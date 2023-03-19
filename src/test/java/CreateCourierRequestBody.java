public class CreateCourierRequestBody {
    private final String login;
    private final String password;
    private final String firstName;

    public CreateCourierRequestBody(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
}

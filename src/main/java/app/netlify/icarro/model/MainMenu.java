package app.netlify.icarro.model;

public class MainMenu {

    private String search;
    private String addCar;
    private String terms;
    private String signUp;
    private String login;

    public MainMenu(String login,
                    String signUp,
                    String terms,
                    String addCar,
                    String search) {

        this.login = login;
        this.signUp = signUp;
        this.terms = terms;
        this.addCar = addCar;
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public String getAddCar() {
        return addCar;
    }

    public String getTerms() {
        return terms;
    }

    public String getSignUp() {
        return signUp;
    }

    public String getLogin() {
        return login;
    }
}
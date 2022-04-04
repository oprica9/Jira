package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel;

import static rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.activities.MainActivity.USER;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.User;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Type;

public class UserViewModel extends ViewModel {

    private MutableLiveData<User> userData = new MutableLiveData<>();
    private SharedPreferences sharedPreferences;

    public LiveData<User> getUserData() {
        return userData;
    }

    // context is from main activity
    public void loadData(Context context) {

        // initialize shared preferences
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        // load data from shared preferences, if there is data to load
        String savedData = sharedPreferences.getString(USER, null);
        if (savedData != null) {
            this.userData.setValue(getUserFromSharedPrefs());
        }
    }

    public boolean login(String username, String email, String password) {

        // handle null and empty
        if (username == null || username.equals("")
                || email == null || email.equals("")
                || password == null || password.equals("")) {
            throw new NullPointerException("Fields can't be empty.");
        }

        // validate data
        if (!isPasswordValid(password) || !isEmailValid(email)) {
            throw new RuntimeException("Invalid email or password. Password must be greater than 5 characters.");
        }

        // check if already logged in
        User user = userData.getValue();
        if (user != null) {

            // authenticate
            if (password.equals(user.getPassword())
                    && username.equals(user.getUsername())
                    && email.equals(user.getEmail())
            ) {
                user.setRememberMe(true);
                sharedPreferences
                        .edit()
                        .putString(USER, generateStringFromUser(user))
                        .apply();
                return true;

            } else {

                return false;
            }

        } else {
            // todo i mean hardcoded 0 yikes
            // first time login, gotta save that data
            Type type = initType(username);
            String userStr =
                    0 + ","
                            + username + ","
                            + email + ","
                            + password + ","
                            + type.name().toLowerCase() + ","
                            + true;
            // ovo true je za kao remember me, u slucaju da se izloguje pa da opet udje u app, da ga
            // ne bi automatski ulogovao preko shared prefs

            sharedPreferences
                    .edit()
                    .putString(USER, userStr)
                    .apply();

            User userToSave = new User(0, username, email, password, type);
            userToSave.setRememberMe(true);

            userData.setValue(userToSave);
            return true;
        }
    }

    public boolean logout() {
        User user = getUserFromSharedPrefs();
        if (user != null) {
            user.setRememberMe(false);
            String s = generateStringFromUser(user);
            sharedPreferences
                    .edit()
                    .putString(USER, s)
                    .apply();
            userData.setValue(null);
            return true;
        }
        return false;
    }

    // private

    private String generateStringFromUser(User user) {
        return 0 + ","
                + user.getUsername() + ","
                + user.getEmail() + ","
                + user.getPassword() + ","
                + user.getType().name().toLowerCase() + ","
                + user.isRememberMe();
    }

    public boolean isLoggedIn() {
        User user = getUserFromSharedPrefs();
        if (user != null)
            return user.isRememberMe();
        return false;
    }

    private User getUserFromSharedPrefs() {
        String savedData = sharedPreferences.getString(USER, null);
        if (savedData != null) {
            String[] data = savedData.split(",");
            Type type = loadType(data[4]);
            User user = new User(Integer.parseInt(data[0]), data[1], data[2], data[3], type);
            user.setRememberMe(Boolean.parseBoolean(data[5]));
            return user;
        }
        return null;
    }

    private Type initType(String username) {
        if (username.startsWith("admin")) {
            return Type.ADMIN;
        } else {
            return Type.USER;
        }
    }

    private Type loadType(String type) {
        if (type.equalsIgnoreCase("user")) {
            return Type.USER;
        } else if (type.equalsIgnoreCase("admin")) {
            return Type.ADMIN;
        }
        return null;
    }

    private boolean isPasswordValid(String password) {
        // todo only for testing
        return true;
        // return password.length() >= 5;
    }

    private boolean isEmailValid(String email) {
        // todo only for testing
        return true;
        // return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

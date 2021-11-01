package settings;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseSettings {

    private static FirebaseAuth authenticate;

    public static FirebaseAuth getFirebaseAuthenticate() {

        if (authenticate == null) {
            authenticate = FirebaseAuth.getInstance();
        }
        return authenticate;

    }


}

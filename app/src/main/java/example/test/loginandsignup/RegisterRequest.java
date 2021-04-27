package example.test.loginandsignup;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    //이거 바꿔야 합니다. DB형식이 바꼈습니다.

    final static private String URL = "https://mysnapbook.skanaimaging.com/api/sUser";
    private final Map<String, String> map;

    public RegisterRequest(String email, String password, String name, Response.Listener<String>listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

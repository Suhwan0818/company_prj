package example.test.loginandsignup;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignupRequest extends StringRequest {
    //바꿔야합니다. DB형식이 바꼈습니다.
    final static private String URL = "https://mysnapbook.skanaimaging.com/api/sUser";
    private final Map<String,String> map;

    public SignupRequest(String userID, Response.Listener<String>listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}

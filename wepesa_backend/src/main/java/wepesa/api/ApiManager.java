package wepesa.api;

import wepesa.api.impl.UserApiImpl;

public class ApiManager
{
    private static ApiManager instance;

    public static ApiManager getInstance()
    {
        if (instance == null)
        {
            instance = new ApiManager();
        }

        return instance;
    }

    /* Blockchain APIs */
    private UserApi userApi;

    private ApiManager()
    {

    }

    public UserApi getUserApi() {

        if(userApi == null)
        {
            userApi = new UserApiImpl();
        }

        return userApi;
    }
}

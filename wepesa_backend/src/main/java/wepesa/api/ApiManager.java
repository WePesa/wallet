package wepesa.api;

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

        }

        return userApi;
    }
}

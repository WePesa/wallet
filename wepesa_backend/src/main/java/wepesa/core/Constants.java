package wepesa.core;

public final class Constants
{
    /* API Server */
    public static final String VERSION = "v0.9";
    public static final String BASE_URI = "/" + VERSION + "/";
    public static final String HTTP_SCHEME = "http";
    public static final String HOST = "0.0.0.0";
    public static final int PORT = 7070;

    /* Auth */
    public static final int LOGIN_SUCCESSFUL = 0;
    public static final int LOGIN_FAIL = 1;
    public static final int USER_DOES_NOT_EXIST = 2;

    /* Register User */
    public static final int USER_INSERTED = 0;
    public static final int USER_EMAIL_EXISTS = 1;

    private Constants()
    {
    }
}

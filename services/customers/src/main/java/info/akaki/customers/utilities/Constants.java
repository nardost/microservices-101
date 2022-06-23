package info.akaki.customers.utilities;

public final class Constants {

    private Constants() {
    }

    public static final String REGEX_UUID = "^[\\da-fA-F]{8}(-[\\da-fA-F]{4}){3}-[\\da-fA-F]{12}$";
    public static final String REGEX_PHONE = "^[+]\\d+";
    public static final String REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String REGEX_NAME = "^[a-zA-Z]{1,15}$";
    public static final String REGEX_ZIP_CODE = "^\\d{5}$";

    public static final int MIN_LENGTH_NAME = 1;
    public static final int MAX_LENGTH_NAME = 32;
    public static final int MAX_LENGTH_EMAIL = 320;
    public static final int MAX_LENGTH_PHONE_NUMBER = 15;
    public static final int MAX_LENGTH_CITY = 20;
    public static final int MAX_LENGTH_STREET_ADDRESS = 255;
}

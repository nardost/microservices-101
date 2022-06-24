package info.akaki.subscription.utilities;

import java.math.BigDecimal;
import java.util.Objects;

import static info.akaki.subscription.utilities.Constants.MAX_LENGTH_CITY;
import static info.akaki.subscription.utilities.Constants.MAX_LENGTH_STREET_ADDRESS;
import static info.akaki.subscription.utilities.Constants.REGEX_EMAIL;
import static info.akaki.subscription.utilities.Constants.REGEX_NAME;
import static info.akaki.subscription.utilities.Constants.REGEX_PHONE;
import static info.akaki.subscription.utilities.Constants.REGEX_UUID;
import static info.akaki.subscription.utilities.Constants.REGEX_ZIP_CODE;

public final class Validators {

    private Validators() {
    }

    public static Boolean validateUUID(String uuid) {
        return Objects.nonNull(uuid) &&  uuid.matches(REGEX_UUID);
    }

    public static Boolean validatePhoneNumber(String phoneNumber) {
        return Objects.nonNull(phoneNumber) && phoneNumber.matches(REGEX_PHONE);
    }

    public static Boolean validateEmail(String email) {
        return Objects.nonNull(email) && email.matches(REGEX_EMAIL);
    }

    public static Boolean validateName(String name) {
        return Objects.nonNull(name) && name.matches(REGEX_NAME);
    }

    public static Boolean validateStreetAddress(String streetAddress) {
        return Objects.nonNull(streetAddress) && streetAddress.length() <= MAX_LENGTH_STREET_ADDRESS;
    }

    public static Boolean validateCity(String city) {
        return Objects.nonNull(city) && city.length() <= MAX_LENGTH_CITY;
    }

    public static Boolean validateZipCode(String zipCode) {
        return Objects.nonNull(zipCode) && zipCode.matches(REGEX_ZIP_CODE);
    }
    public static Boolean isValidRatePerUnit(BigDecimal ratePerUnit) {
        if(Objects.isNull(ratePerUnit)) {
            return Boolean.FALSE;
        }
        return ratePerUnit.doubleValue() > 0.0d;
    }
    public static Boolean isValidBaseUnits(double baseUnit) {
        return baseUnit > 0.0d;
    }
    public static Boolean isValidMaxUnits(double maxUnit) {
        return maxUnit > 0.0d;
    }
}

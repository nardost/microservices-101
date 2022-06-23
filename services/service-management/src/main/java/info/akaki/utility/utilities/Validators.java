package info.akaki.utility.utilities;

import java.math.BigDecimal;
import java.util.Objects;

import static info.akaki.utility.utilities.Constants.REGEX_UUID;

public final class Validators {

    private Validators() {
    }

    public static Boolean isValidUUID(String uuid) {
        return Objects.nonNull(uuid) &&  uuid.matches(REGEX_UUID);
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

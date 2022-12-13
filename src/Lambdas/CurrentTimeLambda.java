package Lambdas;

import java.time.LocalDateTime;

/** Lambda used to convert the time to the current local date time. */
public interface CurrentTimeLambda {
    LocalDateTime currentTime(CurrentTimeLambda now);
}

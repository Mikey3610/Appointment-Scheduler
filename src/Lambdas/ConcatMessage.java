package Lambdas;

/** Lambda used to concatenate the notification displayed to the user at login of any appointments upcoming within 15 minutes. */
public interface ConcatMessage {
    String LoginAlert(ConcatMessage s);
}

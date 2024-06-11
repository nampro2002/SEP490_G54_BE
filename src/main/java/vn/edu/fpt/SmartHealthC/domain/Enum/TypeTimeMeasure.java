package vn.edu.fpt.SmartHealthC.domain.Enum;

public enum TypeTimeMeasure {
    BEFORE_BREAKFAST,
    AFTER_BREAKFAST,
    AFTER_LUNCH,
    BEFORE_LUNCH,
    AFTER_DINNER,
    BEFORE_DINNER;

    public static int getIndex(TypeTimeMeasure measure) {
        return measure.ordinal();
    }
}

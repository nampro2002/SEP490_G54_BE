package vn.edu.fpt.SmartHealthC.domain.Enum;

public enum TypeTimeMeasure {
    BEFORE_BREAKFAST,
    AFTER_BREAKFAST,
    BEFORE_LUNCH,
    AFTER_LUNCH,
    BEFORE_DINNER,
    AFTER_DINNER;

    public static int getIndex(TypeTimeMeasure measure) {
        return measure.ordinal();
    }
}

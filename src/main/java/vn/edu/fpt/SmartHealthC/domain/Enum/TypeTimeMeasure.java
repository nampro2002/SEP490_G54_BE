package vn.edu.fpt.SmartHealthC.domain.Enum;

public enum TypeTimeMeasure {
    BEFORE_BREAKFAST(1),
    AFTER_BREAKFAST(2),
    BEFORE_LUNCH(3),
    AFTER_LUNCH(4),
    BEFORE_DINNER(5),
    AFTER_DINNER(6);

    private final int index;

    TypeTimeMeasure(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }

    public static TypeTimeMeasure getByIndex(int index) {
        for (TypeTimeMeasure e : values()) {
            if (e.getIndex() == index) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant with index " + index);
    }
}

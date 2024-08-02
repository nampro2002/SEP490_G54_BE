package vn.edu.fpt.SmartHealthC.domain.Enum;

public enum TypeTopic {
    DAILY_EN("dailyen"),
    DAILY_KR("dailykr"),
    MONDAY_AM_EN("mondayamen"),
    SUNDAY_PM_EN("sundaypmen"),
    MONDAY_AM_KR("mondayamkr"),
    SUNDAY_PM_KR("sundaypmkr");

    private final String topicName;

    TypeTopic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public static TypeTopic getByTopicName(String topicName) {
        for (TypeTopic e : values()) {
            if (e.getTopicName().equals(topicName)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant with topicName: " + topicName);
    }
}

package life.mosu.mosuserver.domain.school;

public enum Area {
    DAECHI("대치"),
    MOKDONG("목동"),
    DAEGU("대구");

    private final String areaName;

    Area(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }
}
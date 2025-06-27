package life.mosu.mosuserver.domain.event;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DurationJpaVO {

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public static DurationJpaVO of(final LocalDate startDate, final LocalDate endDate) {
        return new DurationJpaVO(startDate, endDate);
    }

    /**
     * 주어진 기간보다 이후인지 확인합니다.
     *
     * @param duration 비교할 기간
     * @return 이후라면 true, 아니라면 false
     */
    public boolean isBefore(final DurationJpaVO duration) {
        return endDate.isBefore(duration.startDate);
    }

    /**
     * 주어진 기간보다 이전인지 확인합니다.
     *
     * @param duration 비교할 기간
     * @return 이전이라면 true, 아니라면 false
     */
    public boolean isAfter(final DurationJpaVO duration) {
        return startDate.isAfter(duration.endDate);
    }

    /**
     * 시작일과 종료일의 차이를 구한다.
     *
     * @return 시작일과 종료일의 차이 일수
     */
    public Long days() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 기간이 끝났는지 확인합니다.
     *
     * @param clock 현재 시간
     * @return 기간이 끝났다면 true, 아니라면 false
     */
    public boolean isEnded(final Clock clock) {
        final LocalDate now = LocalDate.now(clock);
        return now.isAfter(endDate);
    }

    /**
     * 주어진 날짜가 기간 내에 있는지 확인합니다.
     *
     * @param date 확인할 날짜
     * @return 기간 내에 있다면 true, 아니라면 false
     */
    public boolean isBetween(final LocalDate date) {
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
            (date.isBefore(endDate) || date.isEqual(endDate));
    }
}
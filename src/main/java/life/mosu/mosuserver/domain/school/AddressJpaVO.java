package life.mosu.mosuserver.domain.school;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressJpaVO {

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "street")
    private String street;

    @Column(name = "detail")
    private String detail;

    @Builder
    public AddressJpaVO(
        String zipcode,
        String street,
        String detail
    ) {
        this.zipcode = zipcode;
        this.street = street;
        this.detail = detail;
    }
}

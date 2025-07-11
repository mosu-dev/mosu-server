package life.mosu.mosuserver.presentation.user;

public record CustomerKeyResponse(
        String customerKey
) {

    public static CustomerKeyResponse from(String customerKey) {
        return new CustomerKeyResponse(customerKey);
    }
}

package lt.projectx.moviewatchlist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateWatcherRequest(
        @NotBlank(message = "Username cannot be empty")
        String username,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotNull(message = "Join date cannot be null")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Join date must be in the format yyyy-MM-dd")
        String joinDate
) {
}

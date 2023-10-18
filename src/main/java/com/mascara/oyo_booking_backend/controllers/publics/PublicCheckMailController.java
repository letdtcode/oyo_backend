package com.mascara.oyo_booking_backend.controllers.publics;

import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.CheckMailRequest;
import com.mascara.oyo_booking_backend.dtos.response.MessageResponse;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 9:46 CH
 * Filename  : PublicCheckUserController
 */
@Tag(name = "Public Check Mail", description = "Authentication APIs")
@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class PublicCheckMailController {

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Check Mail Exist", description = "Public Api check mail exist for Sign Up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/checkmail")
    public ResponseEntity<?> checkMailExist(@Valid @RequestBody CheckMailRequest checkMailRequest) {
        if (userRepository.existsByMail(checkMailRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new MessageResponse("User exist !"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not exist !"));
    }
}

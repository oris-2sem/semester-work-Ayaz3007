package com.example.ludikgames.controller.api;

import com.example.ludikgames.dto.slots.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tags(value = { @Tag(name = "Slot")})
public interface SlotApi {
    @Operation(summary = "Проверка, успешен ли спин")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешность спина",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SpinResultDto.class))
                    })
    })
    @PostMapping("/{id}/spin")
    ResponseEntity<SpinResultDto> getSuccessOfSpin(@PathVariable("id") String id, @RequestBody SpinDto spinDto, Principal principal);

    @PostMapping("/{id}/result")
    @Operation(summary = "Получение результата выигрышного спина")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат выигрыша",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = WinResultDto.class))
                    })
    })
    ResponseEntity<WinResultDto> getWinResult(@RequestBody WinSpinDto spinDto, Principal principal, @PathVariable String id);

    @PostMapping
    ResponseEntity<?> addSlot(@ModelAttribute("slot") SlotDto slotDto);

    @Operation(summary = "Добавление слота в избранные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202")
    })
    @PutMapping("/favourite")
    ResponseEntity<?> addSlotToFavourite(@RequestBody FavouriteSlotDto favouriteSlotDto, Principal principal);

    @Operation(summary = "Удаление слота из избранного")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202")
    })
    @DeleteMapping("/favourite")
    ResponseEntity<?> deleteSlotFromFavourite(@RequestBody FavouriteSlotDto favouriteSlotDto, Principal principal);
}

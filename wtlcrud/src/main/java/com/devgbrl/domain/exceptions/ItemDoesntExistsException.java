package com.devgbrl.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItemDoesntExistsException extends RuntimeException {
    private String itemKey;
    private String message;
}

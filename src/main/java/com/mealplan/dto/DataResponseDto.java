package com.mealplan.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponseDto {
  private boolean success;
  private String message;
  private Map<String, Object> data;
}

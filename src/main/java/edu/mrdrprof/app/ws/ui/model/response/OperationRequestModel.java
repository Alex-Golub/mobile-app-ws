package edu.mrdrprof.app.ws.ui.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mr.Dr.Professor
 * @since 3/23/2021 1:26 PM
 */
@NoArgsConstructor
@Getter
@Setter
public class OperationRequestModel {
  private String operationName;
  private String operationStatus;
}

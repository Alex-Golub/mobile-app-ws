package edu.mrdrprof.app.ws.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Custom error message that will be converted
 * to JSON representation and sent to client in
 * case of exception.
 *
 * @author Mr.Dr.Professor
 * @since 3/22/2021 5:01 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorMessage {
  private Date timeStamp;
  private String message;
}

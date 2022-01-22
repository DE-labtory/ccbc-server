package io.coin.ccbc.domain.exceptions;

/**
 * @author Bomi
 * @date 2021/08/17
 */

public class ExchangeServerException extends RuntimeException {

  public ExchangeServerException(String messsage) {
    super(messsage);
  }

}

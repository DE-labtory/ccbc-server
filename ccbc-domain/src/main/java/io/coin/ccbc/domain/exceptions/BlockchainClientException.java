package io.coin.ccbc.domain.exceptions;

public class BlockchainClientException extends RuntimeException {

  public BlockchainClientException() {
    super();
  }

  public BlockchainClientException(String message) {
    super(message);
  }

  public BlockchainClientException(String format, Object... args) {
    super(String.format(format, args));
  }
}

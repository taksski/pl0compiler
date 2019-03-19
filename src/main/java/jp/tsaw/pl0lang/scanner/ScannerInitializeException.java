package jp.tsaw.pl0lang.scanner;

public class ScannerInitializeException extends RuntimeException {
    ScannerInitializeException(Throwable e) {
        super("Unable to initialize scanner object", e);
    }
}

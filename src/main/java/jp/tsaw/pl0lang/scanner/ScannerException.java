package jp.tsaw.pl0lang.scanner;

class ScannerException extends RuntimeException {
    ScannerException(Throwable e) {
        super("Unable to continue executing a scanner", e);
    }
}

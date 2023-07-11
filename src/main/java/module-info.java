module tn3270j {
    requires static lombok;

    requires java.logging;
    requires bsh;
    requires org.slf4j;
    requires org.checkerframework.checker.qual;

    exports com.itquasar.multiverse.tn3270j;
    exports com.itquasar.multiverse.tn3270j.status;
}

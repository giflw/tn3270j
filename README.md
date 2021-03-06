# TN3270j

TN3270 Java library wrapping x3270/wc3270

To run x3270 wrapper with maven use:

```shell
.\mvnw package exec:java '-Dexec.args="implementationId path/to/ws3270/or/s3270'
```

where `implementationId` is only `3270/j3270` by now. It means TN3270j will be using https://github.com/3270/j3270 implementation under the hood.

## Based on 

### j3270: Simple tn3270 Support for Java

*https://github.com/3270/j3270*

j3270 is a set of Java libraries that uses [s3270](http://x3270.bgp.nu/Unix/s3270-man.html "s3270"), exposing its capabilities to software running on the JVM.

The design is layered, instead of having a single "high-level" library for using the tn3270 protocol, j3270 provides a [base](base) layer that wraps over [s3270](http://x3270.bgp.nu/Unix/s3270-man.html "s3270") and additional libraries built on top. This reduces the problems of trying to build an entire tn3270 implementation from the ground up and building a complete API on top of it. Anything that can be done using [s3270](http://x3270.bgp.nu/Unix/s3270-man.html "s3270") should be possible to do using the same API in [j3270's wrapper layer](base).


### j3270 

*https://github.com/filipesimoes/j3270*

A Java Wrapper for x3270 (IBM 3270 terminal emulator) based on [py3270](https://github.com/py3270/py3270).

It is a Java API for x3270 (Linux) or s3270 (Windows) subprocess.

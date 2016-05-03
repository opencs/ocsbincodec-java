# OpenCS Binary-to-Text library for Java 1.4
Copyright (c) 2015-2016 Open Communications Security. All rights reserved.

## Introduction

The OpenCS Binary-to-Text library for Java (ocsbincodec-java) is a customizable
library capable of encode and decode binary values to plain text using multiple
standard and non-standard encodings schemes.

This library was designed to be as flexible as possible. This means that the
implementations in this library will always favor flexibility first.

This version is a backport of the original OpenCS Binary-to-Text library for 
Java that targets Java 1.4. Because of that, the binary interface of this
version was modified in order to handle the lack of classes only available
on Java 5 and later.

It is important to notice that users of this version can easly start to use the
original version but users of the new version cannot start to use this version
of the library without proper adjustments.

Although this version is fully compatible with Java 5 and later, it is 
recommended to use the non Java 1.4 for better performance and compatibility
with the new frameworks introduced in Java 1.5.

## Version history

* 1.0.2:
  * Backport of the original source version 1.0.1 to Java 1.4;

## Features

* Multiple Binary-to-Text encondings
  * Binary (customizeble)
  * Hexadecimal (RFC4648, customizable)
  * Base32 (RFC4648, customizable)
  * Base64 (RFC4648, customizable)
* Custom alphabets
* Uniform interface for all encoders/decoders
* Compatible with Java 1.4 and higher

## Licensing

This software is released under the *Modified BSD License*.

## FAQ

### Can I use this library on commercial software?

Yes. Just follow the license restrictions.




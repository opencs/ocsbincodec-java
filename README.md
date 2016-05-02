# OpenCS Binary-to-Text library for Java
Copyright (c) 2015 Open Communications Security. All rights reserved.

## Introduction

The OpenCS Binary-to-Text library for Java (ocsbincodec-java) is a customizable
library capable of encode and decode binary values to plain text using multiple
standard and non-standard encodings schemes.

This library was designed to be as flexible as possible. This means that the
implementations in this library will always favor flexibility first.

## Version history

* 1.0.2:
  * Now compatible with Java 1.4
* 1.0.1:
  * Documentation updated
  * A few fixes in unit-tests
  * Method br.com.opencs.bincodec.Base2NCodec.isIgnored(int) became public;
  * Method br.com.opencs.bincodec.Base2NCodec.isPadding(int) became public;
* 1.0.0:
  * First public version

## Features

* Multiple Binary-to-Text encondings
  * Binary (customizeble)
  * Hexadecimal (RFC4648, customizable)
  * Base32 (RFC4648, customizable)
  * Base64 (RFC4648, customizable)
* Custom alphabets
* Uniform interface for all encoders/decoders
* Compatible with Java 1.6 and higher (including Android API)

## Licensing

This software is released under the *Modified BSD License*.

## FAQ

### Can I use this library on commercial software?

Yes. Just follow the license restrictions.




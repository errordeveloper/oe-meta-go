DESCRIPTION = "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"

SRC_URI = "http://golang.org/dl/go${PV}.src.tar.gz"

LIC_FILES_CHKSUM = "file://LICENSE;md5=591778525c869cdde0ab5a1bf283cd81"
SRC_URI[md5sum] = "4b66d7249554181c314f139ea78920b1"
SRC_URI[sha256sum] = "eb983e6c5b2b9838f482c5442b1ac1856f610f2b21f3c123b3fedb48ffc35382"

S = "${WORKDIR}/go/"

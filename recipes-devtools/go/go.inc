DESCRIPTION = "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"

SRC_URI = "hg://code.google.com/p;module=go;rev=release-branch.go${PV}"

LIC_FILES_CHKSUM = "file://${WORKDIR}/go/LICENSE;md5=591778525c869cdde0ab5a1bf283cd81"

S = "${WORKDIR}/go/src"

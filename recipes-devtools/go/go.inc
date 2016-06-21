DESCRIPTION = "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"

DEPENDS = "virtual/${TARGET_PREFIX}gcc"

SRC_URI = "http://golang.org/dl/go${PV}.src.tar.gz"

S = "${WORKDIR}/go/"

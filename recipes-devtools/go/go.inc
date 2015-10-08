DESCRIPTION = "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"

SRC_URI_PREFIX = "http://golang.org/dl/"
GO_BOOTSTRAP_SOURCE="go1.4.src.tar.gz"
SRC_URI_GO_BOOTSTRAP = "${SRC_URI_PREFIX}/${GO_BOOTSTRAP_SOURCE}"
SRC_URI = "${SRC_URI_PREFIX}/go${PV}.src.tar.gz"

S = "${WORKDIR}/go/"

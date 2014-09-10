DESCRIPTION = "This is a simple example recipe that cross-compiles a Go program."
SECTION = "examples"
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"

SRC_URI = " \
  file://helloworld.go \
  file://LICENSE \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=591778525c869cdde0ab5a1bf283cd81"

DEPENDS = "go-cross"

do_compile() {
  export GOARCH="arm"
  go build helloworld.go
}

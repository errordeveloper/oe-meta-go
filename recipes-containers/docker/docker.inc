DESCRIPTION = "Docker is an open platform for distributed applications."
HOMEPAGE = "https://docker.com/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/docker/docker"

DEPENDS = " \
  go-cross \
  iproute2 \
  bridge-utils \
  sqlite3 \
"

RDEPENDS_${PN} = " \
  iproute2 \
  bridge-utils \
"

##NOTE: it links to sqlite statically, so we don't need it at runtime,
## althought for debugging one my wish to have sqlite3 command installed
# RDEPENDS_${PN}_dev = " sqlite3 "

##TODO: make lxc, btrfs and aufs conditional

S = "${WORKDIR}/git"

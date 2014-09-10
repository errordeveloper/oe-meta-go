DESCRIPTION = "Docker is an open platform for distributed applications."
HOMEPAGE = "https://docker.com/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/docker/docker"

DEPENDS = "go-cross"

S = "${WORKDIR}/git"

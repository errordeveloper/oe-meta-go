require docker.inc

SRCREV = "v${PV}/cross_target_without_docker_installed"
SRC_URI = "git://github.com/errordeveloper/docker;branch=${SRCREV}"

EXTRA_OEMAKE = "cross \
  DOCKER_RUN_DOCKER=env \
  AUTO_GOPATH=1 \
  GOARCH=arm \
"

require docker.inc

SRCREV = "v${PV}/cross_target_without_docker_installed"
SRC_URI = "git://github.com/errordeveloper/docker;branch=${SRCREV}"

do_compile() {
  export DOCKER_BUILDTAGS='exclude_graphdriver_btrfs exclude_graphdriver_devicemapper exclude_graphdriver_aufs'
  #export DOCKER_CROSSPLATFORMS='linux/arm'

  #oe_runmake cross \
  #  DOCKER_RUN_DOCKER=env \
  #  AUTO_GOPATH=1 \
  #  GOARCH=arm

  export GOARCH=arm
  export AUTO_GOPATH=1
  bash -x hack/make.sh binary
}

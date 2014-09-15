require docker.inc

SRCREV = "v${PV}/fix_7979"
SRC_URI = "git://github.com/errordeveloper/docker;branch=${SRCREV}"

do_compile() {
  export DOCKER_BUILDTAGS=" \
    exclude_graphdriver_devicemapper \
    exclude_graphdriver_aufs \
  "

  export GOARCH="arm"
  export AUTO_GOPATH="1"

  bash -x hack/make.sh binary
}

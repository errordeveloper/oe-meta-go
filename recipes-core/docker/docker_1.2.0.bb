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

do_install() {
  install -d "${D}${bindir}"
  install -m 0755 "${S}/bundles/${PV}/binary/docker-${PV}" "${D}${bindir}"
  ln -sf "docker-${PV}" "${S}/bundles/${PV}/binary/docker-${PV}"

  ##TODO: grab systemd configs, if we have systemd in distro features ("${S}/contrib/init/systemd/docker.{socker,service}")
}

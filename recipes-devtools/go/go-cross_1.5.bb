require go.inc
require go_${PV}.inc

inherit cross

SRC_URI += "\
        file://Fix-ccache-compilation-issue.patch \
        "

do_compile() {
  ## install a build of Go 1.4 in the SYSROOT so we don't need it anywhere else
  ## in the system (as it currently is the default)
  export GOROOT_BOOTSTRAP_INSTALL="${STAGING_DIR_HOST}/go1.4"
  export GOROOT_BOOTSTRAP="${GOROOT_BOOTSTRAP_INSTALL}/go"

  mkdir -p ${GOROOT_BOOTSTRAP_INSTALL}
  cd ${GOROOT_BOOTSTRAP_INSTALL}
  wget ${SRC_URI_GO_BOOTSTRAP}
  tar -xzvf ${GO_BOOTSTRAP_SOURCE}
  cd - && cd ${GOROOT_BOOTSTRAP_INSTALL}/go/src/
  ./all.bash
  cd -

  ## Setting `$GOBIN` doesn't do any good, looks like it ends up copying binaries there.
  export GOROOT_FINAL="${SYSROOT}${libdir}/go"

  export GOHOSTOS="linux"
  export GOOS="linux"

  export GOARCH="${TARGET_ARCH}"
  if [ "${TARGET_ARCH}" = "x86_64" ]; then
    export GOARCH="amd64"
  fi
  if [ "${TARGET_ARCH}" = "arm" ]
  then
    if [ `echo ${TUNE_PKGARCH} | cut -c 1-7` = "cortexa" ]
    then
      echo GOARM 7
      export GOARM="7"
    fi
  fi
  if [ "${TARGET_ARCH}" = "aarch64" ]; then
    export GOARCH="arm64"
  fi

  ## TODO: consider setting GO_EXTLINK_ENABLED
  export CGO_ENABLED="1"
  export CC=${BUILD_CC}
  export CC_FOR_TARGET="${TARGET_SYS}-gcc"
  export CXX_FOR_TARGET="${TARGET_SYS}-g++"
  export GO_GCFLAGS="${HOST_CFLAGS}"
  export GO_LDFLAGS="${HOST_LDFLAGS}"

  cd src && sh -x ./make.bash

  ## The result is `go env` giving this:
  # GOARCH="amd64"
  # GOBIN=""
  # GOCHAR="6"
  # GOEXE=""
  # GOHOSTARCH="amd64"
  # GOHOSTOS="linux"
  # GOOS="linux"
  # GOPATH=""
  # GORACE=""
  # GOROOT="/home/build/poky/build/tmp/sysroots/x86_64-linux/usr/lib/cortexa8hf-vfp-neon-poky-linux-gnueabi/go"
  # GOTOOLDIR="/home/build/poky/build/tmp/sysroots/x86_64-linux/usr/lib/cortexa8hf-vfp-neon-poky-linux-gnueabi/go/pkg/tool/linux_amd64"
  ## The above is good, but these are a bit odd... especially the `-m64` flag.
  # CC="arm-poky-linux-gnueabi-gcc"
  # GOGCCFLAGS="-fPIC -m64 -pthread -fmessage-length=0"
  # CXX="arm-poky-linux-gnueabi-g++"
  ## TODO: test on C+Go project.
  # CGO_ENABLED="1"
}

do_install() {
  ## It turns out that `${D}${bindir}` is already populated by compilation script
  ## We need to copy the rest, unfortunatelly pretty much everything [1, 2].
  ##
  ## [1]: http://sources.gentoo.org/cgi-bin/viewvc.cgi/gentoo-x86/dev-lang/go/go-1.3.1.ebuild?view=markup)
  ## [2]: https://code.google.com/p/go/issues/detail?id=2775

  ## It should be okay to ignore `${WORKDIR}/go/bin/linux_arm`...
  ## Also `gofmt` is not needed right now.
  install -d "${D}${bindir}"
  install -m 0755 "${WORKDIR}/go/bin/go" "${D}${bindir}"
  install -d "${D}${libdir}/go"
  ## TODO: use `install` instead of `cp`
  for dir in lib pkg src test
  do cp -a "${WORKDIR}/go/${dir}" "${D}${libdir}/go/"
  done
}

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly

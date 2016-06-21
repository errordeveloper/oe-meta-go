require go.inc
require go_${PV}.inc

inherit cross

SRC_URI += "\
        file://bsd_svid_source.patch \
        file://ccache.patch \
        "

do_compile() {
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

  export CGO_ENABLED="1"
  ## TODO: consider setting GO_EXTLINK_ENABLED

  export CC="${BUILD_CC}"
  export CC_FOR_TARGET="${CC}"
  export CXX_FOR_TARGET="${CXX}"
  export GO_CCFLAGS="${HOST_CFLAGS}"
  export GO_LDFLAGS="${HOST_LDFLAGS}"

  cd src && bash -x ./make.bash

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
  for dir in include lib pkg src test
  do cp -a "${WORKDIR}/go/${dir}" "${D}${libdir}/go/"
  done
}

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly

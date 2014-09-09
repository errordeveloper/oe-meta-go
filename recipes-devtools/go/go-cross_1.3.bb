require go.inc

inherit cross

deltask configure

do_compile() {
  export GOBIN="${D}${bindir}"
  export GOROOT_FINAL="${D}${libdir}/go"

  export GOHOSTOS="linux"
  export GOOS="linux"

  ## TODO: make these conditional
  export GOARCH="arm"
  export GOARM="7"
  export GOHOSTARCH="amd64"

  export CGO_ENABLED="1"
  ## TODO: consider setting GO_EXTLINK_ENABLED

  export CC="${BUILD_CC}"
  export CC_FOR_TARGET="${TARGET_SYS}-gcc"
  export CXX_FOR_TARGET="${TARGET_SYS}-g++"
  export GO_CCFLAGS="${HOST_CFLAGS}"
  export GO_LDFLAGS="${HOST_LDFLAGS}"

  ./make.bash
}

do_install() {
  ## It turns out that `${D}${bindir}` is already populated by compilation script
  ## We need to copy the rest, unfortunatelly pretty much everything [1, 2].
  ##
  ## [1]: http://sources.gentoo.org/cgi-bin/viewvc.cgi/gentoo-x86/dev-lang/go/go-1.3.1.ebuild?view=markup)
  ## [2]: https://code.google.com/p/go/issues/detail?id=2775

  ## TODO: use install instead of mkdir and cp
  mkdir -p "${D}${libdir}/go"
  for dir in include lib pkg src test
  do cp -a "${WORKDIR}/go/${dir}" "${D}${libdir}/go/"
  done
}

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly

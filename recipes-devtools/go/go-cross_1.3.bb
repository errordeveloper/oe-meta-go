require go.inc

inherit cross

do_compile() {
  export GOROOT_FINAL="${D}${libdir}"

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

#!/usr/bin/env bash

set -e
set -x

VERSION="0.6.0-INCUBATING"
MESOS_VERSION=0.20.1

mkdir -p third_party
pushd third_party
wget -nv -c "https://svn.apache.org/repos/asf/incubator/aurora/3rdparty/ubuntu/trusty64/python/mesos.native-${MESOS_VERSION}-py2.7-linux-x86_64.egg"
popd

./gradlew distTar

goals=(
  src/main/python/apache/aurora/client/bin:aurora_client
  src/main/python/apache/aurora/client/cli:aurora2
  src/main/python/apache/aurora/client/bin:aurora_admin
  src/main/python/apache/aurora/executor/bin:gc_executor
  src/main/python/apache/aurora/executor/bin:thermos_executor
  src/main/python/apache/aurora/executor/bin:thermos_runner
  src/main/python/apache/thermos/observer/bin:thermos_observer
)

for goal in "${goals[@]}"; do
  ./pants "$goal"
done

mkdir -p dist/tmp/deb/opt dist/tmp/deb/usr/bin

pushd dist/tmp/deb/opt
tar xvf "../../../../dist/distributions/aurora-scheduler-${VERSION}.tar"
popd

#unzip "dist/distributions/aurora-scheduler-${VERSION}.zip" \
#  -d dist/tmp/deb/usr/local

args=(
  -t deb
  -s dir
  -n aurora
  -v "$VERSION"
  --iteration 0~trusty1
  -d java-7-runtime
  -a all
  -m "Benjamin Staffin <ben@folsomlabs.com>"
  --exclude '*.bat'
  -C dist/tmp/deb
  opt
)

fpm "${args[@]}"

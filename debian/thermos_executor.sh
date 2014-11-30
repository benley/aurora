#!/bin/sh
set -e

if [ -e /etc/default/aurora-thermos-executor ]; then
  . /etc/default/aurora-thermos-executor
fi

exec /usr/share/aurora/bin/thermos_executor.pex \
  --announcer-enable \
  --announcer-ensemble "${ZK_ENSEMBLE:-localhost:2181}" \
  --announcer-serverset-path "${ZK_SERVERSET_PATH:-/aurora}"

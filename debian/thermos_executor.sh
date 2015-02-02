#!/bin/sh
set -e

if [ -e /etc/default/thermos ]; then
  . /etc/default/thermos
fi

exec /usr/share/aurora/bin/thermos_executor.pex \
  --announcer-enable \
  --announcer-ensemble "${ANNOUNCER_ENSEMBLE:-localhost:2181}" \
  --announcer-serverset-path "${ANNOUNCER_SERVERSET_PATH:-/aurora/svc}"

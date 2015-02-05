#!/usr/bin/env python
"""Package thermos_runner within thermos_executor."""

import contextlib
import zipfile

with contextlib.closing(zipfile.ZipFile('dist/thermos_executor.pex',
                                        'a')) as zf:
    zf.writestr('apache/aurora/executor/resources/__init__.py', '')
    zf.write('dist/thermos_runner.pex',
             'apache/aurora/executor/resources/thermos_runner.pex')

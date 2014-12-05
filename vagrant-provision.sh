#!/bin/bash
set -e
set -x

export DEBIAN_FRONTEND=noninteractive

apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 21E1FA28 E56151BF
cat > /etc/apt/sources.list.d/vagrant.list <<EOF
deb http://folsom-labs-artifacts.s3.amazonaws.com/apt trusty main
deb http://repos.mesosphere.io/ubuntu trusty main
EOF
apt-get update
apt-get -qfy install pbuilder git-buildpackage

cd /vagrant
/usr/lib/pbuilder/pbuilder-satisfydepends
mkdir -p "$HOME/build-area"
git-buildpackage --git-export-dir="$HOME/build-area" -us -uc --git-ignore-new

cd "$HOME/build-area"
dpkg-scanpackages . > Packages

cat > /etc/apt/sources.list.d/local.list <<EOF
deb [trusted=yes] file://$HOME/build-area ./
EOF

apt-get update
apt-get -qfy install aurora-scheduler aurora-mesos-slave aurora-tools

service aurora-scheduler stop ||true
service aurora-thermos stop ||true
service mesos-master stop ||true
service mesos-slave stop ||true

mkdir -p /etc/aurora
cat > /etc/aurora/clusters.json <<EOF
[{
"name": "example",
"zk": "192.168.33.8",
"scheduler_zk_path": "/aurora/scheduler",
"auth_mechanism": "UNAUTHENTICATED",
"slave_run_directory": "latest",
"slave_root": "/var/lib/mesos"
}]
EOF

mkdir -p /var/lib/aurora/scheduler/db
mesos-log initialize --path=/var/lib/aurora/scheduler/db

mkdir -p /etc/mesos-slave/attributes
echo "/var/lib/mesos" > /etc/mesos-slave/work_dir
echo "1" > /etc/mesos-slave/attributes/rack
echo "$HOSTNAME" > /etc/mesos-slave/attributes/host

service mesos-master start
service mesos-slave start
service aurora-scheduler start
service aurora-thermos start

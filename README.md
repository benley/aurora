# Debian packaging for Aurora

This is a work in progress - use at your own risk for now!

To build, you'll need to combine the debian/ directory in this branch with the appropriate branch or tarball of the Aurora sources. [Git-buildpackage](https://github.com/agx/git-buildpackage) is a great tool for this, and can run the build process after producing a source package.

```sh
# (on an ubuntu 14.04 system)
git clone [this repo]
git checkout debian/trusty
sudo apt-get install pbuilder
sudo /usr/lib/pbuilder/pbuilder-satisfydepends
git-buildpackage -us -uc
# Packages will land in ../build-area/ when the build finishes.
```

You can also use pbuilder/cowbuilder if you want to build for a distribution other than the one currently running:

```sh
sudo apt-get install cowbuilder
sudo cowbuilder --create \
  --basepath /var/cache/pbuilder/base-trusty-amd64.cow \
  --distribution trusty \
  --architecture amd64
git-buildpackage --git-dist=trusty --git-arch=amd64 --git-pbuilder
```

The build has package dependencies on Thrift 0.9.1 and Gradle 2.2.1, neither of which are in Ubuntu 14.04, so these will have to come from a ppa. Stay tuned for that.

## Vagrant testing

The Vagrantfile at the root of this repository should work for launching an Ubuntu 14.04 vm, using that to build debs from this branch, and setting up a bare minimum working Aurora/Mesos/Zookeeper installation.

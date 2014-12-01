# Debian packaging for Aurora

This is a work in progress - use at your own risk for now!

To build, you'll need to combine the 0.6.0-incubating branch with the debian/ directory in this branch.
[Git-buildpackage](https://github.com/agx/git-buildpackage) is a great tool for this, and can run the build process after producing a source package.

```sh
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

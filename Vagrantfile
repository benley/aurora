# encoding: utf-8
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = '2'

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = 'ubuntu/trusty64'
  config.cache.auto_detect = true
  config.vm.define 'debtest' do |deb|
    deb.vm.network :private_network, ip: '192.168.33.8'
    deb.vm.hostname = 'aurora-debtest'
    deb.vm.provider :virtualbox do |vb|
      vb.customize ['modifyvm', :id, '--memory', '1024']
    end
    deb.vm.synced_folder '../build-area', '/debs', type: 'nfs'
  end
end

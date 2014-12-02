# encoding: utf-8
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = '2'

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = 'ubuntu/trusty64'

  if Vagrant.has_plugin?('vagrant-cachier')
    config.cache.auto_detect = true
    config.cache.scope = :box
    config.cache.synced_folder_opts = {
      type: :nfs,
      mount_options: [:rw, :tcp]
    }
  end

  unless Vagrant.has_plugin?('vagrant-hostsupdater')
    fail 'Please install the vagrant-hostsupdater plugin.'
  end

  config.vm.define 'debtest' do |deb|
    deb.vm.network :private_network, ip: '192.168.33.8'
    deb.vm.hostname = 'aurora-debtest'
    deb.vm.provider :virtualbox do |vb|
      vb.customize ['modifyvm', :id, '--memory', '1024']
    end
    deb.vm.provision 'shell', path: 'vagrant-provision.sh'
  end
end

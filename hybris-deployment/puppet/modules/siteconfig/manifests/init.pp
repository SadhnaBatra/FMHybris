# == Class: siteconfig
#
# Installs Hybris and build dependencies to Vagrant machine.
#
class siteconfig {

  exec { apt-get-update:
    command => 'apt-get update';
  }

  package { ['ant', 'openjdk-7-jdk']:
    ensure => present,
    require => Exec[apt-get-update];
  }

  exec { init-hybris-directory:
    command => 'cp /vagrant/hybris-install/hybris5.3.tar.gz /tmp/. && rm -rf /usr/share/hybris && mkdir -p /usr/share/hybris && gunzip /tmp/hybris5.3.tar.gz';
  }

  exec { setup-hybris:
    command => 'tar xf /tmp/hybris5.3.tar -C /usr/share/hybris && mkdir -p /usr/share/hybris/bin/custom && ln -s /vagrant/fm /usr/share/hybris/bin/custom/fm && cp /vagrant/config/* /usr/share/hybris/config/. && chown -R vagrant:vagrant /usr/share/hybris',
    require => Exec[init-hybris-directory];
  }

  exec { install-hybris:
    user => 'vagrant',
    command => 'bash -c ". setantenv.sh; ant clean all"',
    cwd => '/usr/share/hybris/bin/platform',
    require => [ Package['ant'], Package['openjdk-7-jdk'], Exec[setup-hybris] ];
  }

  exec { cleanup:
    command => 'rm -f /tmp/hybris5.3.tar',
    require => Exec[install-hybris];
  }
}
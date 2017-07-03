FROM exoplatform/ci:jenkins-plugins

COPY config/ /usr/share/jenkins/ref/
COPY init.groovy.d /usr/share/jenkins/ref/init.groovy.d
COPY init.groovy.mixins /usr/share/jenkins/ref/init.groovy.mixins
COPY init.dsl.d /usr/share/jenkins/ref/init.dsl.d

RUN chown -R jenkins:jenkins /usr/share/jenkins/ref

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

ENTRYPOINT /usr/local/bin/jenkins.sh

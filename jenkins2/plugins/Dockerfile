FROM jenkins/jenkins:2.73.2

LABEL MAINTAINER "eXo Platform <docker@exoplatform.com>"

USER root

# Install plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

RUN apt-get update && apt-get install -y git gettext jq && rm -rf /var/lib/apt/lists/*

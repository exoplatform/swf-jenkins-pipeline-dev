FROM jenkins/jenkins:2.164.3

LABEL MAINTAINER "eXo Platform <docker@exoplatform.com>"

USER root

ARG LAST_PLUGINS_VERSION=false

# Install plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN if ${LAST_PLUGINS_VERSION}; then cut -f1 -d":" /usr/share/jenkins/ref/plugins.txt > /tmp/plugins.txt; else cp /usr/share/jenkins/ref/plugins.txt /tmp/plugins.txt; fi && \
    /usr/local/bin/install-plugins.sh < /tmp/plugins.txt

RUN apt-get update && apt-get install -y git gettext jq && rm -rf /var/lib/apt/lists/*

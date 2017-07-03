#!/usr/bin/env sh
set -e

[ ! -z "$DEBUG" ] && set -ex

apk --update add gettext

[ ! -f /data/secret-key ] && \
    echo $(< /dev/urandom tr -dc _A-Z-a-z-0-9 | head -c${1:-32}) > /data/secret-key

export SECRET_KEY=$(cat /data/secret-key)

cat /data/gogs/conf/app.ini.tmpl | envsubst > /data/gogs/conf/app.ini

exec /bin/s6-svscan /app/gogs/docker/s6/

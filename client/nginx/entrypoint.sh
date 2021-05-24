#!/usr/bin/env sh
set -eu

envsubst '${API_URI}' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf

exec "$@"
